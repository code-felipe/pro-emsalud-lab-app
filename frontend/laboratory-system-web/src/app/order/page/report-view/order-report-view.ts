
import {
  Component,
  ElementRef,
  OnInit,
  ViewChild
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { OrderReportResponse } from '../../components/order.model';
import { OrderService } from '../../components/order.service';

import { SimpleChemistryReportComponent } from
  '../detail/simple-chemistry/pages/report/simple-chemistry-report.component';

import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import { WidalReactionReportComponent } from '../detail/reaction-widal/pages/report/widal-report.component';
import { UrinalysisReportComponent } from '../detail/urinalysis/pages/report/urinalysis-report.component';
import { StoolReportComponent } from '../detail/stool/pages/report/stool-report.component';
import { TorchReportComponent } from '../detail/torch/pages/report/torch-report.component';

@Component({
  selector: 'app-order-report-view',
  standalone: true,
  imports: [
    CommonModule,
    SimpleChemistryReportComponent,
    WidalReactionReportComponent,
    UrinalysisReportComponent,
    StoolReportComponent,
    TorchReportComponent
  ],
  templateUrl: './order-report-view.html',
  styleUrl: './order-report-view.scss'
})
export class OrderReportView implements OnInit {

  @ViewChild('reportContent')
  reportContent!: ElementRef<HTMLElement>;

  report?: OrderReportResponse;

  // Identificadores de navegación
  patientId!: number;
  orderId!: number;

  loading = true;
  hasError = false;
  downloadingPdf = false;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService
  ) {}

  // =====================================================
  // INIT
  // =====================================================

  ngOnInit(): void {

    this.patientId = Number(
      this.route.parent?.snapshot.paramMap.get('id')
    );

    this.orderId = Number(
      this.route.snapshot.paramMap.get('orderId')
    );

    console.log('Patient ID:', this.patientId);
    console.log('Order ID:', this.orderId);

    if (
      !Number.isInteger(this.patientId) ||
      this.patientId <= 0 ||
      !Number.isInteger(this.orderId) ||
      this.orderId <= 0
    ) {

      this.loading = false;
      this.hasError = true;

      console.error('Parámetros inválidos de la ruta');

      return;
    }

    this.loadOrderReport();
  }

  // =====================================================
  // LOAD REPORT
  // =====================================================

  private loadOrderReport(): void {

    this.orderService
      .getOrderReport(this.patientId, this.orderId)
      .subscribe({

        next: (response) => {

          console.log('Reporte recibido:', response);
          console.log(
            'Químicas simples:',
            response.simpleChemistries
          );
            console.log('Reporte recibido:', response);
          console.log(
            'Stools:',
            response.stools
          );

          this.report = response;

          this.loading = false;
          this.hasError = false;

        },

        error: (error) => {

          console.error('Error cargando reporte:', error);

          this.loading = false;
          this.hasError = true;

        }

      });
  }

  // =====================================================
  // DOWNLOAD PDF
  // =====================================================

  async downloadPdf(): Promise<void> {

    if (!this.reportContent || this.downloadingPdf) {
      return;
    }

    this.downloadingPdf = true;

    try {

      const element = this.reportContent.nativeElement;

      const canvas = await html2canvas(element, {
        scale: 2,
        useCORS: true,
        backgroundColor: '#ffffff',
        logging: false
      });

      const image = canvas.toDataURL('image/png');

      const pdf = new jsPDF('p', 'mm', 'a4');

      const pageWidth = pdf.internal.pageSize.getWidth();
      const pageHeight = pdf.internal.pageSize.getHeight();

      const imageHeight =
        (canvas.height * pageWidth) / canvas.width;

      let heightLeft = imageHeight;
      let position = 0;

      pdf.addImage(
        image,
        'PNG',
        0,
        position,
        pageWidth,
        imageHeight
      );

      heightLeft -= pageHeight;

      while (heightLeft > 0) {

        position = heightLeft - imageHeight;

        pdf.addPage();

        pdf.addImage(
          image,
          'PNG',
          0,
          position,
          pageWidth,
          imageHeight
        );

        heightLeft -= pageHeight;
      }

      const reportId = this.report?.id ?? 'sin-id';

      pdf.save(`Reporte-Orden-${reportId}.pdf`);

    } catch (error) {

      console.error('Error generando PDF:', error);

    } finally {

      this.downloadingPdf = false;

    }
  }

  // =====================================================
  // WHATSAPP
  // =====================================================

  openWhatsApp(): void {

    if (!this.report) {
      return;
    }

    const phone =
      this.report.patientPhoneNumber?.replace(/\D/g, '');

    if (!phone) {
      return;
    }

    const message = encodeURIComponent(
      `Hola ${this.report.patientFullName}, le compartimos información sobre su reporte de laboratorio de la orden #${this.report.id}.`
    );

    window.open(
      `https://wa.me/${phone}?text=${message}`,
      '_blank',
      'noopener,noreferrer'
    );
  }

}
