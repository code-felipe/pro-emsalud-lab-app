import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { TestResponse, TestType } from '../../components/test.model';
import {TestFilter,} from '../../components/test-filter.component';

import { TestService } from '../../components/test.service';

import { FilterUtils } from '../../../../utils/filter/filter-utils.model';
import { PaginatorComponent } from '../../../../utils/paginator/paginator.component';

import { ModalService } from '../../../../utils/modal/modal-service';
import { ModalComponent } from '../../../../utils/modal/modal.component';
import { TestFilterComponent } from '../filter/filter.component';
import { TestCategoryResponse } from '../../../test-category/components/test-category.model';
import { TestCategoryService } from '../../../test-category/components/test-category.service';
import { AlertService } from '../../../../utils/alert/alert.service';
import { AuthService } from '../../../../auth/components/auth.service';


@Component({
  selector: 'app-test-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    PaginatorComponent,
    ModalComponent,
    TestFilterComponent
  ],

  templateUrl: './test-list.component.html',
  
})
export class TestsComponentList implements OnInit {

  tests: TestResponse[] = [];

  filter: TestFilter = {};

  draftFilter: TestFilter = {};

  testCategories: TestCategoryResponse[] = [];

  paginator: any;

  hasError = false;

  title: string = 'Catalogo de Test Referencial'
  
  private readonly authService = inject(AuthService);
  protected readonly isAdmin = this.authService.isAdmin;
  protected readonly isUser = this.authService.isUser;

  testTypeLabels: Record<TestType, string> = {
  [TestType.CUANTITATIVA]: 'Cuantitativa',
  [TestType.RAPIDA]: 'Rápida',
  [TestType.SEMI_CUANTITATIVA]: 'SEMI_CUANTITATIVA',
  [TestType.OTROS]: 'Otros',
};



  constructor(
    private testService: TestService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: ModalService,
    private testCategoryService: TestCategoryService,
    private alertService: AlertService
  ) {}


  ngOnInit(): void {

    this.loadTestCategories();
    this.activatedRoute.queryParamMap.subscribe((params) => {

      const page = params.get('page')
        ? +params.get('page')!
        : 0;

      const size = params.get('size')
        ? +params.get('size')!
        : 20;
    
    const sort = params.get('sort')
      ?? 'createdAt,desc';

      this.filter = FilterUtils.buildTestFilter(params);

      this.loadTests(page, size, sort);
    });
  }

  public onApplyFilters(newFilter: TestFilter): void {

    const cleanedParams = {

      page: 0,

      name: newFilter.name?.trim()
        ? newFilter.name.trim()
        : null,

      priceMin: newFilter.priceMin ?? null,

      priceMax: newFilter.priceMax ?? null,

      active: newFilter.active ?? null,

      testCategoryName: newFilter.testCategoryName?.trim()
        ? newFilter.testCategoryName.trim()
        : null,

      createdAtAfter: newFilter.createdAtAfter ?? null,

      createdAtBefore: newFilter.createdAtBefore ?? null,

      updatedAtAfter: newFilter.updatedAtAfter ?? null,

      updatedAtBefore: newFilter.updatedAtBefore ?? null,
    };


    this.router.navigate([], {

      relativeTo: this.activatedRoute,

      queryParams: cleanedParams,

      queryParamsHandling: 'merge',

    });
  }

public toggleActive(test: TestResponse): void {

  const request$ = test.active
    ? this.testService.deactivateTest(test.id)
    : this.testService.activateTest(test.id);

  request$.subscribe({

    next: (response) => {

      test.active = !test.active;

      this.alertService.success(
        response.message,
        test.active
          ? 'Test referencial activado'
          : 'Test referencial desactivado'
      );

    },

    error: (error) => {

      console.error(error);

      this.alertService.error(
        'No fue posible cambiar el estado del test referencial.'
      );

    }

  });
}


public delete(test: TestResponse): void {

  this.alertService.confirmDelete(test.name)
    .then(confirmed => {

      if (!confirmed) {
        return;
      }

      this.testService.deleteTest(test.id).subscribe({

        next: (response) => {

          this.tests = this.tests.filter(
            t => t.id !== test.id
          );

        this.alertService.success(
          response.message,
          'Test referencial eliminado'
          );
        },

        error: (error) => {

          console.error(error);

          this.alertService.error(
           error.error?.message ?? 'No fue posible eliminar el test referencial.'
          );
        }

      });
    });
}




  /**
   * Carga los tests.
   */
private loadTests(page: number, size: number, sort: string): void {
    
    this.testService.getTests(
        page,
        size,
        this.filter,
        sort
      )
      .subscribe({

        next: (response) => {

          this.tests = response.content;

          this.paginator = response;

          this.hasError = false;

        },

        error: (err) => {

          this.hasError = true;

          console.error(err);

        },

      });
  }

private loadTestCategories(): void {
    
    this.testCategoryService.getTestCategories(0, 100)
    .subscribe({

      next: (response) => {
        this.testCategories = response.content;
      },

      error: (err) => {
        console.error('Error loading test categories', err);
      }

    });
}

// FILTERS
onNameSearch(event: Event): void {
  const input = event.target as HTMLInputElement;
  const name = input.value.trim();

  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      name: name || null
    },
    queryParamsHandling: 'merge'
  });
}

clearNameSearch(): void {
  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      name: null
    },
    queryParamsHandling: 'merge'
  });
}

onAscendingDate(): void {
  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      sort: 'createdAt,asc'
    },
    queryParamsHandling: 'merge'
  });
}

onDescendingDate(): void {
  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      sort: 'createdAt,desc'
    },
    queryParamsHandling: 'merge'
  });
}

openFilterModal(): void {
  this.draftFilter = {
    ...this.filter
  };

  this.modalService.openModal({
    type: 'filter',
    title: 'Filtros',
  });
}

applyFilters(): void {
  this.onApplyFilters(this.draftFilter);

  this.modalService.closeModal();
}

closeFilterModal(): void {
  this.modalService.closeModal();
}

resetModal(): void{
      this.draftFilter = {};
  this.filter = {};

  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      name: null,
      priceMin: null,
      priceMax: null,
      active: null,
      testCategoryName: null,
      createdAtAfter: null,
      createdAtBefore: null,
      updatedAtAfter: null,
      updatedAtBefore: null,
    },
    queryParamsHandling: 'merge',
  });
}


}




