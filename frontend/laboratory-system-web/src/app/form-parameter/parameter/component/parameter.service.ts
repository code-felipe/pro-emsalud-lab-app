import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { CreateParameterRequest, ParameterResponse, UpdateParameterRequest } from './parameter.model';
import { Page } from '../../../utils/paginator/paginator.model';
import { ApiResponse } from '../../../utils/response/api-response';


@Injectable({
  providedIn: 'root',
})
export class ParameterService {
  private url: string = 'http://localhost:8080/api/parameters';

  constructor(private http: HttpClient) {}

public getParameters(
  page: number,
  size: number,
): Observable<Page<ParameterResponse>> {

  const params = new HttpParams()
    .set('page', page)
    .set('size', size);

  return this.http.get<Page<ParameterResponse>>(this.url,{ params });
}


  public createParameter(param: CreateParameterRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.url, param);
  }

  public getParameter(id: number): Observable<ParameterResponse> {
    return this.http.get<ParameterResponse>(`${this.url}/${id}`);
  }

  public updateParameter(id: number, param: UpdateParameterRequest): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.url}/${id}`, param);
  }

  public deleteParameter(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.url}/${id}`);
  }

  //== USE FOR URIANALYSIS AND STOOL TESTS ==
  public findByExamType(examType: string): Observable<ParameterResponse[]>{
    return this.http.get<ParameterResponse[]>(`${this.url}/exam/${examType}`);
  }
}
