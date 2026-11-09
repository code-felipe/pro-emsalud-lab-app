import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { TestResultFilter } from '../../../../../results/components/result-filter.component';
import { Page } from '../../../../../utils/paginator/paginator.model';
import { FilterUtils } from '../../../../../utils/filter/filter-utils.model';
import { ApiResponse } from '../../../../../utils/response/api-response';
import { CreateWidalReactionRequest, UpdateWidalReactionRequest, WidalReactionResponse } from './widal-model';

@Injectable({
  providedIn: 'root',
})
export class WidalReactionService {

  private url: string = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // =====================================================
  // Resultados de la Orden
  // =====================================================

  public getTestResults(
    patientId: number,
    orderId: number,
    page: number,
    size: number,
    filter?: TestResultFilter,
    sort?: string
  ): Observable<Page<WidalReactionResponse>> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/widal-reactions`;

    return this.http.get<Page<WidalReactionResponse>>(url, {
      params: FilterUtils.buildHttpParams(
        page,
        size,
        filter,
        sort
      ),
    });
  }

    public getTestResultsByPatientId(
    patientId: number,
    page: number,
    size: number,
    filter?: TestResultFilter,
    sort?: string
  ): Observable<Page<WidalReactionResponse>> {

    const url = `${this.url}/patients/${patientId}/widal-reactions`;

    return this.http.get<Page<WidalReactionResponse>>(url, {
      params: FilterUtils.buildHttpParams(
        page,
        size,
        filter,
        sort
      ),
    });
  }

  public createTestResult(
    patientId: number,
    orderId: number,
    testResult: CreateWidalReactionRequest
  ): Observable<ApiResponse> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/widal-reactions`;

    return this.http.post<ApiResponse>(url, testResult);
  }

  // =====================================================
  // OPERACIONES SOBRE LOS RESULTADOS
  // =====================================================

  public getTestResult(
    id: number
  ): Observable<WidalReactionResponse> {

    const url = `${this.url}/widal-reactions/${id}`;

    return this.http.get<WidalReactionResponse>(url);
  }

  public getPatientOrderTestResult(
    patientId: number,
    orderId: number,
    testResultId: number
): Observable<WidalReactionResponse> {

  const url =
    `${this.url}/patients/${patientId}/orders/${orderId}/widal-reactions/${testResultId}`;

  return this.http.get<WidalReactionResponse>(url);
}


public updateTestResult(
  testResultId: number,
  testResult: UpdateWidalReactionRequest
): Observable<ApiResponse> {

  const url = `${this.url}/widal-reactions/${testResultId}`;

  return this.http.put<ApiResponse>(url, testResult);
}



  public deleteTestResult(
    id: number
  ): Observable<ApiResponse> {

    const url = `${this.url}/widal-reactions/${id}`;

    return this.http.delete<ApiResponse>(url);
  }
}
