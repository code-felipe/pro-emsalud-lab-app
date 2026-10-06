import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { PaginatorComponent } from '../../../utils/paginator/paginator.component';
import { ModalComponent } from '../../../utils/modal/modal.component';
import { ModalService } from '../../../utils/modal/modal-service';
import { AlertService } from '../../../utils/alert/alert.service';
import { DoctorResponse } from '../../components/doctor.model';
import { DoctorService } from '../../components/doctor.service';


@Component({
  selector: 'app-doctor-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    PaginatorComponent,
    // ModalComponent,
    
  ],

  templateUrl: './doctor-list.component.html',
  
})
export class DoctorComponentList implements OnInit {

  doctors: DoctorResponse[] = [];

//   filter: PatientFilter = {};

//   draftFilter: PatientFilter = {};

  paginator: any;

  hasError = false;

  title: string = 'Contactos'

  constructor(
    private doctorService: DoctorService,
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

    //   this.filter = FilterUtils.buildPatientFilter(params);

      this.loadDoctors(page, size);
    });
  }

//  public onApplyFilters(newFilter: PatientFilter): void {

//   const cleanedParams = {

//     page: 0,

//     search: newFilter.search?.trim()
//       ? newFilter.search.trim()
//       : null,

//     gender: newFilter.gender ?? null,

//     dateOfBirth: newFilter.dateOfBirth ?? null,

//     createdAtAfter: newFilter.createdAtAfter ?? null,

//     createdAtBefore: newFilter.createdAtBefore ?? null,

//     updatedAtAfter: newFilter.updatedAtAfter ?? null,

//     updatedAtBefore: newFilter.updatedAtBefore ?? null,
//   };

//   this.router.navigate([], {

//     relativeTo: this.activatedRoute,

//     queryParams: cleanedParams,

//     queryParamsHandling: 'merge',

//   });

//  }

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


public delete(doctor: DoctorResponse): void {

  this.alertService.confirmDelete(doctor.firstName)
    .then(confirmed => {

      if (!confirmed) {
        return;
      }

      this.doctorService.deleteDoctor(doctor.id).subscribe({

        next: (response) => {

          this.doctors = this.doctors.filter(
            t => t.id !== doctor.id
          );

        this.alertService.success(
          response.message,
          'Doctor eliminado'
          );
        },

        error: (error) => {

          console.error(error);

          this.alertService.error(
            error.error?.message ?? 'No fue posible eliminar la ficha del doctor.'
          );
        }

      });
    });
}

private loadDoctors(page: number, size: number): void {
    
    this.doctorService.getDoctors(
        page,
        size
      )
      .subscribe({

        next: (response) => {

          this.doctors = response.content;

          this.paginator = response;

          this.hasError = false;

        },

        error: (err) => {

          this.hasError = true;

          console.error(err);

        },

      });
  }

getInitials(name: string): string {
  return (name ?? '')
    .trim()
    .split(/\s+/)
    .slice(0, 2)
    .map(part => part.charAt(0).toUpperCase())
    .join('');
}

trackById(_: number, doctor: { id: number }): number {
  return doctor.id;
}
  

// FILTERS
// onSearch(event: Event): void {
//   const input = event.target as HTMLInputElement;
//   const name = input.value.trim();

//   this.router.navigate([], {
//     relativeTo: this.activatedRoute,
//     queryParams: {
//       page: 0,
//       search: name || null
//     },
//     queryParamsHandling: 'merge'
//   });
// }

// clearSearch(): void {
//   this.router.navigate([], {
//     relativeTo: this.activatedRoute,
//     queryParams: {
//       page: 0,
//       search: null
//     },
//     queryParamsHandling: 'merge'
//   });
// }

// onAscendingDate(): void {
//   this.router.navigate([], {
//     relativeTo: this.activatedRoute,
//     queryParams: {
//       page: 0,
//       sort: 'createdAt,asc'
//     },
//     queryParamsHandling: 'merge'
//   });
// }

// onDescendingDate(): void {
//   this.router.navigate([], {
//     relativeTo: this.activatedRoute,
//     queryParams: {
//       page: 0,
//       sort: 'createdAt,desc'
//     },
//     queryParamsHandling: 'merge'
//   });
// }

// openFilterModal(): void {
//   this.draftFilter = {
//     ...this.filter
//   };

//   this.modalService.openModal({
//     type: 'filter',
//     title: 'Filtros',
//   });
// }

// applyFilters(): void {
//   this.onApplyFilters(this.draftFilter);

//   this.modalService.closeModal();
// }

// closeFilterModal(): void {
//   this.modalService.closeModal();
// }

// resetModal(): void{
//       this.draftFilter = {};
//   this.filter = {};

//   this.router.navigate([], {
//     relativeTo: this.activatedRoute,
//     queryParams: {
//       page: 0,
//       gender: null,
//       search: null,
//       createdAtAfter: null,
//       createdAtBefore: null,
//       updatedAtAfter: null,
//       updatedAtBefore: null,
//     },
//     queryParamsHandling: 'merge',
//   });
// }


}




