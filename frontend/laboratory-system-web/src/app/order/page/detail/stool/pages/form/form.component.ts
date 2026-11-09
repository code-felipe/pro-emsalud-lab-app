
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
  StoolResult,
  StoolResponse,
  CreateStoolRequest,
  UpdateStoolRequest
} from '../../component/stool.model';

import { StoolService } from '../../component/stool.service';
import { STOOL_FIELDS } from './form-config';



type StoolFormModel = {
  testId: number;
  unitPrice: number;
  stoolResult: StoolResult;
};


@Component({
  selector: 'app-stool-form',
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
export class StoolFormComponent implements OnInit {

  // =====================================================
  // FORM MODEL
  // =====================================================

  public stool: StoolFormModel = {

    testId: 0,

    unitPrice: 0,

    stoolResult: {

      // EXAMEN MACROSCÓPICO

      color: '',
      consistency: '',
      mucus: '',
      pH: '',
      blood: '',
      foodRemains: '',

      // EXAMEN MICROSCÓPICO

      leukocytes: '',
      erythrocytes: '',
      yeast: '',
      vegetableCells: '',
      muscleFibers: '',
      fats: '',
      starches: '',
      parasites: '',
      bacteria: '',

      // OBSERVACIONES

      observation: ''

    }

  };


  // =====================================================
  // COMPONENT STATE
  // =====================================================

  public title = 'Nuevo Coproanálisis';

  public apiError: any = null;

  public patientId!: number;

  public orderId!: number;

  public stoolId?: number;

  public selectedTest?: TestResponse;

  public fields: ExamField<keyof StoolResult>[] = STOOL_FIELDS;


  constructor(
    private stoolService: StoolService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}


  ngOnInit(): void {

    this.loadRouteParams();

  }


  // =====================================================
  // ROUTE PARAMS
  // =====================================================

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


    // EDIT

    if (id) {

      this.stoolId = +id;

      this.title = 'Editar Coproanálisis';

      this.loadStool();

    }

  }


  // =====================================================
  // LOAD STOOL
  // =====================================================

  private loadStool(): void {

    if (!this.stoolId) {
      return;
    }


    this.stoolService
      .getPatientOrderTestResult(
        this.patientId,
        this.orderId,
        this.stoolId
      )
      .subscribe({

        next: (response: StoolResponse) => {

          this.stool = {

            testId: response.testId,

            unitPrice: response.unitPrice,

            stoolResult: {
              ...response.stoolResult
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


          this.title = 'Editar Coproanálisis';

        },


        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'No se pudo cargar el coproanálisis',
            'Error'
          );

          this.goToResults();

        }

      });

  }


  // =====================================================
  // TEST SELECTION
  // =====================================================

  public onTestSelected(
    test: TestResponse | undefined
  ): void {

    this.selectedTest = test;

    this.stool.testId = test?.id ?? 0;

    this.stool.unitPrice = test?.price ?? 0;

  }


  // =====================================================
  // VALIDATION
  // =====================================================

  public isStoolResultValid(): boolean {

    const result = this.stool.stoolResult;

    const requiredFields: (keyof StoolResult)[] = [

      // EXAMEN MACROSCÓPICO

      'color',
      'consistency',
      'mucus',
      'pH',
      'blood',
      'foodRemains',

      // EXAMEN MICROSCÓPICO

      'leukocytes',
      'erythrocytes',
      'yeast',
      'vegetableCells',
      'muscleFibers',
      'fats',
      'starches',
      'parasites',
      'bacteria'

    ];


    return requiredFields.every(field => {

      const value = result[field];

      return typeof value === 'string' &&
        value.trim().length > 0;

    });

  }


  // =====================================================
  // BUILD REQUEST
  // =====================================================

  private buildStoolResult(): StoolResult {

    return {

      ...this.stool.stoolResult,

      observation:
        this.stool.stoolResult.observation?.trim() ?? ''

    };

  }


  private buildRequest(): CreateStoolRequest {

    return {

      testId: this.stool.testId,

      stoolResult: this.buildStoolResult()

    };

  }


  // =====================================================
  // CREATE
  // =====================================================

  public create(): void {

    this.apiError = null;


    if (!this.stool.testId) {

      this.alertService.error(
        'Debes seleccionar un test',
        'Coproanálisis'
      );

      return;

    }


    if (!this.isStoolResultValid()) {

      this.alertService.error(
        'Debes completar todos los resultados del coproanálisis',
        'Coproanálisis'
      );

      return;

    }


    const request = this.buildRequest();


    console.log(
      'STOOL REQUEST:',
      JSON.stringify(request, null, 2)
    );


    this.stoolService
      .createTestResult(
        this.patientId,
        this.orderId,
        request
      )
      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Coproanálisis'
          );

          this.goToResults();

        },


        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al crear el coproanálisis',
            'Error'
          );

        }

      });

  }


  // =====================================================
  // UPDATE
  // =====================================================

  public update(): void {

    this.apiError = null;


    if (!this.stoolId) {

      this.alertService.error(
        'El ID del coproanálisis es requerido',
        'Coproanálisis'
      );

      return;

    }


    if (!this.stool.testId) {

      this.alertService.error(
        'Debes seleccionar un test',
        'Coproanálisis'
      );

      return;

    }


    if (!this.isStoolResultValid()) {

      this.alertService.error(
        'Debes completar todos los resultados del coproanálisis',
        'Coproanálisis'
      );

      return;

    }


    const request: UpdateStoolRequest = this.buildRequest();


    this.stoolService
      .updateTestResult(
        this.stoolId,
        request
      )
      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Coproanálisis'
          );

          this.goToResults();

        },


        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al actualizar el coproanálisis',
            'Error'

          );

        }

      });

  }


  // =====================================================
  // NAVIGATION
  // =====================================================

  public goToResults(): void {

    this.router.navigate([

      '/patients',
      this.patientId,
      'orders',
      this.orderId,
      'stools'

    ]);

  }


  // =====================================================
  // FORM SECTIONS
  // =====================================================

  public sections: string[] = [

    ...new Set(

      this.fields
        .map(field => field.section)
        .filter((section): section is string => !!section)

    )

  ];


  public getFieldsBySection(
    section: string
  ): ExamField<keyof StoolResult>[] {

    return this.fields

      .filter(field => field.section === section)

      .sort((a, b) => a.order - b.order);

  }

  public getFieldValue(field: ExamField<keyof StoolResult>): string {
  return this.stool.stoolResult[field.name] ?? '';
}

public setFieldValue(
  field: ExamField<keyof StoolResult>,
  value: string
): void {
  this.stool.stoolResult[field.name] = value;
}

}
