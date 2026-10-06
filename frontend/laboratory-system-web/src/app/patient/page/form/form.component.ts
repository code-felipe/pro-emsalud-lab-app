import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';

import {
    CreatePatientRequest, PatientResponse,
    Gender,UpdatePatientRequest,
} from '../../components/patient.model';

import { PatientService } from '../../components/patient.service';
import { AlertService } from '../../../utils/alert/alert.service';

@Component({
  selector: 'app-patient-form',
  standalone: true,

  imports: [
    FormsModule,
    RouterLink,
    CommonModule
  ],

  templateUrl: './form.component.html',
})
export class PatientFormComponent implements OnInit {

  public patient: CreatePatientRequest & { id?: number } = {
    gender: undefined as unknown as Gender,
    firstName: '',
    middleName: '',
    fatherLastName: '',
    motherLastName: '',
    dateOfBirth: '',
    primaryPhoneNumber: '',
    optionalPhoneNumber: '',
    city: '',
  };

  public title: string = 'Nuevo Paciente';

  public apiError: any = null;

  public genders = [
    {
      value: Gender.MALE,
      label: 'Masculino'
    },
    {
      value: Gender.FEMALE,
      label: 'Femenino'
    }
  ];

  constructor(
    private patientService: PatientService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}


  ngOnInit(): void {
    this.loadPatient();
  }


  public loadPatient(): void {

    this.activatedRoute.params.subscribe((params) => {

      const id = params['id'];

      if (!id) {
        return;
      }

      this.patientService.getPatient(+id).subscribe({

        next: (response: PatientResponse) => {

          this.patient = {
            id: response.id,
            gender: response.gender,
            firstName: response.firstName,
            middleName: response.middleName,
            fatherLastName: response.fatherLastName,
            motherLastName: response.motherLastName,
            dateOfBirth: response.dateOfBirth,
            primaryPhoneNumber: response.primaryPhoneNumber,
            optionalPhoneNumber: response.optionalPhoneNumber,
            city: response.city,
          };

          this.title = 'Editar Paciente';
        },

        error: (err: HttpErrorResponse) => {

          Swal.fire(
            'Error',
            err.error?.message ?? 'No se pudo cargar el paciente',
            'error'
          ).then(() => {

            this.router.navigate(['/patients']);

          });

        },

      });

    });

  }

  public create(): void {

    this.apiError = null;

    const request: CreatePatientRequest = {
      gender: this.patient.gender,

      firstName: this.patient.firstName.trim(),

      middleName: this.patient.middleName?.trim() ?? '',

      fatherLastName: this.patient.fatherLastName.trim(),

      motherLastName: this.patient.motherLastName.trim(),

      dateOfBirth: this.patient.dateOfBirth,

      primaryPhoneNumber:
        this.patient.primaryPhoneNumber.trim(),

      optionalPhoneNumber:
        this.patient.optionalPhoneNumber?.trim() || undefined,

      city: this.patient.city.trim(),
    };

    this.patientService.createPatient(request).subscribe({

      next: (response) => {

        this.alertService.success(
          response.message,
          'Paciente'
        );

        this.router.navigate(['/patients']);

      },

      error: (err: HttpErrorResponse) => {

        this.apiError = err.error;

        this.alertService.error(
          this.apiError?.message ??
            'Hubo un error al crear el paciente',
          'Error'
        );

      }

    });
  }

  public update(): void {

    this.apiError = null;

    if (!this.patient.id) {

      this.alertService.error(
        'El ID es requerido para actualizar el paciente',
        'Error'
      );

      return;
    }
    const request: UpdatePatientRequest = {

      gender: this.patient.gender,

      firstName: this.patient.firstName.trim(),

      middleName: this.patient.middleName?.trim() ?? '',

      fatherLastName:
        this.patient.fatherLastName.trim(),

      motherLastName:
        this.patient.motherLastName.trim(),

      dateOfBirth:
        this.patient.dateOfBirth,

      primaryPhoneNumber:
        this.patient.primaryPhoneNumber.trim(),

      optionalPhoneNumber:
        this.patient.optionalPhoneNumber?.trim() || undefined,

      city:
        this.patient.city.trim(),
    };


    this.patientService
      .updatePatient(this.patient.id, request)
      .subscribe({

        next: (response: any) => {

          this.alertService.success(
            response.message,
            'Paciente'
          );

          this.router.navigate(['/patients']);

        },

        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          if (!this.apiError?.errors) {

            this.alertService.error(
              this.apiError?.message ??
                'Hubo un error al actualizar el paciente',
              'Error'
            );

          }

        },

      });
  }

}
