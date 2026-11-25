
import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { SimpleChemistryReportResponse } from '../../components/simple-chemistry.model';
import { TestMethodsPipe } from '../../../../../../utils/test-methods/test-methods.pipe';

@Component({
  selector: 'app-simple-chemistry-report',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    TestMethodsPipe
  ],
  templateUrl: './simple-chemistry-report.component.html',
  styleUrl: './simple-chemistry-report.component.scss'
})
export class SimpleChemistryReportComponent implements OnInit {

  @Input() results: SimpleChemistryReportResponse[] = [];

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

  get hasAnyReference(): boolean {
    return this.results.some(r => this.hasReference(r.reference));
  }

  hasReference(reference: string | null | undefined): boolean {
    return typeof reference === 'string' && reference.trim().length > 0;
  }
}
