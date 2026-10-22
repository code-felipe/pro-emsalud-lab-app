import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  ActivatedRoute,
  Router,
  RouterLink
} from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { AlertService } from '../../../utils/alert/alert.service';

import { TestResultService } from '../../components/result.service';

import {
  CreateTestResultRequest,
  UpdateTestResultRequest,
  TestResultResponse
} from '../../components/result.model';

import { TestResponse } from '../../../catalog/test/components/test.model';

import { TestSelectorComponent } from '../../../utils/test-selector/test-selector.component';
import { TEST_TEMPLATE_RESULT } from '../../../utils/templates/templates';


@Component({
  selector: 'app-test-result-form',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    TestSelectorComponent,
  ],

  templateUrl: './form.component.html',
})
export class TestResultFormComponent implements OnInit {

  public testResult: CreateTestResultRequest = {
    result: '',
    unitPrice: 0,
    testId: 0,
  };

  public title: string = 'Nuevo Resultado';

  public apiError: any = null;

  public patientId!: number;

  public orderId!: number;

  public testResultId?: number;

  public selectedTest?: TestResponse;

public templates = TEST_TEMPLATE_RESULT;

public templateMenuOpen = false;

public insertTemplate(content: string): void {
  this.testResult.result = content;
  this.templateMenuOpen = false;
}
  


  constructor(
    private testResultService: TestResultService,
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

    const testResultId =
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

    if (testResultId) {

      this.testResultId = +testResultId;

      this.title = 'Editar Resultado';

      this.loadTestResult();

    }

  }


  // =====================================================
  // LOAD TEST RESULT
  // =====================================================

private loadTestResult(): void {

  if (!this.testResultId) {
    return;
  }

  this.testResultService
    .getPatientOrderTestResult(
      this.patientId,
      this.orderId,
      this.testResultId
    )
    .subscribe({

      next: (response: TestResultResponse) => {

        this.testResult = {
          testId: response.testId,
          result: response.result,
          unitPrice: response.unitPrice,
        };

        this.selectedTest = {
          id: response.testId,
          name: response.testName,
          testType: response.testType,
          reference: response.testReference,
          price: response.unitPrice,
          active: true,
          createdAt: '',
          updatedAt: '',
          testCategoryId: 0,
          testCategoryName: response.categoryName,
        };

        this.title = 'Editar Resultado';
      },

      error: (err: HttpErrorResponse) => {

        this.apiError = err.error;

        this.alertService.error(
          this.apiError?.message ??
            'No se pudo cargar el resultado',
          'Error'
        );

        this.goToResults();
      },

    });
}



  // =====================================================
  // TEST SELECTOR
  // =====================================================

  public onTestSelected(
    test: TestResponse | undefined
  ): void {

    this.selectedTest = test;

    this.testResult.testId = test?.id ?? 0;

  }


  // =====================================================
  // CREATE
  // =====================================================

  public create(): void {

    this.apiError = null;


    if (!this.testResult.testId) {

      this.alertService.error(
        'Debes seleccionar un test',
        'Resultado'
      );

      return;
    }


    if (!this.testResult.result.trim()) {

      this.alertService.error(
        'Debes ingresar el resultado',
        'Resultado'
      );

      return;
    }


    this.testResultService
      .createTestResult(
        this.patientId,
        this.orderId,
        this.testResult
      )
      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Resultado'
          );

          this.goToResults();

        },

        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al crear el resultado',
            'Error'
          );

        },

      });

  }

public update(): void {

  this.apiError = null;

  if (!this.testResultId) {

    this.alertService.error(
      'El ID del resultado es requerido',
      'Resultado'
    );

    return;
  }

  if (!this.testResult.testId) {

    this.alertService.error(
      'Debes seleccionar un test',
      'Resultado'
    );

    return;
  }

  if (!this.testResult.result.trim()) {

    this.alertService.error(
      'Debes ingresar el resultado',
      'Resultado'
    );

    return;
  }

  const request: UpdateTestResultRequest = {
    testId: this.testResult.testId,
    result: this.testResult.result.trim()
  };

  this.testResultService
    .updateTestResult(
      this.testResultId,
      request
    )
    .subscribe({

      next: (response) => {

        this.alertService.success(
          response.message,
          'Resultado'
        );

        this.goToResults();

      },

      error: (err: HttpErrorResponse) => {

        this.apiError = err.error;

        this.alertService.error(
          this.apiError?.message ??
            'Hubo un error al actualizar el resultado',
          'Error'
        );

      },

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
      'test-results',
    ]);

  }

}
