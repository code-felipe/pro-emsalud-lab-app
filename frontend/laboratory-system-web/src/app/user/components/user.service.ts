import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../utils/response/api-response';
import { CreateUserRequest, RoleResponse, UpdateUserRequest, UploadPictureResponse, UserResponse } from './user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  private url: string = 'http://localhost:8080/api/users';
  private urlRole: string = 'http://localhost:8080/api/roles';

  constructor(private http: HttpClient) {}

  public getUsers(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(this.url, {});
  }

  public getRoles(): Observable<RoleResponse[]>{
    return this.http.get<RoleResponse[]>(this.urlRole,{})
  }

  public createUser(user: CreateUserRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.url,user);
  }

  public getUser(id: number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.url}/${id}`);
  }

  public updateUser(id: number, user: UpdateUserRequest): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.url}/${id}`,user);
  }

  public deleteUser(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.url}/${id}`);
  }

  public toggleStatus(id: number): Observable<void> {
  return this.http.patch<void>(
    `${this.url}/${id}/toggle-status`,
    {}
  );
}

  public uploadPicture(file: File, id: number): Observable<UploadPictureResponse> {

    const formData = new FormData();

    formData.append('file', file);
    formData.append('id', id.toString());

    return this.http.post<UploadPictureResponse>(`${this.url}/upload`,formData);
  }

}

// public uploadPicture(
//     id: number,
//     file: File
//   ): Observable<{ message: string; user: UserResponse }> {

//     const formData = new FormData();

//     formData.append('file', file);
//     formData.append('id', id.toString());

//     return this.http.post<{
//       message: string;
//       user: UserResponse;
//     }>(
//       `${this.url}/upload`,
//       formData
//     );
//   }

// public getPictureUrl(fileName: string): string {
//     return `${this.url}/uploads/img/${encodeURIComponent(fileName)}`;
//   }
// }

//   public activateTest(id: number): Observable<ApiResponse>{
//     return this.http.patch<ApiResponse>(`${this.url}/${id}/activate`, {});
//   }

//    public deactivateTest(id: number): Observable<ApiResponse>{
//     return this.http.patch<ApiResponse>(`${this.url}/${id}/deactivate`, {});
//   }




