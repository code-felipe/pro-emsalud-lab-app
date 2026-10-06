import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { CreateTestRequest, TestResponse, TestSearchResponse, UpdateTestRequest } from './test.model';
import { Page } from '../../../utils/paginator/paginator.model';
import { TestFilter } from './test-filter.component';
import { FilterUtils } from '../../../utils/filter/filter-utils.model';
import { ApiResponse } from '../../../utils/response/api-response';

@Injectable({
  providedIn: 'root',
})
export class TestService {

  private url: string = 'http://localhost:8080/api/tests';

  constructor(private http: HttpClient) {}

  public getTests(
    page: number,
    size: number,
    filter?: TestFilter,
    sort?: string
  ): Observable<Page<TestResponse>> {
    return this.http.get<Page<TestResponse>>(this.url, {
      params: FilterUtils.buildHttpParams(page, size, filter, sort),
    });
  }

  public createTest(test: CreateTestRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.url,test);
  }

  public getTest(id: number): Observable<TestResponse> {
    return this.http.get<TestResponse>(`${this.url}/${id}`);
  }

  public updateTest(id: number, test: UpdateTestRequest): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.url}/${id}`,test);
  }

  public deleteTest(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.url}/${id}`);
  }

  public activateTest(id: number): Observable<ApiResponse>{
    return this.http.patch<ApiResponse>(`${this.url}/${id}/activate`, {});
  }

   public deactivateTest(id: number): Observable<ApiResponse>{
    return this.http.patch<ApiResponse>(`${this.url}/${id}/deactivate`, {});
  }

  public uploadPicture(file: File,id: number): Observable<TestResponse> {

    const formData = new FormData();

    formData.append('file', file);
    formData.append('id', id.toString());

    return this.http.post<TestResponse>(`${this.url}/upload`,formData);
  }

// Search by test name and/or category name
public searchTests(
  query: string
): Observable<TestSearchResponse> {

  return this.http.get<TestSearchResponse>(`${this.url}/search`, {
    params: {
      query
    }
  });
}


}

