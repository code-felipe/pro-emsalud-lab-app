
import {
  Component,
  ElementRef,
  OnInit,
  ViewChild
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { OrderReportResponse } from '../../components/order.model';
import { OrderService } from '../../components/order.service';

import { SimpleChemistryReportComponent } from
  '../detail/simple-chemistry/pages/report/simple-chemistry-report.component';

import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

import { WidalReactionReportComponent } from
  '../detail/reaction-widal/pages/report/widal-report.component';

import { UrinalysisReportComponent } from
  '../detail/urinalysis/pages/report/urinalysis-report.component';

import { StoolReportComponent } from
  '../detail/stool/pages/report/stool-report.component';

import { TorchReportComponent } from
  '../detail/torch/pages/report/torch-report.component';

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

  patientId!: number;
  orderId!: number;

  loading = true;
  hasError = false;
  downloadingPdf = false;

  willExceedPage = false;

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

          this.report = response;

          this.loading = false;
          this.hasError = false;

          requestAnimationFrame(() => {
            requestAnimationFrame(() => {
              this.checkReportHeight();
            });
          });

        },

        error: (error) => {

          console.error('Error cargando reporte:', error);

          this.loading = false;
          this.hasError = true;

        }

      });

  }

  // =====================================================
  // CHECK PAGE BREAKS
  // =====================================================


private checkReportHeight(canvas?: HTMLCanvasElement): void {

  if (!this.reportContent) {
    return;
  }

  const element = this.reportContent.nativeElement;
  const rootRect = element.getBoundingClientRect();

  const contentWidth = rootRect.width;

  if (!contentWidth) {
    return;
  }

  const scale = canvas
    ? canvas.width / contentWidth
    : 1;

  // Altura real de una página A4 en píxeles del canvas
  const pageHeight = (canvas?.width ?? contentWidth) * (297 / 210);

  const totalHeight = canvas
    ? canvas.height
    : element.scrollHeight * scale;

  // Margen preventivo de 5 mm
  const safetyMargin = pageHeight * (5 / 297);

  const availablePageHeight = pageHeight - safetyMargin;

  const testBlocks = Array.from(
    element.querySelectorAll<HTMLElement>('.report-test-block')
  );

  let hasCut = false;
  let hasSecondPage = totalHeight > availablePageHeight;

  for (
    let page = 1;
    page * pageHeight < totalHeight;
    page++
  ) {

    const pageBoundary = page * pageHeight;

    for (const block of testBlocks) {

      const rect = block.getBoundingClientRect();

      const blockTop =
        (rect.top - rootRect.top) * scale;

      const blockBottom =
        (rect.bottom - rootRect.top) * scale;

      if (
        pageBoundary > blockTop + 1 &&
        pageBoundary < blockBottom - 1
      ) {

        hasCut = true;

        console.warn(
          'Posible corte de examen:',
          block,
          'Página:',
          page + 1
        );

        break;
      }

    }

    if (hasCut) {
      break;
    }

  }

  // Advertir si hay un examen cortado O si el documento excede una página
  this.willExceedPage = hasCut || hasSecondPage;

  console.log('Validación de paginación:', {
    anchoCanvas: canvas?.width,
    altoCanvas: canvas?.height,
    alturaPagina: pageHeight,
    margenPreventivo: safetyMargin,
    cantidadExamenes: testBlocks.length,
    existeCorte: hasCut,
    excedeUnaPagina: hasSecondPage,
    mostrarAdvertencia: this.willExceedPage
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

      // Validar utilizando el canvas real
      this.checkReportHeight(canvas);

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
