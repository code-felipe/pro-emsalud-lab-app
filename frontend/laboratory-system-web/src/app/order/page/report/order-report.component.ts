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

@Component({
  selector: 'app-order-report',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-report.component.html',
  styleUrl: './order-report.component.css',
})
export class OrderReportComponent {
  @Input() report?: OrderReportResponse;

  @ViewChild('reportContent')
  reportContent?: ElementRef<HTMLElement>;

  downloadingPdf = false;

  async downloadPdf(): Promise<void> {
    if (!this.reportContent || !this.report) {
      return;
    }

    this.downloadingPdf = true;

    const original = this.reportContent.nativeElement;

    // Clon fuera del modal, con el ancho exacto del área útil del PDF.
    const clone = original.cloneNode(true) as HTMLElement;
    clone.classList.add('generating-pdf');

    const host = document.createElement('div');
    host.style.cssText = [
      'position:fixed',
      'left:-10000px',
      'top:0',
      'width:199.9mm',
      'height:auto',
      'overflow:visible',
      'background:#ffffff',
      'z-index:-1',
    ].join(';');

    host.appendChild(clone);
    document.body.appendChild(host);

    try {
      // Esperar fuentes, imágenes (logos) y el layout.
      await document.fonts.ready;

      await Promise.all(
        Array.from(clone.querySelectorAll('img')).map((img) =>
          img.complete
            ? Promise.resolve()
            : new Promise<void>((resolve) => {
                img.onload = () => resolve();
                img.onerror = () => resolve();
              })
        )
      );

      await new Promise<void>((resolve) =>
        requestAnimationFrame(() =>
          requestAnimationFrame(() => resolve())
        )
      );

      // Sin width/height/windowHeight: html2canvas mide el clon ya renderizado.
      const canvas = await html2canvas(clone, {
        scale: 3,
        useCORS: true,
        backgroundColor: '#ffffff',
        scrollX: 0,
        scrollY: 0,
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

      // Escala para que todo el ancho de la captura entre en la página.
      const scale = contentWidth / canvas.width;

      // Altura de cada fragmento de la imagen en píxeles.
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

        // Liberar memoria de los fragmentos intermedios.
        pageCanvas.width = 0;
        pageCanvas.height = 0;
      }

      pdf.save(`reporte-laboratorio-${this.report.id}.pdf`);

      // Liberar la imagen original.
      canvas.width = 0;
      canvas.height = 0;
    } catch (error) {
      console.error('Error al generar el PDF:', error);
    } finally {
      host.remove();
      this.downloadingPdf = false;
    }
  }

  openWhatsApp(): void {
    const patientPhone = this.report?.patientPhoneNumber;

    if (!patientPhone) {
      console.warn('El paciente no tiene número de teléfono.');
      return;
    }

    // Quitar espacios, guiones, paréntesis y otros caracteres.
    let phone = String(patientPhone).replace(/\D/g, '');

    // Si es un número guatemalteco de 8 dígitos,
    // agregar el código de país 502.
    if (phone.length === 8) {
      phone = `502${phone}`;
    }

    const message = encodeURIComponent(
      'Hola, le compartimos su reporte de laboratorio.'
    );

    const whatsappUrl = `https://wa.me/${phone}?text=${message}`;

    window.open(whatsappUrl, '_blank');
  }
}