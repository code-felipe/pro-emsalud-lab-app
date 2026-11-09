
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
import { CreateUrinalysisRequest, UpdateUrinalysisRequest, UrinalysisResponse, UrinalysisResult } from '../../component/urinalysis.model';
import { URINALYSIS_FIELDS } from './form-config';
import { UrinalysisService } from '../../component/urinalysis.service';


type UrinalysisFormModel = {
  testId: number;
  unitPrice: number;
  urinalysisResult: UrinalysisResult;
};


@Component({
  selector: 'app-urinalysis-form',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    TestSelectorComponent
    // Agregar aquí ExamFieldComponent cuando confirmemos
    // su API de inputs y outputs.
  ],

  templateUrl: './form.component.html',
  styleUrl: './form.component.css'
})
export class UrinalysisFormComponent implements OnInit {

  public urinalysis: UrinalysisFormModel = {

    testId: 0,

    unitPrice: 0,

    urinalysisResult: {

      // EXAMEN FISICO

      color: '',
      appearance: '',
      specificGravity: '',

      // EXAMEN QUIMICO

      pH: '',
      leukocytes: '',
      nitrites: '',
      urobilinogen: '',
      protein: '',
      glucose: '',
      ketones: '',
      bilirubin: '',
      erythrocytes: '',

      // EXAMEN MICROSCOPICO

      microscopicLeukocytes: '',
      microscopicErythrocytes: '',
      epithelialCells: '',
      bacteria: '',
      mucus: '',
      crystals: '',
      yeast: '',
      others: '',

      observation: ''
    }
  };


  public title = 'Nuevo Uroanálisis';

  public apiError: any = null;

  public patientId!: number;

  public orderId!: number;

  public urinalysisId?: number;

  public selectedTest?: TestResponse;

  public fields: ExamField<keyof UrinalysisResult>[] = URINALYSIS_FIELDS;


  constructor(
    private urinalysisService: UrinalysisService,
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


    // EDIT

    if (id) {

      this.urinalysisId = +id;

      this.title = 'Editar Uroanálisis';

      this.loadUrinalysis();

    }

  }


  private loadUrinalysis(): void {

    if (!this.urinalysisId) {
      return;
    }

    this.urinalysisService
      .getPatientOrderTestResult(
        this.patientId,
        this.orderId,
        this.urinalysisId
      )
      .subscribe({

        next: (response: UrinalysisResponse) => {

          this.urinalysis = {

            testId: response.testId,

            unitPrice: response.unitPrice,

            urinalysisResult: {
              ...response.urinalysisResult
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


          this.title = 'Editar Uroanálisis';

        },


        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'No se pudo cargar el uroanálisis',
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

    this.urinalysis.testId = test?.id ?? 0;

    this.urinalysis.unitPrice = test?.price ?? 0;

  }


  // =====================================================
  // VALIDATION
  // =====================================================

  public isUrinalysisResultValid(): boolean {

    const result = this.urinalysis.urinalysisResult;

    const requiredFields: (keyof UrinalysisResult)[] = [

      // EXAMEN FISICO

      'color',
      'appearance',
      'specificGravity',

      // EXAMEN QUIMICO

      'pH',
      'leukocytes',
      'nitrites',
      'urobilinogen',
      'protein',
      'glucose',
      'ketones',
      'bilirubin',
      'erythrocytes',

      // EXAMEN MICROSCOPICO

      'microscopicLeukocytes',
      'microscopicErythrocytes',
      'epithelialCells',
      'bacteria',
      'mucus',
      'crystals',
      'yeast',
      'others'

    ];


    return requiredFields.every(field => {

      const value = result[field];

      return typeof value === 'string' && value.trim().length > 0;

    });

  }


  // =====================================================
  // BUILD REQUEST
  // =====================================================

  private buildUrinalysisResult(): UrinalysisResult {

    return {

      ...this.urinalysis.urinalysisResult,

      observation:
        this.urinalysis.urinalysisResult.observation?.trim() ?? ''

    };

  }


  private buildRequest(): CreateUrinalysisRequest {

    return {

      testId: this.urinalysis.testId,

      urinalysisResult: this.buildUrinalysisResult()

    };

  }


  // =====================================================
  // CREATE
  // =====================================================

  public create(): void {

    this.apiError = null;


    if (!this.urinalysis.testId) {

      this.alertService.error(
        'Debes seleccionar un test',
        'Uroanálisis'
      );

      return;

    }


    if (!this.isUrinalysisResultValid()) {

      this.alertService.error(
        'Debes completar todos los resultados del uroanálisis',
        'Uroanálisis'
      );

      return;

    }


    const request = this.buildRequest();

    console.log('URINALYSIS REQUEST:', JSON.stringify(request, null, 2));
    this.urinalysisService
      .createTestResult(
        this.patientId,
        this.orderId,
        request
      )
      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Uroanálisis'
          );

          this.goToResults();

        },


        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al crear el uroanálisis',
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


    if (!this.urinalysisId) {

      this.alertService.error(
        'El ID del uroanálisis es requerido',
        'Uroanálisis'
      );

      return;

    }


    if (!this.urinalysis.testId) {

      this.alertService.error(
        'Debes seleccionar un test',
        'Uroanálisis'
      );

      return;

    }


    if (!this.isUrinalysisResultValid()) {

      this.alertService.error(
        'Debes completar todos los resultados del uroanálisis',
        'Uroanálisis'
      );

      return;

    }


    const request: UpdateUrinalysisRequest = this.buildRequest();


    this.urinalysisService
      .updateTestResult(
        this.urinalysisId,
        request
      )
      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Uroanálisis'
          );

          this.goToResults();

        },


        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al actualizar el uroanálisis',
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
      'urinalysis'

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
): ExamField<keyof UrinalysisResult>[] {

  return this.fields
    .filter(field => field.section === section)
    .sort((a, b) => a.order - b.order);

}

  

}
