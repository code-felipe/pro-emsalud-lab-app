
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  ActivatedRoute,
  Router,
  RouterLink
} from '@angular/router';

import { HttpErrorResponse } from '@angular/common/http';

import { TestSelectorComponent } from '../../../../../../utils/test-selector/test-selector.component';
import { TestResponse } from '../../../../../../catalog/test/components/test.model';
import { AlertService } from '../../../../../../utils/alert/alert.service';

import { ExamField } from '../../../../../../utils/exam-field/exam-field.component';

import {
  TorchIgG,
  TorchIgM,
  TorchResponse,
  CreateTorchRequest,
  UpdateTorchRequest
} from '../../components/torch.model';

import { TorchService } from '../../components/torch.service';

import {
  TORCH_IGG_FIELDS,
  TORCH_IGM_FIELDS
} from './form-config';


type TorchFormModel = {
  testId: number;
  unitPrice: number;
  torchIgG: TorchIgG;
  torchIgM: TorchIgM;
};


@Component({
  selector: 'app-torch-form',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    TestSelectorComponent
  ],

  templateUrl: './form.component.html',
  styleUrl: './form.component.css'
})
export class TorchFormComponent implements OnInit {

  public torch: TorchFormModel = {

    testId: 0,

    unitPrice: 0,

    torchIgG: {
      toxoplasma: null,
      cytomegalovirus: null,
      rubella: null,
      herpes: null
    },

    torchIgM: {
      toxoplasma: null,
      cytomegalovirus: null,
      rubella: null,
      herpes: null
    }

  };


  public title = 'Nuevo Panel TORCH';

  public apiError: any = null;

  public patientId!: number;

  public orderId!: number;

  public torchId?: number;

  public selectedTest?: TestResponse;


  public torchIgGFields: ExamField<keyof TorchIgG>[] =
    TORCH_IGG_FIELDS;

  public torchIgMFields: ExamField<keyof TorchIgM>[] =
    TORCH_IGM_FIELDS;


  constructor(
    private torchService: TorchService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}


  ngOnInit(): void {
    this.loadRouteParams();
  }


  private loadRouteParams(): void {

    const patientId =
      this.activatedRoute.parent?.snapshot.paramMap.get('id');

    const orderId =
      this.activatedRoute.snapshot.paramMap.get('orderId');

    const id =
      this.activatedRoute.snapshot.paramMap.get('id');


    if (!patientId || !orderId) {

      this.alertService.error(
        'No se pudo identificar el paciente o la orden',
        'Error'
      );

      return;

    }


    this.patientId = +patientId;
    this.orderId = +orderId;


    if (id) {

      this.torchId = +id;

      this.title = 'Editar Panel TORCH';

      this.loadTorch();

    }

  }


  private loadTorch(): void {

    if (!this.torchId) {
      return;
    }


    this.torchService

      .getPatientOrderTestResult(
        this.patientId,
        this.orderId,
        this.torchId
      )

      .subscribe({

        next: (response: TorchResponse) => {

          this.torch = {

            testId: response.testId,

            unitPrice: response.unitPrice,

            torchIgG: {
              ...response.torchIgG
            },

            torchIgM: {
              ...response.torchIgM
            }

          };


          this.selectedTest = {

            id: response.testId,
            name: response.testName,
            testMethod: response.testMethod,
            reference: response.testReference,
            price: response.unitPrice,
            active: true,
            createdAt: '',
            updatedAt: '',
            testCategoryId: 0,
            testCategoryName: response.categoryName

          };


          this.title = 'Editar Panel TORCH';

        },


        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'No se pudo cargar el panel TORCH',
            'Error'
          );

          this.goToResults();

        }

      });

  }


  public onTestSelected(
    test: TestResponse | undefined
  ): void {

    this.selectedTest = test;

    this.torch.testId = test?.id ?? 0;

    this.torch.unitPrice = test?.price ?? 0;

  }


  // =========================================
  // TORCH IgG
  // =========================================

  public getIgGFieldValue(
    field: ExamField<keyof TorchIgG>
  ): string {

    return this.torch.torchIgG[field.name] ?? '';

  }


  public setIgGFieldValue(
    field: ExamField<keyof TorchIgG>,
    value: string
  ): void {

    this.torch.torchIgG[field.name] = value || null;

  }


  // =========================================
  // TORCH IgM
  // =========================================

  public getIgMFieldValue(
    field: ExamField<keyof TorchIgM>
  ): string {

    return this.torch.torchIgM[field.name] ?? '';

  }


  public setIgMFieldValue(
    field: ExamField<keyof TorchIgM>,
    value: string
  ): void {

    this.torch.torchIgM[field.name] = value || null;

  }


private buildTorchIgG(): TorchIgG {

  return {
    toxoplasma: this.torch.torchIgG.toxoplasma || null,
    cytomegalovirus: this.torch.torchIgG.cytomegalovirus || null,
    rubella: this.torch.torchIgG.rubella || null,
    herpes: this.torch.torchIgG.herpes || null
  };

}

private buildTorchIgM(): TorchIgM {

  return {
    toxoplasma: this.torch.torchIgM.toxoplasma || null,
    cytomegalovirus: this.torch.torchIgM.cytomegalovirus || null,
    rubella: this.torch.torchIgM.rubella || null,
    herpes: this.torch.torchIgM.herpes || null
  };

}


  // =========================================
  // BUILD REQUEST
  // =========================================

  private buildRequest(): CreateTorchRequest {

    return {

      testId: this.torch.testId,

      torchIgG: this.buildTorchIgG(),

      torchIgM: this.buildTorchIgM()

    };

  }

  public create(): void {

    this.apiError = null;


    if (!this.torch.testId) {

      this.alertService.error(
        'Debes seleccionar un test',
        'Panel TORCH'
      );

      return;

    }


    const request = this.buildRequest();


    console.log(
      'TORCH REQUEST:',
      JSON.stringify(request, null, 2)
    );


    this.torchService

      .createTestResult(
        this.patientId,
        this.orderId,
        request
      )

      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Panel TORCH'
          );

          this.goToResults();

        },


        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al crear el panel TORCH',
            'Error'
          );

        }

      });

  }

  public update(): void {

    this.apiError = null;


    if (!this.torchId) {

      this.alertService.error(
        'El ID del panel TORCH es requerido',
        'Panel TORCH'
      );

      return;

    }


    if (!this.torch.testId) {

      this.alertService.error(
        'Debes seleccionar un test',
        'Panel TORCH'
      );

      return;

    }


    const request: UpdateTorchRequest =
      this.buildRequest();


    this.torchService

      .updateTestResult(
        this.torchId,
        request
      )

      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Panel TORCH'
          );

          this.goToResults();

        },


        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al actualizar el panel TORCH',
            'Error'

          );

        }

      });

  }

  public goToResults(): void {

    this.router.navigate([

      '/patients',
      this.patientId,
      'orders',
      this.orderId,
      'torchs'

    ]);

  }

//==RESET FORM ==

public resetForm(): void {

  this.torch.torchIgG = {
    toxoplasma: null,
    cytomegalovirus: null,
    rubella: null,
    herpes: null
  };

  this.torch.torchIgM = {
    toxoplasma: null,
    cytomegalovirus: null,
    rubella: null,
    herpes: null
  };

  console.log('🔄 TORCH FORM RESET');
  console.log('IgG:', this.torch.torchIgG);
  console.log('IgM:', this.torch.torchIgM);

}

}
