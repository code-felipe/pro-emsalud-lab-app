import {
  Component,
  EventEmitter,
  Input,
  Output
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TestFilter } from '../../components/test-filter.component';
import { TestCategoryResponse } from '../../../test-category/components/test-category.model';

@Component({
  selector: 'app-test-filter',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule
  ],

  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css',
})
export class TestFilterComponent {

  @Input()
  filter: TestFilter = {};

  @Input()
  testCategories: TestCategoryResponse[] = [];

  @Output()
  filterChange = new EventEmitter<TestFilter>();

  onFilterChange(): void {
    this.filterChange.emit({
      ...this.filter
    });
  }
}
