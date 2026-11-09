import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  ActivatedRoute,
  Router,
  RouterLink
} from '@angular/router';

import { HttpErrorResponse } from '@angular/common/http';

import {
  CreateWidalReactionRequest,
  UpdateWidalReactionRequest,
  WidalReactionResponse,
  WidalResult,
  WidalTitulation
} from '../../components/widal-model';

import { WidalReactionService } from '../../components/widal.service';
import { TestSelectorComponent } from '../../../../../../utils/test-selector/test-selector.component';
import { TestResponse } from '../../../../../../catalog/test/components/test.model';
import { AlertService } from '../../../../../../utils/alert/alert.service';


// =====================================================
// FORM MODEL
// =====================================================

// Permite que los booleanos y las titulaciones permanezcan
// sin seleccionar (null) hasta que el usuario elija.

type WidalFormResult = {
  [K in keyof WidalResult]:
    WidalResult[K] extends boolean
      ? boolean | null
      : WidalResult[K] extends WidalTitulation
        ? WidalTitulation | null
        : WidalResult[K];
};

type WidalFormModel = {
  testId: number;
  unitPrice: number;
  widalResult: WidalFormResult;
};


@Component({
  selector: 'app-widal-reaction-form',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    TestSelectorComponent
  ],

  templateUrl: './form.component.html',
  styleUrl: './form-radios.component.css'
})
export class WidalReactionFormComponent implements OnInit {

  // =====================================================
  // FORM DATA
  // =====================================================

  public widalReaction: WidalFormModel = {
    testId: 0,
    unitPrice: 0,

    widalResult: {
      salmonellaTyphiO_1_20: null,
      salmonellaTyphiO_1_40: null,
      salmonellaTyphiO_1_80: null,
      salmonellaTyphiO_1_160: null,
      salmonellaTyphiO_1_320: null,

      salmonellaTyphiH_1_20: null,
      salmonellaTyphiH_1_40: null,
      salmonellaTyphiH_1_80: null,
      salmonellaTyphiH_1_160: null,
      salmonellaTyphiH_1_320: null,

      salmonellaTyphiOResult: null,
      salmonellaTyphiHResult: null,

      salmonellaTyphiOResultTitulation: null,
      salmonellaTyphiHResultTitulation: null,

      observation: ''
    }
  };


  // =====================================================
  // GENERAL
  // =====================================================

  public title = 'Nueva Reacción de Widal';

  public apiError: any = null;

  public patientId!: number;

  public orderId!: number;

  public widalReactionId?: number;

  public selectedTest?: TestResponse;


  // =====================================================
  // TITULATIONS
  // =====================================================

  public titulations = Object.values(WidalTitulation);

  public titulationLabels: Record<WidalTitulation, string> = {
    [WidalTitulation.EN_1_20]: '1:20',
    [WidalTitulation.EN_1_40]: '1:40',
    [WidalTitulation.EN_1_80]: '1:80',
    [WidalTitulation.EN_1_160]: '1:160',
    [WidalTitulation.EN_1_320]: '1:320'
  };


  // =====================================================
  // CONSTRUCTOR
  // =====================================================

  constructor(
    private widalReactionService: WidalReactionService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}


  // =====================================================
  // INIT
  // =====================================================

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

      this.widalReactionId = +id;

      this.title = 'Editar Reacción de Widal';

      this.loadWidalReaction();
    }
  }


  // =====================================================
  // LOAD WIDAL REACTION
  // =====================================================

  private loadWidalReaction(): void {

    if (!this.widalReactionId) {
      return;
    }

    this.widalReactionService
      .getPatientOrderTestResult(
        this.patientId,
        this.orderId,
        this.widalReactionId
      )
      .subscribe({

        next: (response: WidalReactionResponse) => {

          this.widalReaction = {
            testId: response.testId,
            unitPrice: response.unitPrice,

            widalResult: {
              ...response.widalResult
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

          this.title = 'Editar Reacción de Widal';
        },

        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'No se pudo cargar la reacción de Widal',
            'Error'
          );

          this.goToResults();
        }

      });
  }


  // =====================================================
  // TEST SELECTOR
  // =====================================================

  public onTestSelected(
    test: TestResponse | undefined
  ): void {

    this.selectedTest = test;

    this.widalReaction.testId = test?.id ?? 0;
    this.widalReaction.unitPrice = test?.price ?? 0;
  }


  // =====================================================
  // VALIDATION
  // =====================================================

 public isWidalResultValid(): boolean {

  const result = this.widalReaction.widalResult;

  const booleanFields: (keyof WidalFormResult)[] = [

    'salmonellaTyphiO_1_20',
    'salmonellaTyphiO_1_40',
    'salmonellaTyphiO_1_80',
    'salmonellaTyphiO_1_160',
    'salmonellaTyphiO_1_320',

    'salmonellaTyphiH_1_20',
    'salmonellaTyphiH_1_40',
    'salmonellaTyphiH_1_80',
    'salmonellaTyphiH_1_160',
    'salmonellaTyphiH_1_320',

    'salmonellaTyphiOResult',
    'salmonellaTyphiHResult'
  ];

  const allBooleansSelected = booleanFields.every(
    field => result[field] !== null
  );

  const titulationsSelected =
    !!result.salmonellaTyphiOResultTitulation &&
    !!result.salmonellaTyphiHResultTitulation;

  return allBooleansSelected && titulationsSelected;
}


  // =====================================================
  // BUILD REQUEST
  // =====================================================

  private buildWidalResult(): WidalResult {

    // Seguro: isWidalResultValid() ya garantizó que no hay nulls.
    return {
      ...this.widalReaction.widalResult
    } as unknown as WidalResult;
  }

  private buildRequest(): CreateWidalReactionRequest {

    return {
      testId: this.widalReaction.testId,
      unitPrice: this.widalReaction.unitPrice,
      widalResult: this.buildWidalResult()
    };
  }


  // =====================================================
  // CREATE
  // =====================================================

  public create(): void {

    this.apiError = null;

    if (!this.widalReaction.testId) {

      this.alertService.error(
        'Debes seleccionar un test',
        'Reacción de Widal'
      );

      return;
    }

    if (!this.isWidalResultValid()) {

      this.alertService.error(
        'Debes completar todos los resultados, las titulaciones y la observación',
        'Reacción de Widal'
      );

      return;
    }

    const request = this.buildRequest();

    this.widalReactionService
      .createTestResult(
        this.patientId,
        this.orderId,
        request
      )
      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Reacción de Widal'
          );

          this.goToResults();
        },

        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al crear la reacción de Widal',
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

    if (!this.widalReactionId) {

      this.alertService.error(
        'El ID de la reacción es requerido',
        'Reacción de Widal'
      );

      return;
    }

    if (!this.widalReaction.testId) {

      this.alertService.error(
        'Debes seleccionar un test',
        'Reacción de Widal'
      );

      return;
    }

    if (!this.isWidalResultValid()) {

      this.alertService.error(
        'Debes completar todos los resultados, las titulaciones y la observación',
        'Reacción de Widal'
      );

      return;
    }

    const request: UpdateWidalReactionRequest = this.buildRequest();

    this.widalReactionService
      .updateTestResult(
        this.widalReactionId,
        request
      )
      .subscribe({

        next: (response) => {

          this.alertService.success(
            response.message,
            'Reacción de Widal'
          );

          this.goToResults();
        },

        error: (err: HttpErrorResponse) => {

          this.apiError = err.error;

          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al actualizar la reacción de Widal',
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
      'widal-reactions'
    ]);
  }

}