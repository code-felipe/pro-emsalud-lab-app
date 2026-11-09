import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { CreateSimpleChemistryRequest, SimpleChemistryResponse, UpdateSimpleChemistryRequest } from './simple-chemistry.model';
import { TestResultFilter } from '../../../../../results/components/result-filter.component';
import { Page } from '../../../../../utils/paginator/paginator.model';
import { FilterUtils } from '../../../../../utils/filter/filter-utils.model';
import { ApiResponse } from '../../../../../utils/response/api-response';

@Injectable({
  providedIn: 'root',
})
export class SimpleChemistryService {

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
  ): Observable<Page<SimpleChemistryResponse>> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/simple-chemistry`;

    return this.http.get<Page<SimpleChemistryResponse>>(url, {
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
  ): Observable<Page<SimpleChemistryResponse>> {

    const url = `${this.url}/patients/${patientId}/simple-chemistry`;

    return this.http.get<Page<SimpleChemistryResponse>>(url, {
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
    testResult: CreateSimpleChemistryRequest
  ): Observable<ApiResponse> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/simple-chemistry`;

    return this.http.post<ApiResponse>(url, testResult);
  }

  // =====================================================
  // OPERACIONES SOBRE LOS RESULTADOS
  // =====================================================

  public getTestResult(
    id: number
  ): Observable<SimpleChemistryResponse> {

    const url = `${this.url}/simple-chemistry/${id}`;

    return this.http.get<SimpleChemistryResponse>(url);
  }

  public getPatientOrderTestResult(
    patientId: number,
    orderId: number,
    testResultId: number
): Observable<SimpleChemistryResponse> {

  const url =
    `${this.url}/patients/${patientId}/orders/${orderId}/simple-chemistry/${testResultId}`;

  return this.http.get<SimpleChemistryResponse>(url);
}


public updateTestResult(
  testResultId: number,
  testResult: UpdateSimpleChemistryRequest
): Observable<ApiResponse> {

  const url = `${this.url}/simple-chemistry/${testResultId}`;

  return this.http.put<ApiResponse>(url, testResult);
}



  public deleteTestResult(
    id: number
  ): Observable<ApiResponse> {

    const url = `${this.url}/simple-chemistry/${id}`;

    return this.http.delete<ApiResponse>(url);
  }
}
