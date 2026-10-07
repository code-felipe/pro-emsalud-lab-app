import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Backup } from './backup.model';

@Injectable({
  providedIn: 'root',
})
export class BackupService {

  private url: string = 'http://localhost:8080/api/backup';

  constructor(private http: HttpClient) {}

  public createBackup(): Observable<Backup> {
    return this.http.post<Backup>(this.url, {});
  }

  public cleanOldBackups(): Observable<void> {
    return this.http.delete<void>(`${this.url}/cleanup`);
  }

  public getLatestBackup(): Observable<Backup> {
  return this.http.get<Backup>(`${this.url}/latest`);
}

public getAllbackups(): Observable<Backup[]>{
    return this.http.get<Backup[]>(`${this.url}`)
}

}
