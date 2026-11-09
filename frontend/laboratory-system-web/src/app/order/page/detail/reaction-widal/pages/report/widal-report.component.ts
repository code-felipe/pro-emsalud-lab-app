
import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WidalReactionReportResponse } from '../../components/widal-model';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-widal-report',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './widal-report.component.html',
  styleUrl: './widal-report.component.scss'
})
export class WidalReactionReportComponent implements OnInit {

  @Input() widalReactionResults: WidalReactionReportResponse[] = [];


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
  
  private readonly testResultTranslations: Record<string, string> = {
    true: 'Positivo',
    false: 'Negativo'
  };

  private readonly testMethodTranslations: Record<string, string> = {
    CUANTITATIVA: 'Cuantitativo',
    RAPIDA: 'Prueba rápida',
    SEMI_CUANTITATIVA: 'Semi-cuantitativo',
    AGLUTINACION_PORTA: 'Aglutinación en portaobjetos',
    OTROS: 'Otros'
  };

  private readonly titulationTranslation: Record<string, string> ={
    EN_1_20: 'EN 1/20',
    EN_1_40: 'EN 1/40',
    EN_1_80: 'EN 1/80',
    EN_1_160: 'EN 1/160',
    EN_1_320: 'EN 1/320'
  }

  translateTestMethod(method?: string | null): string {
    if (!method) {
      return 'No especificado';
    }

    return this.testMethodTranslations[method] ?? method;
  }

  
  translateResultMethod(method?: string | null): string {
    if (method == null || method === '') {
      return 'No especificado';
    }

    return this.testResultTranslations[method] ?? method;
  }

  translateTitulationResult(method?: string | null): string{

    if(!method){
      return 'No especificado'
    }

    return this.titulationTranslation[method] ?? method;
  }

}
