
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

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
  TableToolbar,
} from 'ckeditor5';

import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';

import {
  CreateTestRequest,
  TestMethod,
  TestResponse,
  UpdateTestRequest,
} from '../../components/test.model';

import { TestService } from '../../components/test.service';

import { TestCategoryResponse } from '../../../test-category/components/test-category.model';
import { TestCategoryService } from '../../../test-category/components/test-category.service';
import { AlertService } from '../../../../utils/alert/alert.service';

@Component({
  selector: 'app-test-form',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    CommonModule,
    CKEditorModule,
  ],
  templateUrl: './form.component.html',
})
export class TestFormComponent implements OnInit {

  public test: CreateTestRequest & { id?: number } = {
    name: '',
    group: '',
    testMethod: undefined as unknown as TestMethod,
    reference: '',
    price: 0,
    testCategoryId: 0,
  };

  public title: string = 'Nueva Prueba Referencial';

  public apiError: any = null;

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
    TableToolbar,
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
    'insertTable',
  ],
  table: {
    contentToolbar: [
      'tableColumn',
      'tableRow',
      'mergeTableCells',
    ],
  },
  placeholder: 'Escribe los datos de la prueba...',
};

  public testMethods = [
    {
      value: TestMethod.RAPIDA,
      label: 'Rápida',
    },
    {
      value: TestMethod.CUANTITATIVA,
      label: 'Cuantitativa',
    },
    {
      value: TestMethod.SEMI_CUANTITATIVA,
      label: 'Semi Cuantitativa',
    },
     {
      value: TestMethod.AGLUTINACION_PORTA,
      label: 'Aglutinación en Porta',
    },
    {
      value: TestMethod.OTROS,
      label: 'Otros',
    },
  ];

  public testCategories: TestCategoryResponse[] = [];

  constructor(
    private testService: TestService,
    private testCategoryService: TestCategoryService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadTestCategories();
    this.loadTest();
  }

  // =====================================================
  // LOAD TEST
  // =====================================================

  public loadTest(): void {
    this.activatedRoute.params.subscribe((params) => {
      const id = params['id'];

      // CREATE
      if (!id) {
        return;
      }

      // EDIT
      this.testService.getTest(+id).subscribe({
        next: (response: TestResponse) => {
          this.test = {
            id: response.id,
            name: response.name,
            group: response.group ?? '',
            testMethod: response.testMethod,
            reference: response.reference ?? '',
            price: response.price,
            testCategoryId: response.testCategoryId,
          };

          this.title = 'Edit Test';
        },

        error: (err: HttpErrorResponse) => {
          Swal.fire(
            'Error',
            err.error?.message ?? 'Unexpected error occurred',
            'error'
          ).then(() => {
            this.router.navigate(['/tests']);
          });
        },
      });
    });
  }

  // =====================================================
  // LOAD CATEGORIES
  // =====================================================

  private loadTestCategories(): void {
    this.testCategoryService
      .getTestCategories(0, 100)
      .subscribe({
        next: (response) => {
          this.testCategories = response.content;
        },

        error: (err) => {
          console.error('Error loading test categories', err);
        },
      });
  }

  // =====================================================
  // CREATE
  // =====================================================

  public create(): void {
    this.apiError = null;

    const request: CreateTestRequest = {
      name: this.test.name.trim(),
      group: this.test.group.trim(),
      testMethod: this.test.testMethod,
      reference: this.test.reference?.trim() || undefined,
      price: this.test.price,
      testCategoryId: this.test.testCategoryId,
    };

    this.testService.createTest(request).subscribe({
      next: (response) => {
        this.alertService.success(
          response.message,
          'Test de Referencia'
        );

        this.router.navigate(['/tests']);
      },

      error: (err: HttpErrorResponse) => {
        this.apiError = err.error;

        this.alertService.error(
          this.apiError?.message ??
            'Hubo un error al crear el test de referencia',
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

    if (!this.test.id) {
      this.alertService.error(
        'El ID es requerido para actualizar el test de referencia',
        'Error'
      );

      return;
    }

    const request: UpdateTestRequest = {
      name: this.test.name.trim(),
      group: this.test.group.trim(),
      testMethod: this.test.testMethod,
      reference: this.test.reference?.trim() || undefined,
      price: this.test.price,
      testCategoryId: this.test.testCategoryId,
    };

    this.testService.updateTest(
      this.test.id,
      request
    ).subscribe({
      next: (response: any) => {
        this.alertService.success(
          response.message,
          'Test de Referencia'
        );

        this.router.navigate(['/tests']);
      },

      error: (err: HttpErrorResponse) => {
        this.apiError = err.error;

        if (!this.apiError?.errors) {
          this.alertService.error(
            this.apiError?.message ??
              'Hubo un error al actualizar el test de referencia',
            'Error'
          );
        }
      },
    });
  }
}
