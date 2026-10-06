import { Component, OnInit } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { BaseChartDirective } from 'ng2-charts';

import {
  Chart,
  ChartConfiguration,
  ChartData,
  registerables
} from 'chart.js';

import {
  DashboardSummaryResponse,
  TestResultMonthlyCountProjection,
  TopRequestedTestProjection
} from '../components/metrics.model';

import { MetricsService } from '../components/metrics.service';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    DecimalPipe,
    FormsModule,
    BaseChartDirective
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  dashboardSummary!: DashboardSummaryResponse;

  loading = true;
  error = false;

  startDate!: string;
  endDate!: string;

  trendLoading = false;
  trendError = false;

  topRequestedTests: TopRequestedTestProjection[] = [];

  topRequestedTestsLoading = false;
  topRequestedTestsError = false;

  public lineChartType: 'line' = 'line';

  public lineChartData: ChartData<'line'> = {
    labels: [],

    datasets: [
      {
        label: 'Ventas',
        data: [],
        borderColor: '#334155',
        backgroundColor: 'rgba(51, 65, 85, 0.08)',
        borderWidth: 2,
        pointBackgroundColor: '#334155',
        pointBorderColor: '#ffffff',
        pointBorderWidth: 1,
        pointRadius: 3,
        pointHoverRadius: 5,
        tension: 0.3,
        fill: true
      },
      {
        label: 'Resultados',
        data: [],
        borderColor: '#94a3b8',
        backgroundColor: 'rgba(148, 163, 184, 0.05)',
        borderWidth: 2,
        pointBackgroundColor: '#94a3b8',
        pointBorderColor: '#ffffff',
        pointBorderWidth: 1,
        pointRadius: 3,
        pointHoverRadius: 5,
        tension: 0.3,
        fill: false
      }
    ]
  };

  public lineChartOptions:
    ChartConfiguration<'line'>['options'] = {

    responsive: true,

    maintainAspectRatio: false,

    interaction: {
      mode: 'index',
      intersect: false
    },

    plugins: {
      legend: {
        display: true,
        position: 'top',

        labels: {
          usePointStyle: true,
          padding: 15,
          color: '#64748b'
        }
      },

      tooltip: {
        enabled: true
      }
    },

    scales: {
      x: {
        type: 'category',

        grid: {
          display: false
        },

        ticks: {
          color: '#64748b'
        }
      },

      y: {
        type: 'linear',

        beginAtZero: true,

        grid: {
          color: '#eef1f4'
        },

        ticks: {
          color: '#64748b',

          callback: (value) => {
            return Number(value).toLocaleString();
          }
        }
      }
    }
  };

  constructor(
    private metricsService: MetricsService
  ) {}

  ngOnInit(): void {

    this.setDefaultDates();

    this.loadDashboardSummary();

    this.loadTestResultSaleTrend();

    this.loadTopRequestedTests();
  }

  private loadDashboardSummary(): void {

    this.loading = true;
    this.error = false;

    this.metricsService
      .getDashboardSummary()
      .subscribe({

        next: (response) => {

          console.log(
            'Dashboard response:',
            response
          );

          this.dashboardSummary = response;

          this.loading = false;
        },

        error: (error) => {

          console.error(
            'Error loading dashboard summary:',
            error
          );

          this.error = true;

          this.loading = false;
        }
      });
  }

  private setDefaultDates(): void {

    const today = new Date();

    const sixMonthsAgo = new Date(
      today.getFullYear(),
      today.getMonth() - 5,
      1
    );

    this.startDate = this.formatDate(
      sixMonthsAgo
    );

    this.endDate = this.formatDate(
      today
    );
  }

  private formatDate(
    date: Date
  ): string {

    const year = date.getFullYear();

    const month = String(
      date.getMonth() + 1
    ).padStart(2, '0');

    const day = String(
      date.getDate()
    ).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  public loadTestResultSaleTrend(): void {

    if (
      !this.startDate ||
      !this.endDate
    ) {
      return;
    }

    if (
      this.startDate >
      this.endDate
    ) {

      this.trendError = true;

      console.error(
        'La fecha inicial no puede ser mayor que la fecha final.'
      );

      return;
    }

    const startDate = new Date(
      `${this.startDate}T00:00:00`
    );

    const endDate = this.getNextDay(
      this.endDate
    );

    this.trendLoading = true;
    this.trendError = false;

    this.metricsService
      .getTestResultSaleTrend(
        startDate,
        endDate
      )
      .subscribe({

        next: (
          response:
            TestResultMonthlyCountProjection[]
        ) => {

          console.log(
            'Trend response:',
            response
          );

          this.updateChart(
            response
          );

          this.trendLoading = false;
        },

        error: (error) => {

          console.error(
            'Error loading trend:',
            error
          );

          this.trendError = true;

          this.trendLoading = false;
        }
      });
  }

  private getNextDay(
    dateString: string
  ): Date {

    const date = new Date(
      `${dateString}T00:00:00`
    );

    date.setDate(
      date.getDate() + 1
    );

    return date;
  }

  private updateChart(
    data: TestResultMonthlyCountProjection[]
  ): void {

    this.lineChartData = {

      labels: data.map(
        item => item.monthName
      ),

      datasets: [
        {
          label: 'Ventas',

          data: data.map(
            item => Number(item.sale) || 0
          ),

          borderColor: '#334155',

          backgroundColor:
            'rgba(51, 65, 85, 0.08)',

          borderWidth: 2,

          pointBackgroundColor:
            '#334155',

          pointBorderColor:
            '#ffffff',

          pointBorderWidth: 1,

          pointRadius: 3,

          pointHoverRadius: 5,

          tension: 0.3,

          fill: true
        },

        {
          label: 'Resultados',

          data: data.map(
            item => Number(item.total) || 0
          ),

          borderColor: '#94a3b8',

          backgroundColor:
            'rgba(148, 163, 184, 0.05)',

          borderWidth: 2,

          pointBackgroundColor:
            '#94a3b8',

          pointBorderColor:
            '#ffffff',

          pointBorderWidth: 1,

          pointRadius: 3,

          pointHoverRadius: 5,

          tension: 0.3,

          fill: false
        }
      ]
    };
  }

  private loadTopRequestedTests(): void {

    this.topRequestedTestsLoading = true;

    this.topRequestedTestsError = false;

    this.metricsService
      .getMostTop5RequestedTestResults()
      .subscribe({

        next: (
          response: TopRequestedTestProjection[]
        ) => {

          console.log(
            'Top requested tests:',
            response
          );

          this.topRequestedTests = response;

          this.topRequestedTestsLoading = false;
        },

        error: (error) => {

          console.error(
            'Error loading top requested tests:',
            error
          );

          this.topRequestedTestsError = true;

          this.topRequestedTestsLoading = false;
        }
      });
  }
}
