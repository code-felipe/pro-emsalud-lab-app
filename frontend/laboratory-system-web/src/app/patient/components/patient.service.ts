import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { PatientFilter } from './patient-filter.component';
import { CreatePatientRequest, PatientResponse, UpdatePatientRequest } from './patient.model';
import { Page } from '../../utils/paginator/paginator.model';
import { FilterUtils } from '../../utils/filter/filter-utils.model';
import { ApiResponse } from '../../utils/response/api-response';

@Injectable({
  providedIn: 'root',
})
export class PatientService {

  private url: string = 'http://localhost:8080/api/patients';

  constructor(private http: HttpClient) {}

  public getPatients(
    page: number,
    size: number,
    filter?: PatientFilter,
    sort?: string
  ): Observable<Page<PatientResponse>> {
    return this.http.get<Page<PatientResponse>>(this.url, {
      params: FilterUtils.buildHttpParams(page, size, filter, sort),
    });
  }

  public createPatient(test: CreatePatientRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.url,test);
  }

  public getPatient(id: number): Observable<PatientResponse> {
    return this.http.get<PatientResponse>(`${this.url}/${id}`);
  }

  public updatePatient(id: number, test: UpdatePatientRequest): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.url}/${id}`,test);
  }

  public deletePatient(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.url}/${id}`);
  }

//   public activateTest(id: number): Observable<ApiResponse>{
//     return this.http.patch<ApiResponse>(`${this.url}/${id}/activate`, {});
//   }

//    public deactivateTest(id: number): Observable<ApiResponse>{
//     return this.http.patch<ApiResponse>(`${this.url}/${id}/deactivate`, {});
//   }

  public uploadPicture(file: File,id: number): Observable<PatientResponse> {

    const formData = new FormData();

    formData.append('file', file);
    formData.append('id', id.toString());

    return this.http.post<PatientResponse>(`${this.url}/upload`,formData);
  }
}

