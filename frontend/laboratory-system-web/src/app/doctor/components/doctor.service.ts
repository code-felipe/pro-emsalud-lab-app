import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { CreateDoctorRequest, DoctorResponse, DoctorSearchResponse, UpdateDoctorRequest } from './doctor.model';
import { Page } from '../../utils/paginator/paginator.model';
import { ApiResponse } from '../../utils/response/api-response';


@Injectable({
  providedIn: 'root',
})
export class DoctorService {
  private url: string = 'http://localhost:8080/api/doctors';

  constructor(private http: HttpClient) {}

public getDoctors(
  page: number,
  size: number,
): Observable<Page<DoctorResponse>> {

  const params = new HttpParams()
    .set('page', page)
    .set('size', size);

  return this.http.get<Page<DoctorResponse>>(this.url,{ params });
}


  public createDoctor(test: CreateDoctorRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.url, test);
  }

  public getDoctor(id: number): Observable<DoctorResponse> {
    return this.http.get<DoctorResponse>(`${this.url}/${id}`);
  }

  public updateDoctor(id: number, test: UpdateDoctorRequest): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.url}/${id}`, test);
  }

  public deleteDoctor(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.url}/${id}`);
  }

  public searchByName(name: string): Observable<DoctorSearchResponse>{
      return this.http.get<DoctorSearchResponse>(`${this.url}/search`, {
      params: {
        name: name,
      },
    });
  }

  public uploadPicture(file: File, id: number): Observable<DoctorResponse>{
    let formData = new FormData();
    formData.append("file", file);
    formData.append("id", id.toString());

    return this.http.post<DoctorResponse>(`${this.url}/upload`, formData);
  }
}
