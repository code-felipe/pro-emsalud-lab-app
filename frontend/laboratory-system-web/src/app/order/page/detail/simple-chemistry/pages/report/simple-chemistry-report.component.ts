import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SimpleChemistryReportResponse } from '../../components/simple-chemistry.model';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-simple-chemistry-report',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './simple-chemistry-report.component.html',
  styleUrl: './simple-chemistry-report.component.scss'
})
export class SimpleChemistryReportComponent implements OnInit  {

  @Input() results: SimpleChemistryReportResponse[] = [];

  public patientId!: number;
  public orderId!: number;

  constructor(
    private route: ActivatedRoute,

  ) {}

  ngOnInit(): void {
    this.loadRouteParams();
  }

  private loadRouteParams(): void {

    const patientId = this.route.parent?.snapshot.paramMap.get('id');
    const orderId = this.route.snapshot.paramMap.get('orderId');

    if (patientId) {
      this.patientId = +patientId;
    }

    if (orderId) {
      this.orderId = +orderId;
    }
  }

  private readonly testMethodTranslations: Record<string, string> = {
    CUANTITATIVA: 'Cuantitativo',
    RAPIDA: 'Prueba rápida',
    SEMI_CUANTITATIVA: 'Semi-cuantitativo',
    AGLUTINACION_PORTA: 'Aglutinación en portaobjetos',
    OTROS: 'Otros'
  };

translateTestMethod(method?: string | null): string {
    if (!method) {
      return 'No especificado';
    }

    return this.testMethodTranslations[method] ?? method;
  }


get hasAnyReference(): boolean {
  return this.results.some(r => this.hasReference(r.reference));
}

hasReference(reference: any): boolean {
  return typeof reference === 'string' && reference.trim().length > 0;
}


}