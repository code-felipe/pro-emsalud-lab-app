
import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TorchReportResponse } from '../../components/torch.model';

@Component({
  selector: 'app-torch-report',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './torch-report.component.html',
  styleUrl: './torch-report.component.scss'
})
export class TorchReportComponent implements OnInit {

  @Input() torchResults: TorchReportResponse[] = [];

  public patientId!: number;
  public orderId!: number;

  constructor(
    private route: ActivatedRoute
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


public displayResult(value?: string | null): string {
  if (value == null || value.trim() === '') {
    return 'N/A';
  }

  return value;
}

public getResultClass(value?: string | null): string {
  if (!value || value.trim() === '') {
    return 'result-negative';
  }

  return value.toUpperCase() === 'POSITIVO'
    ? 'result-positive'
    : 'result-negative';
}


}
