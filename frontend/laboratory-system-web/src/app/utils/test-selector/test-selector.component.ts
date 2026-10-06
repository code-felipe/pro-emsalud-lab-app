import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { TestResponse } from '../../catalog/test/components/test.model';
import { TestService } from '../../catalog/test/components/test.service';

@Component({
  selector: 'app-test-selector',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './test-selector.component.html',
  styleUrl: './test-selector.component.css',
})
export class TestSelectorComponent {

  @Input()
  selectedTest?: TestResponse;

  @Output()
  testSelected = new EventEmitter<TestResponse | undefined>();

  public search = '';

  public tests: TestResponse[] = [];

  public apiError: any = null;

  constructor(
    private testService: TestService
  ) {}

  searchTest(): void {

    if (!this.search.trim()) {
      this.tests = [];
      return;
    }

    this.apiError = null;

    this.testService.searchTests(this.search).subscribe({

      next: (response) => {

        this.tests = response.tests;

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

  selectTest(test: TestResponse): void {

    this.selectedTest = test;

    this.tests = [];

    this.search = '';

    this.testSelected.emit(test);
  }

  clearSelection(): void {

    this.selectedTest = undefined;

    this.tests = [];

    this.search = '';

    this.testSelected.emit(undefined);
  }
}
