import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { TestResultFilter } from '../../../../../results/components/result-filter.component';
import { Page } from '../../../../../utils/paginator/paginator.model';
import { FilterUtils } from '../../../../../utils/filter/filter-utils.model';
import { ApiResponse } from '../../../../../utils/response/api-response';
import { CreateStoolRequest, StoolResponse, UpdateStoolRequest } from './stool.model';

@Injectable({
  providedIn: 'root',
})
export class StoolService {

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
  ): Observable<Page<StoolResponse>> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/stools`;

    return this.http.get<Page<StoolResponse>>(url, {
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
  ): Observable<Page<StoolResponse>> {

    const url = `${this.url}/patients/${patientId}/stools`;

    return this.http.get<Page<StoolResponse>>(url, {
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
    testResult: CreateStoolRequest
  ): Observable<ApiResponse> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/stools`;

    return this.http.post<ApiResponse>(url, testResult);
  }

  // =====================================================
  // OPERACIONES SOBRE LOS RESULTADOS
  // =====================================================

  public getTestResult(
    id: number
  ): Observable<StoolResponse> {

    const url = `${this.url}/stools/${id}`;

    return this.http.get<StoolResponse>(url);
  }

  public getPatientOrderTestResult(
    patientId: number,
    orderId: number,
    testResultId: number
): Observable<StoolResponse> {

  const url =
    `${this.url}/patients/${patientId}/orders/${orderId}/stools/${testResultId}`;

  return this.http.get<StoolResponse>(url);
}


public updateTestResult(
  testResultId: number,
  testResult: UpdateStoolRequest
): Observable<ApiResponse> {

  const url = `${this.url}/stools/${testResultId}`;

  return this.http.put<ApiResponse>(url, testResult);
}



  public deleteTestResult(
    id: number
  ): Observable<ApiResponse> {

    const url = `${this.url}/stools/${id}`;

    return this.http.delete<ApiResponse>(url);
  }
}
