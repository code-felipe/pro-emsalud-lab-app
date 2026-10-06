import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import {
  CreateOrderRequest,
  OrderResponse,
  UpdateOrderRequest,
} from '../../components/order.model';

import { OrderService } from '../../components/order.service';

import { DoctorResponse } from '../../../doctor/components/doctor.model';
import { DoctorService } from '../../../doctor/components/doctor.service';
import { AlertService } from '../../../utils/alert/alert.service';

@Component({
  selector: 'app-order-form',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
  ],

  templateUrl: './form.component.html',
})
export class OrderFormComponent implements OnInit {

  public order: CreateOrderRequest & { id?: number } = {
    doctorId: 0,
  };

  public title: string = 'Nueva Orden';

  public apiError: any = null;

  public patientId!: number;

  public doctors: DoctorResponse[] = [];


  constructor(
    private orderService: OrderService,
    private doctorService: DoctorService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}


  ngOnInit(): void {

    const patientId = this.activatedRoute.parent?.snapshot.paramMap.get('id');

    if (!patientId) {

      this.alertService.error(
        'No se pudo identificar el paciente',
        'Error'
      );

      return;
    }

    this.patientId = +patientId;

    this.loadDoctors();

    this.loadOrder();
  }


  // =====================================================
  // LOAD ORDER
  // =====================================================

  public loadOrder(): void {

    this.activatedRoute.params.subscribe((params) => {

      const id = params['id'];

      // CREATE
      if (!id) {
        return;
      }

      // EDIT
      this.orderService.getOrder(+id).subscribe({

        next: (response: OrderResponse) => {

          this.order = {
            id: response.id,
            doctorId: response.doctorId,
          };

          this.title = 'Editar Orden';
        },

        error: (err: HttpErrorResponse) => {

          this.alertService.error(
            err.error?.message ?? 'No se pudo cargar la orden',
            'Error'
          );

          this.goToResults();
        },

      });

    });

  }


  // =====================================================
  // LOAD DOCTORS
  // =====================================================

  private loadDoctors(): void {

    this.doctorService
      .getDoctors(0, 100)
      .subscribe({

        next: (response) => {

          this.doctors = response.content;

        },

        error: (err) => {

          console.error(
            'Error cargando lista de doctores',
            err
          );

        },

      });

  }


  // =====================================================
  // CREATE
  // =====================================================

  public create(): void {

    this.apiError = null;

    if (!this.order.doctorId) {

      this.alertService.error(
        'Debes seleccionar un doctor',
        'Orden'
      );

      return;
    }

    const request: CreateOrderRequest = {
      doctorId: this.order.doctorId,
    };

    this.orderService
      .createOrder(this.patientId, request)
      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Orden'
          );

          this.goToResults();
        },

        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al crear la orden',
            'Error'
          );

        },

      });
  }


  // =====================================================
  // UPDATE
  // =====================================================

  public update(): void {

    this.apiError = null;

    if (!this.order.id) {

      this.alertService.error(
        'El ID es requerido para actualizar la orden',
        'Error'
      );

      return;
    }

    if (!this.order.doctorId) {

      this.alertService.error(
        'Debes seleccionar un doctor',
        'Orden'
      );

      return;
    }

    const request: UpdateOrderRequest = {
      doctorId: this.order.doctorId,
    };

    this.orderService
      .updateOrder(this.order.id, request)
      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Orden'
          );

          this.goToResults();
        },

        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al actualizar la orden',
            'Error'
          );

        },

      });
  }


  // =====================================================
  // NAVIGATION
  // =====================================================

  private goToResults(): void {

    this.router.navigate([
      '/patients',
      this.patientId,
      'results',
    ]);

  }

}