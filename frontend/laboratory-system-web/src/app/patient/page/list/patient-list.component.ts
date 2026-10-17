import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { Gender, PatientResponse } from '../../components/patient.model';
import { PatientFilter } from '../../components/patient-filter.component';
import { PatientService } from '../../components/patient.service';
import { PaginatorComponent } from '../../../utils/paginator/paginator.component';
import { ModalComponent } from '../../../utils/modal/modal.component';
import { ModalService } from '../../../utils/modal/modal-service';
import { AlertService } from '../../../utils/alert/alert.service';
import { FilterUtils } from '../../../utils/filter/filter-utils.model';
import { PatientFilterComponent } from '../filter/filter.component';
import { AuthService } from '../../../auth/components/auth.service';


@Component({
  selector: 'app-test-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    PaginatorComponent,
    ModalComponent,
    PatientFilterComponent
    
  ],

  templateUrl: './patient-list.component.html',
  
})
export class PatientComponentList implements OnInit {

  patients: PatientResponse[] = [];

  filter: PatientFilter = {};

  draftFilter: PatientFilter = {};

  paginator: any;

  hasError = false;

  title: string = 'Pacientes'
  
  private readonly authService = inject(AuthService);
  protected readonly isAdmin = this.authService.isAdmin;
  protected readonly isUser = this.authService.isUser;

  genderLabels: Record<Gender, string> = {
  [Gender.FEMALE]: 'Mujer',
  [Gender.MALE]: 'Hombre'
};



  constructor(
    private patientService: PatientService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: ModalService,
    private alertService: AlertService
  ) {}


  ngOnInit(): void {

    
    this.activatedRoute.queryParamMap.subscribe((params) => {

      const page = params.get('page')
        ? +params.get('page')!
        : 0;

      const size = params.get('size')
        ? +params.get('size')!
        : 20;
    
    const sort = params.get('sort')
      ?? 'createdAt,desc';

      this.filter = FilterUtils.buildPatientFilter(params);

      this.loadPatients(page, size, sort);
    });
  }

 public onApplyFilters(newFilter: PatientFilter): void {

  const cleanedParams = {

    page: 0,

    search: newFilter.search?.trim()
      ? newFilter.search.trim()
      : null,

    gender: newFilter.gender ?? null,

    dateOfBirth: newFilter.dateOfBirth ?? null,

    createdAtAfter: newFilter.createdAtAfter ?? null,

    createdAtBefore: newFilter.createdAtBefore ?? null,

    updatedAtAfter: newFilter.updatedAtAfter ?? null,

    updatedAtBefore: newFilter.updatedAtBefore ?? null,
  };

  this.router.navigate([], {

    relativeTo: this.activatedRoute,

    queryParams: cleanedParams,

    queryParamsHandling: 'merge',

  });

 }

// public toggleActive(test: TestResponse): void {

//   const request$ = test.active
//     ? this.testService.deactivateTest(test.id)
//     : this.testService.activateTest(test.id);

//   request$.subscribe({

//     next: (response) => {

//       test.active = !test.active;

//       this.alertService.success(
//         response.message,
//         test.active
//           ? 'Test referencial activado'
//           : 'Test referencial desactivado'
//       );

//     },

//     error: (error) => {

//       console.error(error);

//       this.alertService.error(
//         'No fue posible cambiar el estado del test referencial.'
//       );

//     }

//   });
// }


public delete(patient: PatientResponse): void {

  this.alertService.confirmDelete(patient.firstName)
    .then(confirmed => {

      if (!confirmed) {
        return;
      }

      this.patientService.deletePatient(patient.id).subscribe({

        next: (response) => {

          this.patients = this.patients.filter(
            t => t.id !== patient.id
          );

        this.alertService.success(
          response.message,
          'Paciente eliminado'
          );
        },

        error: (error) => {

          console.error(error);

          this.alertService.error(
            'No fue posible eliminar el paciente.'
          );
        }

      });
    });
}

private loadPatients(page: number, size: number, sort: string): void {
    
    this.patientService.getPatients(
        page,
        size,
        this.filter,
        sort
      )
      .subscribe({

        next: (response) => {

          this.patients = response.content;

          this.paginator = response;

          this.hasError = false;

        },

        error: (err) => {

          this.hasError = true;

          console.error(err);

        },

      });
  }


// FILTERS
onSearch(event: Event): void {
  const input = event.target as HTMLInputElement;
  const name = input.value.trim();

  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      search: name || null
    },
    queryParamsHandling: 'merge'
  });
}

clearSearch(): void {
  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      search: null
    },
    queryParamsHandling: 'merge'
  });
}

onAscendingDate(): void {
  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      sort: 'createdAt,asc'
    },
    queryParamsHandling: 'merge'
  });
}

onDescendingDate(): void {
  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      sort: 'createdAt,desc'
    },
    queryParamsHandling: 'merge'
  });
}

openFilterModal(): void {
  this.draftFilter = {
    ...this.filter
  };

  this.modalService.openModal({
    type: 'filter',
    title: 'Filtros',
  });
}

applyFilters(): void {
  this.onApplyFilters(this.draftFilter);

  this.modalService.closeModal();
}

closeFilterModal(): void {
  this.modalService.closeModal();
}

resetModal(): void{
      this.draftFilter = {};
  this.filter = {};

  this.router.navigate([], {
    relativeTo: this.activatedRoute,
    queryParams: {
      page: 0,
      gender: null,
      search: null,
      createdAtAfter: null,
      createdAtBefore: null,
      updatedAtAfter: null,
      updatedAtBefore: null,
    },
    queryParamsHandling: 'merge',
  });
}


}




