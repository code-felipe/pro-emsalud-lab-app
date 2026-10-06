import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { Gender, PatientResponse } from '../../components/patient.model';
import { PatientService } from '../../components/patient.service';

@Component({
  selector: 'app-patient-report-detail',
  standalone: true,
  imports: [
    RouterLink, FormsModule,
    CommonModule,
    RouterLinkActive,
    // RouterOutlet,
  ],
  templateUrl: './patient-report.component.html',
})
export class PatientReportComponent implements OnInit  {

  ngOnInit(){

  }

}
