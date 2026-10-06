import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashboardSummaryResponse, TestResultMonthlyCountProjection, TopRequestedTestProjection } from './metrics.model';

@Injectable({
  providedIn: 'root',
})
export class MetricsService {

  private url: string = 'http://localhost:8080/api/metrics';

  constructor(private http: HttpClient) {}



  public getDashboardSummary(): Observable<DashboardSummaryResponse> {

    const url = `${this.url}/summary`;

    return this.http.get<DashboardSummaryResponse>(url)
};


public getMostTop5RequestedTestResults(): Observable<TopRequestedTestProjection[]>{

    const url = `${this.url}/top-five-test-results`;

    return this.http.get<TopRequestedTestProjection[]>(url)
}

public getTestResultSaleTrend(
    startDate: Date,
    endDate: Date
): Observable<TestResultMonthlyCountProjection[]>{

    const url = `${this.url}/test-result-sale-trend`;

    const params = new HttpParams()
    .set('startDate', startDate.toISOString())
    .set('endDate', endDate.toISOString());

    return this.http.get<TestResultMonthlyCountProjection[]>(url, {
        params 
    })
}
}


