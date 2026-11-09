import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { PaginatorComponent } from '../../../utils/paginator/paginator.component';
import { ModalComponent } from '../../../utils/modal/modal.component';
import { AllTestResultsHistory } from '../../components/patient.model';
import { PatientService } from '../../components/patient.service';

@Component({
  selector: 'app-test-result-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    PaginatorComponent
  ],
  templateUrl: './patient-history.component.html',
})
export class PatientHistoryComponent implements OnInit {

  patientId!: number;

  results: AllTestResultsHistory[] = [];

  paginator: any;

  hasError = false;

  title = 'Historial de resultados';

  constructor(
    private patientService: PatientService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

ngOnInit(): void {

  console.group('🔍 DEBUG PATIENT HISTORY');

  console.log('Current URL:', this.router.url);

  console.log('Current route:', this.activatedRoute.snapshot.params);

  console.log('Parent route:', this.activatedRoute.parent?.snapshot.params);

  const patientParam =
    this.activatedRoute.parent?.snapshot.paramMap.get('id');

  console.log('Patient ID parameter:', patientParam);

  this.patientId = Number(patientParam);

  console.log('Patient ID converted:', this.patientId);

  if (!patientParam || !Number.isInteger(this.patientId) || this.patientId <= 0) {

    console.error('❌ Patient ID inválido');

    this.hasError = true;

    console.groupEnd();

    return;
  }

  console.log('✅ Patient ID válido:', this.patientId);

  console.groupEnd();

  this.activatedRoute.queryParamMap.subscribe(params => {

    const page = Number(params.get('page') ?? 0);
    const size = Number(params.get('size') ?? 10);
    const sort = params.get('sort') ?? 'orderCreatedAt,desc';

    console.log('📄 Pagination:', { page, size, sort });

    this.loadTestResults(page, size, sort);

  });
}

  private loadTestResults(
    page: number,
    size: number,
    sort: string
  ): void {

    console.group('🚀 HTTP REQUEST - PATIENT HISTORY');

    console.log('Patient ID:', this.patientId);
    console.log('Page:', page);
    console.log('Size:', size);
    console.log('Sort:', sort);

    console.log(
      'Expected endpoint:',
      `http://localhost:8080/api/patients/${this.patientId}/results`
    );

    console.groupEnd();

    this.patientService.getPatientOrderResults(
      this.patientId,
      page,
      size,
      sort
    ).subscribe({

      next: (response) => {

        console.group('✅ HTTP RESPONSE - PATIENT HISTORY');

        console.log('Full response:', response);
        console.log('Content:', response.content);
        console.log('Total elements:', response.totalElements);
        console.log('Total pages:', response.totalPages);
        console.log('Current page:', response.number);
        console.log('Page size:', response.size);
        console.log('Number of elements:', response.numberOfElements);
        console.log('Is empty:', response.empty);

        if (response.content?.length > 0) {

          console.table(response.content);

        } else {

          console.warn('⚠️ No se encontraron resultados para este paciente');

        }

        console.groupEnd();

        this.results = response.content ?? [];

        this.paginator = response;

        this.hasError = false;

        console.log('📦 Results assigned:', this.results);

      },

      error: (error) => {

        console.group('❌ HTTP ERROR - PATIENT HISTORY');

        console.error('Full error:', error);
        console.error('Status:', error.status);
        console.error('Status text:', error.statusText);
        console.error('Error body:', error.error);
        console.error('URL:', error.url);

        console.groupEnd();

        this.hasError = true;

      }

    });
  }

  onAscendingDate(): void {

    this.router.navigate([], {
      queryParams: {
        page: 0,
        sort: 'orderCreatedAt,asc'
      },
      queryParamsHandling: 'merge'
    });

  }

  onDescendingDate(): void {

    this.router.navigate([], {
      queryParams: {
        page: 0,
        sort: 'orderCreatedAt,desc'
      },
      queryParamsHandling: 'merge'
    });

  }

  public onPageChange(page: number): void {

    this.router.navigate([], {
      queryParams: {
        page: page
      },
      queryParamsHandling: 'merge'
    });

  }

  public goBack(): void {

    this.router.navigate([
      '/patients',
      this.patientId
    ]);

  }

}
