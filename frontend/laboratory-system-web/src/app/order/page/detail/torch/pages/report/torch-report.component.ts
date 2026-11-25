
import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { TorchReportResponse } from '../../components/torch.model';
import { TestMethodsPipe } from '../../../../../../utils/test-methods/test-methods.pipe';

@Component({
  selector: 'app-torch-report',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    TestMethodsPipe
  ],
  templateUrl: './torch-report.component.html',
  styleUrl: './torch-report.component.scss'
})
export class TorchReportComponent implements OnInit {

  @Input() torchResults: TorchReportResponse[] = [];

  public patientId!: number;
  public orderId!: number;

  constructor(
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadRouteParams();
  }

  private loadRouteParams(): void {
    const patientId = this.route.parent?.snapshot.paramMap.get('id');
    const orderId = this.route.snapshot.paramMap.get('orderId');

    if (patientId) {
      this.patientId = +patientId;
    }

    if (orderId) {
      this.orderId = +orderId;
    }
  }

  public displayResult(value?: string | null): string {
    if (value == null || value.trim() === '') {
      return 'N/A';
    }

    return value;
  }

  public getResultClass(value?: string | null): string {
    if (!value || value.trim() === '') {
      return 'result-negative';
    }

    return value.toUpperCase() === 'POSITIVO'
      ? 'result-positive'
      : 'result-negative';
  }
}
