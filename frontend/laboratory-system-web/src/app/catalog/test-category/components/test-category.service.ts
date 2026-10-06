import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';

import { Page } from '../../../utils/paginator/paginator.model';
import { CreateTestCategoryRequest, TestCategoryResponse, UpdateTestCategoryRequest } from './test-category.model';
import { ApiResponse } from '../../../utils/response/api-response';


@Injectable({
  providedIn: 'root',
})
export class TestCategoryService {
  private url: string = 'http://localhost:8080/api/test-categories';

  constructor(private http: HttpClient) {}

public getTestCategories(
  page: number,
  size: number,
): Observable<Page<TestCategoryResponse>> {

  const params = new HttpParams()
    .set('page', page)
    .set('size', size);

  return this.http.get<Page<TestCategoryResponse>>(this.url,{ params });
}


  public createTestCategory(test: CreateTestCategoryRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.url, test);
  }

  public getTestCategory(id: number): Observable<TestCategoryResponse> {
    return this.http.get<TestCategoryResponse>(`${this.url}/${id}`);
  }

  public updateTestCategory(id: number, test: UpdateTestCategoryRequest): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.url}/${id}`, test);
  }

  public deleteTestCategory(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.url}/${id}`);
  }

  public uploadPicture(file: File, id: number): Observable<TestCategoryResponse>{
    let formData = new FormData();
    formData.append("file", file);
    formData.append("id", id.toString());

    return this.http.post<TestCategoryResponse>(`${this.url}/upload`, formData);
  }
}
