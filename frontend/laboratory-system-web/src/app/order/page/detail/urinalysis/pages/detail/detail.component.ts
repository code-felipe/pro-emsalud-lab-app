import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

import { TestMethod } from '../../../../../../catalog/test/components/test.model';
import { UrinalysisResponse } from '../../component/urinalysis.model';
import { UrinalysisService } from '../../component/urinalysis.service';

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
export class UrinalysisDetailComponent implements OnInit {

  test: UrinalysisResponse | null = null;

  title: string = 'Detalle de la Prueba';

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
  constructor(
    private route: ActivatedRoute,
    private testService: UrinalysisService
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
      Swal.fire('Error', 'El ID de la prueba no es válido', 'error');
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
          error.error?.message ?? 'El ID de la prueba no existe',
          'error'
        );
      }

    });
  }
}