import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  ActivatedRoute,
  Router,
  RouterLink
} from '@angular/router';

import { HttpErrorResponse } from '@angular/common/http';

import { CKEditorModule } from '@ckeditor/ckeditor5-angular';

import {
  ClassicEditor,
  Essentials,
  Paragraph,
  Bold,
  Italic,
  Link,
  List,
  Table,
  TableToolbar
} from 'ckeditor5';

import { AlertService } from '../../../utils/alert/alert.service';

import { TestResultService } from '../../components/result.service';

import {
  CreateTestResultRequest,
  UpdateTestResultRequest,
  TestResultResponse
} from '../../components/result.model';

import { TestResponse } from '../../../catalog/test/components/test.model';

import { TestSelectorComponent } from '../../../utils/test-selector/test-selector.component';


@Component({
  selector: 'app-test-result-form',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    TestSelectorComponent,
    CKEditorModule
  ],

  templateUrl: './form.component.html',
})
export class TestResultFormComponent implements OnInit {

  // =====================================================
  // CKEDITOR 5
  // =====================================================

  public Editor = ClassicEditor;

  public editorConfig = {
    licenseKey: 'GPL',

    plugins: [
      Essentials,
      Paragraph,
      Bold,
      Italic,
      Link,
      List,
      Table,
      TableToolbar
    ],

    toolbar: [
      'undo',
      'redo',
      '|',
      'bold',
      'italic',
      '|',
      'link',
      'bulletedList',
      'numberedList',
      '|',
      'insertTable'
    ],

    table: {
      contentToolbar: [
        'tableColumn',
        'tableRow',
        'mergeTableCells'
      ]
    },

    placeholder: 'Escribe el resultado del test...',
  };


  // =====================================================
  // TEST RESULT
  // =====================================================

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


  constructor(
    private testResultService: TestResultService,
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
  // VALIDATE EDITOR CONTENT
  // =====================================================

  public hasResultContent(): boolean {

    const content = this.testResult.result ?? '';

    const textContent = content
      .replace(/<br\s*\/?>/gi, '')
      .replace(/<\/p>/gi, '')
      .replace(/<[^>]*>/g, '')
      .replace(/&nbsp;|&#160;|&#xA0;/gi, ' ')
      .replace(/&amp;/gi, '&')
      .trim();

    // Admitir tablas aunque sus celdas estén vacías.
    const hasTable = /<table(?:\s|>)/i.test(content);

    return textContent.length > 0 || hasTable;
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
            result: response.result ?? '',
            unitPrice: response.unitPrice,
          };

          this.selectedTest = {
            id: response.testId,
            name: response.testName,
            group: response.testGroup,
            testMethod: response.testMethod,
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

    if (!this.hasResultContent()) {

      this.alertService.error(
        'Debes ingresar el resultado',
        'Resultado'
      );

      return;
    }

    const request: CreateTestResultRequest = {
      ...this.testResult,
      result: this.testResult.result.trim(),
    };

    this.testResultService
      .createTestResult(
        this.patientId,
        this.orderId,
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
              'Hubo un error al crear el resultado',
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

    if (!this.hasResultContent()) {

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
