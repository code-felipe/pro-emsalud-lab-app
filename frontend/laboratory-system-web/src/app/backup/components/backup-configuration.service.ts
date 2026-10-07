import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { ApiResponse } from '../../utils/response/api-response';
import { BackupConfiguration, UpdateBackupConfigurationRequest } from './backup.model';

@Injectable({
  providedIn: 'root',
})
export class BackupConfigurationService {

  private url: string = 'http://localhost:8080/api/backup/configuration';

  constructor(private http: HttpClient) {}

  public getConfiguration(): Observable<BackupConfiguration> {
    return this.http.get<BackupConfiguration>(this.url);
  }

  public updateConfiguration(
    id: number,
    request: UpdateBackupConfigurationRequest): Observable<ApiResponse> {

    return this.http.put<ApiResponse>(`${this.url}/${id}`,request);
  }
}
