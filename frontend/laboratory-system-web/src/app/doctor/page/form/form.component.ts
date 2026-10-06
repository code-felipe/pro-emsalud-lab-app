import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { CreateDoctorRequest, DoctorResponse, Title } from '../../../doctor/components/doctor.model';
import { DoctorService } from '../../../doctor/components/doctor.service';
import { AlertService } from '../../../utils/alert/alert.service';

@Component({
  selector: 'app-doctor-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './form.component.html',
})
export class DoctorFormComponent implements OnInit {

  public doctor: CreateDoctorRequest & { id?: number } = {
    firstName: '',
    lastName: '',
    title: Title.Dr,
  };

  public title: string = 'Nuevo Contacto';

  public apiError: any = null;

  public titles: Title[] = Object.values(Title);

  constructor(
    private doctorService: DoctorService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadDoctor();
  }

  public loadDoctor(): void {
    this.activatedRoute.params.subscribe((params) => {
      const id = params['id'];

      // CREATE
      if (!id) {
        return;
      }

      // EDIT
      this.doctorService.getDoctor(+id).subscribe({
        next: (response: DoctorResponse) => {
          this.doctor = {
            id: response.id,
            firstName: response.firstName,
            lastName: response.lastName,
            title: response.title,
          };
          this.title = 'Editar Doctor';
        },
        error: (err: HttpErrorResponse) => {
          this.alertService.error(
            err.error?.message ?? 'No se pudo cargar la ficha del Doctor',
            'Error'
          );
        },
      });
    });
  }
  public create(): void {
    this.apiError = null;

    this.doctorService.createDoctor(this.doctor).subscribe({
      next: (response) => {
        this.alertService.success(response.message, 'Doctor');
        this.router.navigate(['/doctors']);
      },
      error: (err: HttpErrorResponse) => {
        this.apiError = err.error;
        this.alertService.error(
          this.apiError?.message ?? 'Hubo un error al crear la ficha del doctor',
          'Error'
        );
      },
    });
  }

  public update(): void {
    this.apiError = null;

    if (!this.doctor.id) {
      this.alertService.error(
        'El ID es requerido para actualizar la ficha del doctor',
        'Error'
      );
      return;
    }

    this.doctorService.updateDoctor(this.doctor.id, this.doctor).subscribe({
      next: (response) => {
        this.alertService.success(response.message, 'Doctor');
        this.router.navigate(['/doctors']);
      },
      error: (err: HttpErrorResponse) => {
        this.apiError = err.error;
        this.alertService.error(
          this.apiError?.message ?? 'Hubo un error al actualizar la ficha del doctor',
          'Error'
        );
      },
    });
  }
}