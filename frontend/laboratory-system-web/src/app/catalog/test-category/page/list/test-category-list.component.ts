import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PaginatorComponent } from '../../../../utils/paginator/paginator.component';
import { TestCategoryResponse } from '../../../test-category/components/test-category.model';
import { TestCategoryService } from '../../../test-category/components/test-category.service';
import { AlertService } from '../../../../utils/alert/alert.service';


@Component({
  selector: 'app-test-category-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    PaginatorComponent,
  ],

  templateUrl: './test-category-list.component.html',
})
export class TestCategoryList implements OnInit {

  categories: TestCategoryResponse[] = [];

//   filter: TestFilter = {};

//   draftFilter: TestFilter = {};

  paginator: any;

  hasError = false;

  title: string = 'Categorias de Test Referencial'

  constructor(
    private testCatgoryService: TestCategoryService,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}


  ngOnInit(): void {

    this.activatedRoute.queryParamMap.subscribe((params) => {

      const page = params.get('page')
        ? +params.get('page')!
        : 0;

      const size = params.get('size')
        ? +params.get('size')!
        : 20;
    

      this.loadTestsCategories(page, size);
    });
  }



public delete(test: TestCategoryResponse): void {

  this.alertService.confirmDelete(test.name)
    .then(confirmed => {

      if (!confirmed) {
        return;
      }

      this.testCatgoryService.deleteTestCategory(test.id).subscribe({

        next: (response) => {
          
          this.categories = this.categories.filter(
          category => category.id !== test.id);

        this.alertService.success(
          response.message,
          'Test categoria eliminado'
          );
        },

        error: (error) => {

          console.error(error);

          this.alertService.error(
            'No fue posible eliminar categoria test.'
          );
        }

      });
    });
}




  /**
   * Carga los tests.
   */
private loadTestsCategories(page: number, size: number): void {
    
    this.testCatgoryService.getTestCategories(
        page,
        size
      )
      .subscribe({

        next: (response) => {

          this.categories = response.content;

          this.paginator = response;

          this.hasError = false;

        },

        error: (err) => {

          this.hasError = true;

          console.error(err);

        },

      });
  }
}




