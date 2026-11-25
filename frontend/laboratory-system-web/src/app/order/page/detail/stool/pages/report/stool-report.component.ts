
import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { StoolReportResponse } from '../../component/stool.model';
import { TestMethodsPipe } from '../../../../../../utils/test-methods/test-methods.pipe';

@Component({
  selector: 'app-stool-report',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    TestMethodsPipe
  ],
  templateUrl: './stool-report.component.html',
  styleUrl: './stool-report.component.scss'
})
export class StoolReportComponent implements OnInit {

  @Input() results: StoolReportResponse[] = [];

  title = 'Coproanálisis';

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

  hasReference(reference: string | null | undefined): boolean {
    return typeof reference === 'string' && reference.trim().length > 0;
  }
}
