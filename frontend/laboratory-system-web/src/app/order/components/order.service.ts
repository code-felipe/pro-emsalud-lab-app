import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';

import { Page } from '../../utils/paginator/paginator.model';
import { FilterUtils } from '../../utils/filter/filter-utils.model';
import { ApiResponse } from '../../utils/response/api-response';
import { OrderFilter } from './order-filter.component';
import {
  CreateOrderRequest,
  OrderReportResponse,
  OrderResponse,
  PendingOrderNotificationResponse,
  UpdateOrderRequest
} from './order.model';

@Injectable({
  providedIn: 'root',
})
export class OrderService {

  private url: string = 'http://localhost:8080/api';

  private readonly ordersChangedSubject = new Subject<void>();
  public readonly ordersChanged$ = this.ordersChangedSubject.asObservable();

  constructor(private http: HttpClient) {}

  // =====================================================
  // ÓRDENES DEL PACIENTE
  // =====================================================

  public getOrders(
    patientId: number,
    page: number,
    size: number,
    filter?: OrderFilter,
    sort?: string
  ): Observable<Page<OrderResponse>> {

    const url = `${this.url}/patients/${patientId}/orders`;

    return this.http.get<Page<OrderResponse>>(url, {
      params: FilterUtils.buildHttpParams(
        page,
        size,
        filter,
        sort
      ),
    });
  }

  public createOrder(
    patientId: number,
    order: CreateOrderRequest
  ): Observable<ApiResponse> {
    const url = `${this.url}/patients/${patientId}/orders`;

    return this.http.post<ApiResponse>(url, order);
  }

  // =====================================================
  // OPERACIONES SOBRE UNA ORDEN
  // =====================================================

  public getOrder(
    id: number
  ): Observable<OrderResponse> {

    const url = `${this.url}/orders/${id}`;

    return this.http.get<OrderResponse>(url);
  }

  public updateOrder(
    id: number,
    order: UpdateOrderRequest
  ): Observable<ApiResponse> {

    const url = `${this.url}/orders/${id}`;

    return this.http.put<ApiResponse>(url, order);
  }

  public deleteOrder(
    id: number
  ): Observable<ApiResponse> {

    const url = `${this.url}/orders/${id}`;

    return this.http.delete<ApiResponse>(url);
  }

  public inProgress(
    id: number
  ): Observable<ApiResponse> {

    const url = `${this.url}/orders/${id}/inprogress`;

    return this.http.patch<ApiResponse>(url, {});
  }

  public completed(
    id: number
  ): Observable<ApiResponse> {

    const url = `${this.url}/orders/${id}/completed`;

    return this.http.patch<ApiResponse>(url, {});
  }

  public getOrderReport(
  patientId: number,
  orderId: number
): Observable<OrderReportResponse> {

  const url =
    `${this.url}/patients/${patientId}/orders/${orderId}/report`;

  return this.http.get<OrderReportResponse>(url);
}

  public getPendingOrders(): Observable<number> {
    return this.http.get<number>(
      `${this.url}/orders/notifications/pending-count`
    );
  }

public getPendingOrderNotifications():
  Observable<PendingOrderNotificationResponse[]> {
  return this.http.get<PendingOrderNotificationResponse[]>(
    `${this.url}/orders/notifications`
  );
}

public notifyOrdersChanged(): void {
  this.ordersChangedSubject.next();
}


}

/*
  public getPendingOrders(): Observable<number> {
    return this.http.get<number>(
      `${this.url}/orders/notifications/pending-count`
    );
  }

public getPendingOrderNotifications():
  Observable<PendingOrderNotificationResponse[]> {
  return this.http.get<PendingOrderNotificationResponse[]>(
    `${this.url}/orders/notifications`
  );
}
*/