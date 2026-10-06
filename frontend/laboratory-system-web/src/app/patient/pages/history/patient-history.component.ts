import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { PaginatorComponent } from '../../../utils/paginator/paginator.component';
import { ModalComponent } from '../../../utils/modal/modal.component';
import { ModalService } from '../../../utils/modal/modal-service';
import { AlertService } from '../../../utils/alert/alert.service';
import { FilterUtils } from '../../../utils/filter/filter-utils.model';
import { HttpErrorResponse } from '@angular/common/http';
import { TestResultFilterComponent } from '../../../results/page/filter/filter.component';
import { TestResultResponse } from '../../../results/components/result.model';
import { TestResultFilter } from '../../../results/components/result-filter.component';
import { TestResultService } from '../../../results/components/result.service';



@Component({
  selector: 'app-test-result-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    PaginatorComponent,
    ModalComponent,
    TestResultFilterComponent
  ],

  templateUrl: './patient-history.component.html',
})
export class PatientHistoryComponent implements OnInit {

  patientId!: number;
  orderId!: number;

  results: TestResultResponse[] = [];

  filter: TestResultFilter = {};

  draftFilter: TestResultFilter = {};

  paginator: any;

  hasError = false;

  title = 'Resultados';


  constructor(
    private testResultService: TestResultService,
    private router: Router,
    private modalService: ModalService,
    private alertService: AlertService,
    private activatedRoute: ActivatedRoute
  ) {}

ngOnInit(): void {
  
 const patientId = Number(
    this.activatedRoute.parent?.snapshot.paramMap.get('id')
  );

  const orderId = Number(
    this.activatedRoute.snapshot.paramMap.get('orderId')
  );

  this.patientId = patientId;
  this.orderId = orderId;

  console.log('patientId:', patientId);
  console.log('orderId:', orderId);

  this.activatedRoute.queryParamMap.subscribe((params) => {

    const page = params.get('page')
      ? +params.get('page')!
      : 0;

    const size = params.get('size')
      ? +params.get('size')!
      : 30;

    const sort = params.get('sort')
      ?? 'createdAt,desc';

    this.filter = FilterUtils.buildTestResultFilter(params);
    
    console.log('Test-result filter:', this.filter);
    console.log('Loading orders for patient:', this.patientId);
    this.loadTestResults(page, size, sort);
  });
}


  private loadTestResults(
    page: number,
    size: number,
    sort: string
  ): void {

    this.testResultService.getTestResultsByPatientId(
      this.patientId,
      page,
      size,
      this.filter,
      sort
    )
    .subscribe({

      next: (response) => {

        this.results = response.content;
        // console.log(this.orders)
        this.paginator = response;

        this.hasError = false;

      },

      error: (err) => {

        this.hasError = true;

        console.error(err);

      },

    });
  }

  public delete(result: TestResultResponse): void {

    this.alertService
      .confirmDelete(result.id.toString())
      .then(confirmed => {

        if (!confirmed) {
          return;
        }

        this.testResultService
          .deleteTestResult(result.id)
          .subscribe({

            next: (response) => {

              this.results = this.results.filter(
                o => o.id !== result.id
              );

              this.alertService.success(
                response.message,
                'Test Result eliminado'
              );

            },

            error: (error) => {

              console.error(error);

              this.alertService.error(
                'No fue posible eliminar el resultado.'
              );

            }

          });

      });
  }

  public onApplyFilters(newFilter: TestResultFilter): void {

    const cleanedParams = {

      page: 0,

      search: newFilter.search ?? null,

      priceMin: newFilter.priceMin ?? null,

      priceMax: newFilter.priceMax ?? null,

      createdAtAfter: newFilter.createdAtAfter ?? null,

      createdAtBefore: newFilter.createdAtBefore ?? null,

      updatedAtAfter: newFilter.updatedAtAfter ?? null,

      updatedAtBefore: newFilter.updatedAtBefore ?? null,

    };

    this.router.navigate([], {

      queryParams: cleanedParams,

      queryParamsHandling: 'merge',

    });
  }


  onSearch(event: Event): void {

    const input = event.target as HTMLInputElement;

    const search = input.value.trim();

    this.router.navigate([], {

      queryParams: {

        page: 0,

        search: search || null

      },

      queryParamsHandling: 'merge'

    });
  }


clearSearch(): void {

  this.router.navigate([], {
    queryParams: {
      page: 0,
      search: null
    },
    queryParamsHandling: 'merge'
  });
}



  onAscendingDate(): void {

    this.router.navigate([], {

      queryParams: {

        page: 0,

        sort: 'createdAt,asc'

      },

      queryParamsHandling: 'merge'

    });
  }


  onDescendingDate(): void {

    this.router.navigate([], {

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


  resetModal(): void {

    this.draftFilter = {};

    this.filter = {};

    this.router.navigate([], {

      queryParams: {

        page: 0,

        priceMin: null,

        priceMax: null,

        createdAtAfter: null,

        createdAtBefore: null,

        updatedAtAfter: null,

        updatedAtBefore: null,

      },

      queryParamsHandling: 'merge',

    });
  }

}
//   public toggleStatus(order: OrderResponse): void {

//   const isCompleted = order.status === OrderStatus.COMPLETED;

//   const request$ = isCompleted
//     ? this.orderService.inProgress(order.id)
//     : this.orderService.completed(order.id);

//   request$.subscribe({

//     next: (response) => {

//       order.status = isCompleted
//         ? OrderStatus.IN_PROGRESS
//         : OrderStatus.COMPLETED;

//       this.alertService.success(response.message, 'Orden');
//     },

//     error: (err: HttpErrorResponse) => {

//       this.alertService.error(
//         err.error?.message ?? 'No fue posible cambiar el estado de la orden.',
//         'Error'
//       );
//     },

//   });
// }