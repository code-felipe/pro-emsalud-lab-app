import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { TestResultFilter } from '../../../../../results/components/result-filter.component';
import { Page } from '../../../../../utils/paginator/paginator.model';
import { FilterUtils } from '../../../../../utils/filter/filter-utils.model';
import { ApiResponse } from '../../../../../utils/response/api-response';
import { CreateTorchRequest, TorchResponse, UpdateTorchRequest } from './torch.model';

@Injectable({
  providedIn: 'root',
})
export class TorchService {

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
  ): Observable<Page<TorchResponse>> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/torchs`;

    return this.http.get<Page<TorchResponse>>(url, {
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
  ): Observable<Page<TorchResponse>> {

    const url = `${this.url}/patients/${patientId}/torchs`;

    return this.http.get<Page<TorchResponse>>(url, {
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
    testResult: CreateTorchRequest
  ): Observable<ApiResponse> {

    const url = `${this.url}/patients/${patientId}/orders/${orderId}/torchs`;

    return this.http.post<ApiResponse>(url, testResult);
  }

  // =====================================================
  // OPERACIONES SOBRE LOS RESULTADOS
  // =====================================================

  public getTestResult(
    id: number
  ): Observable<TorchResponse> {

    const url = `${this.url}/torchs/${id}`;

    return this.http.get<TorchResponse>(url);
  }

  public getPatientOrderTestResult(
    patientId: number,
    orderId: number,
    testResultId: number
): Observable<TorchResponse> {

  const url =
    `${this.url}/patients/${patientId}/orders/${orderId}/torchs/${testResultId}`;

  return this.http.get<TorchResponse>(url);
}


public updateTestResult(
  testResultId: number,
  testResult: UpdateTorchRequest
): Observable<ApiResponse> {

  const url = `${this.url}/torchs/${testResultId}`;

  return this.http.put<ApiResponse>(url, testResult);
}



  public deleteTestResult(
    id: number
  ): Observable<ApiResponse> {

    const url = `${this.url}/torchs/${id}`;

    return this.http.delete<ApiResponse>(url);
  }
}
