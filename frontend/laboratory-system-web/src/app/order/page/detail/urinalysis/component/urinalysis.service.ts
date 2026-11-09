import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { TestResultFilter } from '../../../../../results/components/result-filter.component';
import { Page } from '../../../../../utils/paginator/paginator.model';
import { FilterUtils } from '../../../../../utils/filter/filter-utils.model';
import { ApiResponse } from '../../../../../utils/response/api-response';
import { CreateUrinalysisRequest, UpdateUrinalysisRequest, UrinalysisResponse } from './urinalysis.model';

@Injectable({
  providedIn: 'root',
})
export class UrinalysisService {

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
  ): Observable<Page<UrinalysisResponse>> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/urinalysis`;

    return this.http.get<Page<UrinalysisResponse>>(url, {
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
  ): Observable<Page<UrinalysisResponse>> {

    const url = `${this.url}/patients/${patientId}/urinalysis`;

    return this.http.get<Page<UrinalysisResponse>>(url, {
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
    testResult: CreateUrinalysisRequest
  ): Observable<ApiResponse> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/urinalysis`;

    return this.http.post<ApiResponse>(url, testResult);
  }

  // =====================================================
  // OPERACIONES SOBRE LOS RESULTADOS
  // =====================================================

  public getTestResult(
    id: number
  ): Observable<UrinalysisResponse> {

    const url = `${this.url}/urinalysis/${id}`;

    return this.http.get<UrinalysisResponse>(url);
  }

  public getPatientOrderTestResult(
    patientId: number,
    orderId: number,
    testResultId: number
): Observable<UrinalysisResponse> {

  const url =
    `${this.url}/patients/${patientId}/orders/${orderId}/urinalysis/${testResultId}`;

  return this.http.get<UrinalysisResponse>(url);
}


public updateTestResult(
  testResultId: number,
  testResult: UpdateUrinalysisRequest
): Observable<ApiResponse> {

  const url = `${this.url}/urinalysis/${testResultId}`;

  return this.http.put<ApiResponse>(url, testResult);
}



  public deleteTestResult(
    id: number
  ): Observable<ApiResponse> {

    const url = `${this.url}/urinalysis/${id}`;

    return this.http.delete<ApiResponse>(url);
  }
}
