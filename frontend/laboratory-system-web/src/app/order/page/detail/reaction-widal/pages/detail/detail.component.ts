import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

import { WidalReactionService } from '../../components/widal.service';
import {
  WidalReactionResponse,
  WidalTitulation
} from '../../components/widal-model';

import { TestMethod } from '../../../../../../catalog/test/components/test.model';

@Component({
  selector: 'app-widal-reaction-detail',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    CommonModule
  ],
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.css',
})
export class WidalReactionDetailComponent implements OnInit {

  test: WidalReactionResponse | null = null;

  title: string = 'Detalle de la Prueba Reacción de Widal';

  hasError = false;

  public patientId!: number;
  public orderId!: number;

  testMethodLabels: Record<TestMethod, string> = {
    [TestMethod.CUANTITATIVA]: 'Cuantitativa',
    [TestMethod.RAPIDA]: 'Rápida',
    [TestMethod.SEMI_CUANTITATIVA]: 'Semi-cuantitativa',
    [TestMethod.AGLUTINACION_PORTA]: 'Aglutinación porta',
    [TestMethod.OTROS]: 'Otros',
  };

  widalTitulationLabels: Record<WidalTitulation, string> = {
    [WidalTitulation.EN_1_20]: '1:20',
    [WidalTitulation.EN_1_40]: '1:40',
    [WidalTitulation.EN_1_80]: '1:80',
    [WidalTitulation.EN_1_160]: '1:160',
    [WidalTitulation.EN_1_320]: '1:320'
  };

  widalODilutions = [
    { label: '1:20', key: 'salmonellaTyphiO_1_20' },
    { label: '1:40', key: 'salmonellaTyphiO_1_40' },
    { label: '1:80', key: 'salmonellaTyphiO_1_80' },
    { label: '1:160', key: 'salmonellaTyphiO_1_160' },
    { label: '1:320', key: 'salmonellaTyphiO_1_320' }
  ] as const;

  widalHDilutions = [
    { label: '1:20', key: 'salmonellaTyphiH_1_20' },
    { label: '1:40', key: 'salmonellaTyphiH_1_40' },
    { label: '1:80', key: 'salmonellaTyphiH_1_80' },
    { label: '1:160', key: 'salmonellaTyphiH_1_160' },
    { label: '1:320', key: 'salmonellaTyphiH_1_320' }
  ] as const;

  constructor(
    private route: ActivatedRoute,
    private testService: WidalReactionService
  ) {}

  ngOnInit(): void {
    this.loadRouteParams();
    this.loadTest();
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

  loadTest(): void {

    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (!id || isNaN(id)) {
      this.hasError = true;
      Swal.fire('Error', 'El ID de la reacción de Widal no es válido', 'error');
      return;
    }

    this.testService.getTestResult(id).subscribe({

      next: (response) => {
        this.test = response;
        this.hasError = false;

        // Respaldo: si la ruta no trajo orderId, se toma de la respuesta
        if (!this.orderId) {
          this.orderId = response.orderId;
        }
      },

      error: (error) => {
        this.hasError = true;

        Swal.fire(
          'Error',
          error.error?.message ?? 'El ID de la reacción de Widal no existe',
          'error'
        );
      }

    });
  }
}