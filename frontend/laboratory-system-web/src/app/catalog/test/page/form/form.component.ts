import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';

import {CreateTestRequest,TestResponse,TestType,UpdateTestRequest,} from '../../components/test.model';

import { TestService } from '../../components/test.service';

import { TestCategoryResponse } from '../../../test-category/components/test-category.model';
import { TestCategoryService } from '../../../test-category/components/test-category.service';
import { AlertService } from '../../../../utils/alert/alert.service';
import { TEST_TEMPLATES } from '../../../../utils/templates/templates';


@Component({
  selector: 'app-test-form',
  standalone: true,

  imports: [
    FormsModule,
    RouterLink,
    CommonModule
  ],

  templateUrl: './form.component.html',
})
export class TestFormComponent implements OnInit {


  public test: CreateTestRequest & { id?: number } = {
    name: '',
    testType: undefined as unknown as TestType,
    reference: '',
    price: 0,
    testCategoryId: 0,
  };

  public title: string = 'Nuevo Test Referencial';

  public apiError: any = null;

  testTypes = [
    {
    value: TestType.RAPIDA,
    label: 'Rápida'
    },
    {
    value: TestType.CUANTITATIVA,
    label: 'Cuantitativa'
    },
     {
    value: TestType.SEMI_CUANTITATIVA,
    label: 'Semi Cuantitativa'
    },
    {
    value: TestType.OTROS,
    label: 'Otros'
    }
    ];

  public templates = TEST_TEMPLATES;

  public templateMenuOpen = false;

  public insertTemplate(content: string): void {
  this.test.reference = content;
  this.templateMenuOpen = false;
}

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
            testType: response.testType,
            reference: response.reference ?? undefined,
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

          console.error(
            'Error loading test categories',
            err
          );

        },

      });

  }


 public create(): void {

  this.apiError = null;

  const request: CreateTestRequest = {
    name: this.test.name.trim(),
    testType: this.test.testType,
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

    }

  });
}


  public update(): void {

  this.apiError = null;

  if (!this.test.id) {
    this.alertService.error(
        this.apiError?.message ??
        'El ID es requerido para actualizar el test de referencia',
        'Error'
    )


    return;
  }

  const request: UpdateTestRequest = {

    name: this.test.name.trim(),

    testType: this.test.testType,

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
          'Hubo un error al crear el test de referencia',
        'Error'
      );

      }

    },

  });
}


  

}
