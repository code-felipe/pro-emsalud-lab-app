import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { CreateTestCategoryRequest, TestCategoryResponse, UpdateTestCategoryRequest } from '../../../test-category/components/test-category.model';
import { TestCategoryService } from '../../../test-category/components/test-category.service';
import { AlertService } from '../../../../utils/alert/alert.service';
import Swal from 'sweetalert2';


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
export class TestCategoryFormComponent implements OnInit {


  public testCategory: CreateTestCategoryRequest & { id?: number } = {
    name: '',
  };

  public title: string = 'Nueva Categoria de Test Referencial';

  public apiError: any = null;

  constructor(
    private testCategoryService: TestCategoryService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}


  ngOnInit(): void {

    this.loadCategoryTest();

  }

public loadCategoryTest(): void {

    this.activatedRoute.params.subscribe((params) => {

      const id = params['id'];

      // CREATE
      if (!id) {
        return;
      }

      // EDIT
      this.testCategoryService.getTestCategory(+id).subscribe({

        next: (response: TestCategoryResponse) => {

          this.testCategory = {
            id: response.id,
            name: response.name,
          };

          this.title = 'Editar Categoria';
        },

        error: (err: HttpErrorResponse) => {

          Swal.fire(
            'Error',
            err.error?.message ?? 'Erro inesperado',
            'error'
          ).then(() => {

            this.router.navigate(['/test-category-references']);

          });

        },

      });

    });

  }


 public create(): void {

  this.apiError = null;

  const request: CreateTestCategoryRequest = {
    name: this.testCategory.name.trim(),

  };

  this.testCategoryService.createTestCategory(request).subscribe({

    next: (response) => {

      this.alertService.success(
        response.message,
        'Categoria Test Referencial'
      );

      this.router.navigate(['/test-category-references']);

    },

    error: (err: HttpErrorResponse) => {

      this.apiError = err.error;

      this.alertService.error(
        this.apiError?.message ??
          'Hubo un error al crear la categoria test referencial',
        'Error'
      );

    }

  });
}


public update(): void {
    
    this.apiError = null;

    if (!this.testCategory.id) {
        this.alertService.error(
            this.apiError?.message ??
            'El ID es requerido para actualizar la cateogria de test de referencia',
            'Error'
        )


        return;
    }

  const request: UpdateTestCategoryRequest = {

    name: this.testCategory.name.trim(),
  };

  this.testCategoryService.updateTestCategory(
    this.testCategory.id,
    request
  ).subscribe({

    next: (response: any) => {
        
        this.alertService.success(
        response.message,
        'Categoria de Test de Referencia'
      );

      this.router.navigate(['/test-category-references']);


    },

    error: (err: HttpErrorResponse) => {

      this.apiError = err.error;

      if (!this.apiError?.errors) {

      this.alertService.error(
        this.apiError?.message ??
          'Hubo un error al crear la categoria de test de referencia',
        'Error'
      );

      }

    },

  });
}


  

}
