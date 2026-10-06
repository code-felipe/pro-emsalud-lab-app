import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';

import { DoctorResponse } from '../../doctor/components/doctor.model';
import { DoctorService } from '../../doctor/components/doctor.service';

@Component({
  selector: 'app-doctor-selector',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './doctor-selector.component.html',
  styleUrl: './doctor-selector.component.css',
})
export class DoctorSelectorComponent {

  @Input()
  selectedDoctor?: DoctorResponse;

  @Output()
  doctorSelected = new EventEmitter<DoctorResponse | undefined>();

  public search = '';

  public doctors: DoctorResponse[] = [];

  public apiError: any = null;

  constructor(
    private doctorService: DoctorService
  ) {}

  searchDoctor(): void {

    if (!this.search.trim()) {
      this.doctors = [];
      return;
    }

    this.apiError = null;

    this.doctorService.searchByName(this.search).subscribe({

      next: (response) => {

        this.doctors = response.doctors;

      },

      error: (err: HttpErrorResponse) => {

        this.apiError = err.error;

        Swal.fire(
          'Error',
          err.error?.message ??
            'No se pudo cargar la búsqueda.',
          'error'
        );

      },

    });
  }

  selectDoctor(doctor: DoctorResponse): void {

    this.selectedDoctor = doctor;

    this.doctors = [];

    this.search = '';

    this.doctorSelected.emit(doctor);
  }

  clearSelection(): void {

    this.selectedDoctor = undefined;

    this.doctors = [];

    this.search = '';

    this.doctorSelected.emit(undefined);
  }
}
