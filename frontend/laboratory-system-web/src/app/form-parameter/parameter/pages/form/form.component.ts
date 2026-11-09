
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';
import { CreateParameterRequest, ExamType, InputType, ParameterResponse, UpdateParameterRequest } from '../../component/parameter.model';
import { ParameterService } from '../../component/parameter.service';
import { AlertService } from '../../../../utils/alert/alert.service';



@Component({
  selector: 'app-parameter-form',
  standalone: true,

  imports: [
    FormsModule,
    RouterLink,
    CommonModule
  ],

  templateUrl: './form.component.html',
})
export class ParameterFormComponent implements OnInit {

  public InputType = InputType;

  public parameter: CreateParameterRequest & { id?: number } = {
    name: '',
    examType: undefined as unknown as ExamType,
    inputType: undefined as unknown as InputType,
    displayOrder: 1,
    options: []
  };

  public title: string = 'Nuevo Parámetro';

  public apiError: any = null;

  public examTypes = [
    {
      value: ExamType.URINALYSIS,
      label: 'Uroanálisis'
    },
    {
      value: ExamType.STOOL,
      label: 'Heces'
    }
  ];

  public inputTypes = [
    {
      value: InputType.SELECT,
      label: 'Select'
    },
    {
      value: InputType.RADIO,
      label: 'Radio'
    },
    {
      value: InputType.TEXT,
      label: 'Texto'
    },
    {
      value: InputType.NUMBER,
      label: 'Número'
    }
  ];

  constructor(
    private parameterService: ParameterService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadParameter();
  }

  public loadParameter(): void {

    this.activatedRoute.params.subscribe(params => {

      const id = params['id'];

      if (!id) {
        return;
      }

      this.parameterService.getParameter(+id).subscribe({

        next: (response: ParameterResponse) => {

          this.parameter = {
            id: response.id,
            name: response.name,
            examType: response.examType,
            inputType: response.inputType,
            displayOrder: response.displayOrder,
            options: (response.options ?? []).map(option => ({
              id: option.id,
              value: option.value,
              displayOrder: option.displayOrder
            }))
          };

          this.title = 'Editar Parámetro';
        },

        error: (err: HttpErrorResponse) => {

          Swal.fire(
            'Error',
            err.error?.message ?? 'No se pudo cargar el parámetro',
            'error'
          ).then(() => {
            this.router.navigate(['/parameters']);
          });

        }

      });

    });
  }

  public create(): void {

    this.apiError = null;

    const request: CreateParameterRequest = {

      name: this.parameter.name.trim(),

      examType: this.parameter.examType,

      inputType: this.parameter.inputType,

      displayOrder: this.parameter.displayOrder,

      options: this.parameter.options
        .filter(option => option.value.trim() !== '')
        .map((option, index) => ({
          value: option.value.trim(),
          displayOrder: index + 1
        }))

    };

    this.parameterService.createParameter(request).subscribe({

      next: response => {

        this.alertService.success(
          response.message,
          'Parámetro'
        );

        this.router.navigate(['/parameters']);

      },

      error: (err: HttpErrorResponse) => {

        this.apiError = err.error;

        this.alertService.error(
          this.apiError?.message ??
          'Hubo un error al crear el parámetro',
          'Error'
        );

      }

    });
  }

  public update(): void {

    this.apiError = null;

    if (!this.parameter.id) {

      this.alertService.error(
        'El ID es requerido para actualizar el parámetro',
        'Error'
      );

      return;
    }

    const request: UpdateParameterRequest = {

      name: this.parameter.name.trim(),

      examType: this.parameter.examType,

      inputType: this.parameter.inputType,

      displayOrder: this.parameter.displayOrder,

      options: this.parameter.options
        .filter(option => option.value.trim() !== '')
        .map((option, index) => ({
          id: option.id,
          value: option.value.trim(),
          displayOrder: index + 1
        }))

    };

    this.parameterService
      .updateParameter(this.parameter.id, request)
      .subscribe({

        next: response => {

          this.alertService.success(
            response.message,
            'Parámetro'
          );

          this.router.navigate(['/parameters']);

        },

        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          if (!this.apiError?.errors) {

            this.alertService.error(
              this.apiError?.message ??
              'Hubo un error al actualizar el parámetro',
              'Error'
            );

          }

        }

      });
  }

public addOption(): void {
  this.parameter.options.push({
    value: '',
    displayOrder: this.parameter.options.length + 1
  });
}

  public removeOption(index: number): void {

    this.parameter.options.splice(index, 1);

    this.parameter.options.forEach((option, i) => {
      option.displayOrder = i + 1;
    });

  }

}
