import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { PaginatorComponent } from '../../../utils/paginator/paginator.component';
import { ModalComponent } from '../../../utils/modal/modal.component';
import { ModalService } from '../../../utils/modal/modal-service';
import { AlertService } from '../../../utils/alert/alert.service';

import { OrderFilterComponent } from '../filter/filter.component';

import {
  OrderReportResponse,
  OrderResponse,
  OrderStatus
} from '../../components/order.model';

import { OrderFilter } from '../../components/order-filter.component';
import { OrderService } from '../../components/order.service';
import { FilterUtils } from '../../../utils/filter/filter-utils.model';

import { OrderReportComponent } from '../report/order-report.component';


@Component({
  selector: 'app-order-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    PaginatorComponent,
    ModalComponent,
    OrderFilterComponent,
    OrderReportComponent
  ],

  templateUrl: './order-list.component.html',
})
export class OrderComponentList implements OnInit {

  @Input() patientId!: number;


  // =====================================================
  // LIST
  // =====================================================

  orders: OrderResponse[] = [];

  filter: OrderFilter = {};

  draftFilter: OrderFilter = {};

  paginator: any;

  hasError = false;

  title = 'Órdenes';


  // =====================================================
  // ORDER STATUS
  // =====================================================

  public readonly OrderStatus = OrderStatus;

  statusLabels: Record<OrderStatus, string> = {

    [OrderStatus.PENDING]: 'Pendiente',

    [OrderStatus.IN_PROGRESS]: 'En Progreso',

    [OrderStatus.COMPLETED]: 'Completada'

  };


  // =====================================================
  // REPORT
  // =====================================================

  public selectedOrderReport?: OrderReportResponse;

  public reportLoading = false;


  constructor(
    private orderService: OrderService,
    private router: Router,
    public modalService: ModalService,
    private alertService: AlertService,
    private activatedRoute: ActivatedRoute
  ) {}


  // =====================================================
  // INIT
  // =====================================================

  ngOnInit(): void {

    this.activatedRoute.queryParamMap.subscribe((params) => {

      const page = params.get('page')
        ? +params.get('page')!
        : 0;

      const size = params.get('size')
        ? +params.get('size')!
        : 25;

      const sort = params.get('sort')
        ?? 'createdAt,desc';

      this.filter = FilterUtils.buildOrderFilter(params);

      console.log('Order filter:', this.filter);

      console.log(
        'Loading orders for patient:',
        this.patientId
      );

      this.loadOrders(
        page,
        size,
        sort
      );

    });

  }


  // =====================================================
  // LOAD ORDERS
  // =====================================================

  private loadOrders(
    page: number,
    size: number,
    sort: string
  ): void {

    this.orderService
      .getOrders(
        this.patientId,
        page,
        size,
        this.filter,
        sort
      )
      .subscribe({

        next: (response) => {

          this.orders = response.content;

          this.paginator = response;

          this.hasError = false;

        },

        error: (err) => {

          this.hasError = true;

          console.error(err);

        },

      });

  }


  // =====================================================
  // DELETE
  // =====================================================

  public delete(order: OrderResponse): void {

    this.alertService
      .confirmDelete(order.id.toString())
      .then(confirmed => {

        if (!confirmed) {
          return;
        }

        this.orderService
          .deleteOrder(order.id)
          .subscribe({

            next: (response) => {

              this.orders = this.orders.filter(
                o => o.id !== order.id
              );

              this.alertService.success(
                response.message,
                'Orden eliminada'
              );

            },

            error: (error) => {

              console.error(error);

              this.alertService.error(
                'No fue posible eliminar la orden.'
              );

            }

          });

      });

  }


  // =====================================================
  // TOGGLE STATUS
  // =====================================================

  public toggleStatus(order: OrderResponse): void {

    const isCompleted =
      order.status === OrderStatus.COMPLETED;

    const request$ = isCompleted
      ? this.orderService.inProgress(order.id)
      : this.orderService.completed(order.id);

    request$.subscribe({

      next: (response) => {

        order.status = isCompleted
          ? OrderStatus.IN_PROGRESS
          : OrderStatus.COMPLETED;

        this.alertService.success(
          response.message,
          'Orden'
        );

      },

      error: (err: HttpErrorResponse) => {

        this.alertService.error(
          err.error?.message ??
            'No fue posible cambiar el estado de la orden.',
          'Error'
        );

      },

    });

  }

  public openOrderReport(
    order: OrderResponse
  ): void {

    // Limpiamos el reporte anterior
    this.selectedOrderReport = undefined;

    // Activamos loading
    this.reportLoading = true;


    // Abrimos modal
    this.modalService.openModal({

      type: 'report',

      title: `Reporte de Orden #${order.id}`,

    });

    this.orderService
      .getOrderReport(
        this.patientId,
        order.id
      )
      .subscribe({

        next: (response) => {

          this.selectedOrderReport = response;

          this.reportLoading = false;

        },

        error: (err: HttpErrorResponse) => {

          this.reportLoading = false;

          console.error(err);

          this.modalService.closeModal();

          this.alertService.error(
            err.error?.message ??
              'No fue posible cargar el reporte.',
            'Reporte'
          );

        }

      });

  }

  public closeOrderReport(): void {

    this.modalService.closeModal();

    this.selectedOrderReport = undefined;

    this.reportLoading = false;

  }

  public onApplyFilters(
    newFilter: OrderFilter
  ): void {

    const cleanedParams = {

      page: 0,

      doctorName: newFilter.doctorName?.trim()
        ? newFilter.doctorName.trim()
        : null,

      status: newFilter.status ?? null,

      createdAtAfter:
        newFilter.createdAtAfter ?? null,

      createdAtBefore:
        newFilter.createdAtBefore ?? null,

      updatedAtAfter:
        newFilter.updatedAtAfter ?? null,

      updatedAtBefore:
        newFilter.updatedAtBefore ?? null,

    };


    this.router.navigate([], {

      queryParams: cleanedParams,

      queryParamsHandling: 'merge',

    });

  }

  public onSearch(event: Event): void {

    const input =
      event.target as HTMLInputElement;

    const doctorName =
      input.value.trim();


    this.router.navigate([], {

      queryParams: {

        page: 0,

        doctorName:
          doctorName || null

      },

      queryParamsHandling: 'merge'

    });

  }

  public clearSearch(): void {

    this.router.navigate([], {

      queryParams: {

        page: 0,

        doctorName: null

      },

      queryParamsHandling: 'merge'

    });

  }

  public onAscendingDate(): void {

    this.router.navigate([], {

      queryParams: {

        page: 0,

        sort: 'createdAt,asc'

      },

      queryParamsHandling: 'merge'

    });

  }


  public onDescendingDate(): void {

    this.router.navigate([], {

      queryParams: {

        page: 0,

        sort: 'createdAt,desc'

      },

      queryParamsHandling: 'merge'

    });

  }


  public openFilterModal(): void {

    this.draftFilter = {
      ...this.filter
    };


    this.modalService.openModal({

      type: 'filter',

      title: 'Filtros',

    });

  }

  public applyFilters(): void {

    this.onApplyFilters(
      this.draftFilter
    );

    this.modalService.closeModal();

  }

  public closeFilterModal(): void {

    this.modalService.closeModal();

  }

  public resetModal(): void {

    this.draftFilter = {};

    this.filter = {};


    this.router.navigate([], {

      queryParams: {

        page: 0,

        doctorName: null,

        status: null,

        createdAtAfter: null,

        createdAtBefore: null,

        updatedAtAfter: null,

        updatedAtBefore: null,

      },

      queryParamsHandling: 'merge',

    });

  }

}
