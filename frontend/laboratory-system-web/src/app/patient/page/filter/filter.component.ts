import {
  Component,
  EventEmitter,
  Input,
  Output
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PatientFilter } from '../../components/patient-filter.component';

@Component({
  selector: 'app-patient-filter',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule
  ],

  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css',
})
export class PatientFilterComponent {

  @Input()
  filter: PatientFilter = {};


  @Output()
  filterChange = new EventEmitter<PatientFilter>();

  onFilterChange(): void {
    this.filterChange.emit({
      ...this.filter
    });
  }
}
