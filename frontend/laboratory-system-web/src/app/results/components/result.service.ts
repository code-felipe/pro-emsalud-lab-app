import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Page } from '../../utils/paginator/paginator.model';
import { FilterUtils } from '../../utils/filter/filter-utils.model';
import { ApiResponse } from '../../utils/response/api-response';
import { CreateTestRequest } from '../../catalog/test/components/test.model';
import { CreateTestResultRequest, TestResultResponse, TestResultSearchResponse, UpdateTestResultRequest } from './result.model';
import { TestResultFilter } from './result-filter.component';

@Injectable({
  providedIn: 'root',
})
export class TestResultService {

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
  ): Observable<Page<TestResultResponse>> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/test-results`;

    return this.http.get<Page<TestResultResponse>>(url, {
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
  ): Observable<Page<TestResultResponse>> {

    const url = `${this.url}/patients/${patientId}/test-results`;

    return this.http.get<Page<TestResultResponse>>(url, {
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
    testResult: CreateTestResultRequest
  ): Observable<ApiResponse> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/test-results`;

    return this.http.post<ApiResponse>(url, testResult);
  }

  // =====================================================
  // OPERACIONES SOBRE LOS RESULTADOS
  // =====================================================

  public getTestResult(
    id: number
  ): Observable<TestResultResponse> {

    const url = `${this.url}/test-results/${id}`;

    return this.http.get<TestResultResponse>(url);
  }

  public getPatientOrderTestResult(
    patientId: number,
    orderId: number,
    testResultId: number
): Observable<TestResultResponse> {

  const url =
    `${this.url}/patients/${patientId}/orders/${orderId}/test-results/${testResultId}`;

  return this.http.get<TestResultResponse>(url);
}


public updateTestResult(
  testResultId: number,
  testResult: UpdateTestResultRequest
): Observable<ApiResponse> {

  const url = `${this.url}/test-results/${testResultId}`;

  return this.http.put<ApiResponse>(url, testResult);
}



  public deleteTestResult(
    id: number
  ): Observable<ApiResponse> {

    const url = `${this.url}/test-results/${id}`;

    return this.http.delete<ApiResponse>(url);
  }
}
