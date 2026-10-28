
import {
  Component,
  ElementRef,
  Input,
  ViewChild,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

import { OrderReportResponse } from '../../components/order.model';
import { TestMethod } from '../../../catalog/test/components/test.model';

@Component({
  selector: 'app-order-report',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-report.component.html',
  styleUrls: [
    './order-report.component.css',
    './order-report-actions.component.css',
    './order-report-header.component.css',
    './order-report-info.component.css',
    './order-report-results.component.css',
    './order-report-note.component.css',
    './order-report-footer.component.css',
    './order-report-table.component.css'
  ],
})
export class OrderReportComponent {
  @Input() report?: OrderReportResponse;

  @ViewChild('reportContent')
  reportContent?: ElementRef<HTMLElement>;

  downloadingPdf = false;

  testMethodLabels: Record<TestMethod, string> = {
  [TestMethod.CUANTITATIVA]: 'Cuantitativa',
  [TestMethod.RAPIDA]: 'Rápida',
  [TestMethod.SEMI_CUANTITATIVA]: 'Semi cuantitativa',
  [TestMethod.AGLUTINACION_PORTA]: 'Aglutinación en porta',
  [TestMethod.OTROS]: 'Otros',
};
  async downloadPdf(): Promise<void> {
    if (!this.reportContent || !this.report) {
      return;
    }

    this.downloadingPdf = true;

    const original = this.reportContent.nativeElement;

    // Crear una copia independiente del contenido del reporte.
    const clone = original.cloneNode(true) as HTMLElement;
    clone.classList.add('generating-pdf');

    // Contenedor independiente del modal.
    const host = document.createElement('div');

    host.style.cssText = [
      'position:fixed',
      'left:-10000px',
      'top:0',
      'width:199.9mm',
      'min-width:0',
      'max-width:none',
      'height:auto',
      'overflow:visible',
      'background:#ffffff',
      'z-index:-1',
      'box-sizing:border-box',
    ].join(';');

    // Establecer las dimensiones de la copia.
    clone.style.cssText += `
      display: block;
      width: 100%;
      min-width: 0;
      max-width: none;
      height: auto;
      max-height: none;
      overflow: visible;
      box-sizing: border-box;
    `;

    host.appendChild(clone);
    document.body.appendChild(host);

    let canvas: HTMLCanvasElement | undefined;

    try {
      // Esperar a que las fuentes estén disponibles.
      await document.fonts.ready;

      // Esperar a que terminen de cargar las imágenes.
      await Promise.all(
        Array.from(clone.querySelectorAll('img')).map(
          async (img): Promise<void> => {
            if (img.complete) {
              if (img.decode) {
                await img.decode().catch(() => undefined);
              }
              return;
            }

            await new Promise<void>((resolve) => {
              img.onload = () => resolve();
              img.onerror = () => resolve();
            });
          }
        )
      );

      // Permitir que el navegador calcule el diseño del clon.
      await new Promise<void>((resolve) => {
        requestAnimationFrame(() => {
          requestAnimationFrame(() => resolve());
        });
      });

      // Comprobar el ancho calculado antes de la captura.
      const cloneWidth = clone.getBoundingClientRect().width;

      if (cloneWidth <= 0) {
        throw new Error(
          'El reporte no tiene un ancho válido para generar el PDF.'
        );
      }

      // Capturar el reporte completo, sin depender del scroll del modal.
      canvas = await html2canvas(clone, {
        scale: 3,
        useCORS: true,
        backgroundColor: '#ffffff',
        scrollX: 0,
        scrollY: 0,
        width: Math.ceil(clone.scrollWidth),
        height: Math.ceil(clone.scrollHeight),
        windowWidth: Math.ceil(cloneWidth),
        windowHeight: Math.ceil(clone.scrollHeight),
      });

      const pdf = new jsPDF({
        orientation: 'portrait',
        unit: 'mm',
        format: 'letter',
        compress: true,
      });

      const margin = 8;

      const pageWidth = pdf.internal.pageSize.getWidth();
      const pageHeight = pdf.internal.pageSize.getHeight();

      const contentWidth = pageWidth - margin * 2;
      const contentHeight = pageHeight - margin * 2;

      // Mantener la proporción de la captura al ajustar el ancho.
      const scale = contentWidth / canvas.width;

      // Altura máxima de imagen que cabe en cada página.
      const sliceHeight = Math.max(
        1,
        Math.floor(contentHeight / scale)
      );

      let sourceY = 0;
      let firstPage = true;

      while (sourceY < canvas.height) {
        const currentSliceHeight = Math.min(
          sliceHeight,
          canvas.height - sourceY
        );

        const pageCanvas = document.createElement('canvas');

        pageCanvas.width = canvas.width;
        pageCanvas.height = currentSliceHeight;

        const context = pageCanvas.getContext('2d');

        if (!context) {
          throw new Error(
            'No se pudo crear la imagen de la página PDF.'
          );
        }

        context.fillStyle = '#ffffff';

        context.fillRect(
          0,
          0,
          pageCanvas.width,
          pageCanvas.height
        );

        context.drawImage(
          canvas,
          0,
          sourceY,
          canvas.width,
          currentSliceHeight,
          0,
          0,
          canvas.width,
          currentSliceHeight
        );

        if (!firstPage) {
          pdf.addPage('letter', 'portrait');
        }

        pdf.addImage(
          pageCanvas.toDataURL('image/jpeg', 0.95),
          'JPEG',
          margin,
          margin,
          contentWidth,
          currentSliceHeight * scale,
          undefined,
          'FAST'
        );

        sourceY += currentSliceHeight;
        firstPage = false;

        // Liberar memoria de las capturas parciales.
        pageCanvas.width = 0;
        pageCanvas.height = 0;
      }

      pdf.save(`reporte-laboratorio-${this.report.id}.pdf`);
    } catch (error) {
      console.error('Error al generar el PDF:', error);
    } finally {
      // Liberar memoria y retirar el clon.
      if (canvas) {
        canvas.width = 0;
        canvas.height = 0;
      }

      host.remove();
      this.downloadingPdf = false;
    }
  }

  openWhatsApp(): void {
    const patientPhone = this.report?.patientPhoneNumber;

    if (!patientPhone) {
      console.warn(
        'El paciente no tiene número de teléfono.'
      );
      return;
    }

    // Eliminar espacios, guiones, paréntesis y otros caracteres.
    let phone = String(patientPhone).replace(/\D/g, '');

    // Agregar el código de Guatemala a números de ocho dígitos.
    if (phone.length === 8) {
      phone = `502${phone}`;
    }

    const message = encodeURIComponent(
      'Hola, le compartimos su reporte de laboratorio.'
    );

    const whatsappUrl =
      `https://wa.me/${phone}?text=${message}`;

    window.open(whatsappUrl, '_blank');
  }

private hasEditorContent(value: unknown): boolean {
  if (value == null) {
    return false;
  }

  const html = String(value).trim();

  if (!html) {
    return false;
  }

  const doc = new DOMParser().parseFromString(html, 'text/html');

  // Eliminar elementos que no aportan texto al contenido.
  doc.body.querySelectorAll('script, style').forEach((element) => {
    element.remove();
  });

  const text = (doc.body.textContent ?? '')
    .replace(/[\s\u00a0\u200b\u200c\u200d\uFEFF]/g, '');

  return text.length > 0;
}

showResultDetails(): boolean {
  const results = this.report?.results ?? [];

  return results.some(
    (result) =>
      this.hasEditorContent(result.testGroup) ||
      this.hasEditorContent(result.result) ||
      this.hasEditorContent(result.reference)
  );
}

hasColumnContent(field: 'testGroup' | 'result' | 'reference'): boolean {
  return this.report?.results?.some(
    (item) => this.hasEditorContent(item[field])
  ) ?? false;
}
}
