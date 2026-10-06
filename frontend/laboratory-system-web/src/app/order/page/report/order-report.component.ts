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
  imports: [
    CommonModule
  ],
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

  const element = this.reportContent.nativeElement;

  element.classList.add('generating-pdf');

  try {

    const canvas = await html2canvas(element, {
      scale: 2,
      useCORS: true,
      backgroundColor: '#ffffff',

      height: element.scrollHeight,
      windowHeight: element.scrollHeight,
    });

    const imageData = canvas.toDataURL('image/png');

    const pdf = new jsPDF({
      orientation: 'portrait',
      unit: 'mm',
      format: 'a4',
    });

    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = pdf.internal.pageSize.getHeight();

    const imageWidth = pdfWidth;

    const imageHeight =
      (canvas.height * imageWidth) / canvas.width;

    let heightLeft = imageHeight;
    let position = 0;

    pdf.addImage(
      imageData,
      'PNG',
      0,
      position,
      imageWidth,
      imageHeight
    );

    heightLeft -= pdfHeight;

    while (heightLeft > 0) {

      position = heightLeft - imageHeight;

      pdf.addPage();

      pdf.addImage(
        imageData,
        'PNG',
        0,
        position,
        imageWidth,
        imageHeight
      );

      heightLeft -= pdfHeight;
    }

    pdf.save(
      `reporte-laboratorio-${this.report.id}.pdf`
    );

  } catch (error) {

    console.error(
      'Error al generar el PDF:',
      error
    );

  } finally {

    element.classList.remove('generating-pdf');

    this.downloadingPdf = false;
  }
}

openWhatsApp(): void {
  const patientPhone = this.report?.patientPhoneNumber;

  console.log('Teléfono del paciente:', patientPhone);

  if (!patientPhone) {
    console.warn('El paciente no tiene número de teléfono.');
    return;
  }

  // Quitar espacios, guiones, paréntesis, etc.
  let phone = String(patientPhone).replace(/\D/g, '');

  // Si viene como número guatemalteco de 8 dígitos, agregar 502.
  if (phone.length === 8) {
    phone = `502${phone}`;
  }

  console.log('Número WhatsApp:', phone);

  const message = encodeURIComponent(
    'Hola, le compartimos su reporte de laboratorio.'
  );

  const whatsappUrl =
    `https://wa.me/${phone}?text=${message}`;

  console.log('URL WhatsApp:', whatsappUrl);

  window.open(whatsappUrl, '_blank');
}




}
