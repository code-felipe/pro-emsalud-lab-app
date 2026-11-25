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
  TEST_METHOD_OPTIONS,
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
    testMethods: [],
    reference: '',
    price: 0,
    testCategoryId: 0,
  };

  public title: string = 'Nueva Prueba Referencial';

  public apiError: any = null;

  public methodsTouched = false;

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

  public methodOptions = TEST_METHOD_OPTIONS;

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
  // METHODS (CHECKBOXES)
  // =====================================================

  public isSelected(method: TestMethod): boolean {
    return this.test.testMethods.includes(method);
  }

  public toggleMethod(method: TestMethod, checked: boolean): void {
    this.methodsTouched = true;

    if (checked) {
      if (!this.isSelected(method)) {
        this.test.testMethods = [...this.test.testMethods, method];
      }
    } else {
      this.test.testMethods = this.test.testMethods.filter(
        (m) => m !== method
      );
    }
  }

  private hasMethods(): boolean {
    if (this.test.testMethods.length === 0) {
      this.methodsTouched = true;
      this.alertService.error(
        'Debes seleccionar al menos un método',
        'Error'
      );
      return false;
    }
    return true;
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
            testMethods: [...response.testMethods],
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

    if (!this.hasMethods()) {
      return;
    }

    const request: CreateTestRequest = {
      name: this.test.name.trim(),
      testMethods: this.test.testMethods,
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

    if (!this.hasMethods()) {
      return;
    }

    const request: UpdateTestRequest = {
      name: this.test.name.trim(),
      testMethods: this.test.testMethods,
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