import {
  Component,
  EventEmitter,
  Input,
  Output
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TestResultFilter } from '../../components/result-filter.component';

@Component({
  selector: 'app-test-result-filter',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule
  ],

  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css',
})
export class TestResultFilterComponent {

  @Input()
  filter: TestResultFilter = {};


  @Output()
  filterChange = new EventEmitter<TestResultFilter>();

  onFilterChange(): void {
    this.filterChange.emit({
      ...this.filter
    });
  }
}
