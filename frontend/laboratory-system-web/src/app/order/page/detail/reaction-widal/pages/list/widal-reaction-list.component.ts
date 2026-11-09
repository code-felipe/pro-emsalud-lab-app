import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PaginatorComponent } from '../../../../../../utils/paginator/paginator.component';
import { ModalComponent } from '../../../../../../utils/modal/modal.component';
import { TestResultFilterComponent } from '../../../../../../results/page/filter/filter.component';
import { TestResultFilter } from '../../../../../../utils/filter/result-filter.component';
import { ModalService } from '../../../../../../utils/modal/modal-service';
import { AlertService } from '../../../../../../utils/alert/alert.service';
import { FilterUtils } from '../../../../../../utils/filter/filter-utils.model';
import { WidalReactionResponse } from '../../components/widal-model';
import { WidalReactionService } from '../../components/widal.service';


@Component({
  selector: 'app-simple-chemistry-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    PaginatorComponent,
    ModalComponent,
    TestResultFilterComponent
  ],

  templateUrl: './widal-reaction-list.component.html',
  styleUrl: './widal-reaction-list.css'
})
export class WidalReactionList implements OnInit {

  patientId!: number;
  
  orderId!: number;

  results: WidalReactionResponse[] = [];

  filter: TestResultFilter = {};

  draftFilter: TestResultFilter = {};

  paginator: any;

  hasError = false;

  title = 'Formato - Reacción de Widal';


  constructor(
    private testResultService: WidalReactionService,
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

    this.testResultService.getTestResults(
      this.patientId,
      this.orderId,
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

  public delete(result: WidalReactionResponse): void {

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