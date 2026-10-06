import {
  Component,
  EventEmitter,
  Input,
  Output
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderFilter } from '../../components/order-filter.component';

@Component({
  selector: 'app-order-filter',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule
  ],

  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css',
})
export class OrderFilterComponent {

  @Input()
  filter: OrderFilter = {};


  @Output()
  filterChange = new EventEmitter<OrderFilter>();

  onFilterChange(): void {
    this.filterChange.emit({
      ...this.filter
    });
  }
}
