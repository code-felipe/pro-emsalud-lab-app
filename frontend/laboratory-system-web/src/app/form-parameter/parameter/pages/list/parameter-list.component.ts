
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { ParameterResponse } from '../../component/parameter.model';
import { ParameterService } from '../../component/parameter.service';
import { PaginatorComponent } from '../../../../utils/paginator/paginator.component';
import { AlertService } from '../../../../utils/alert/alert.service';

@Component({
  selector: 'app-parameter-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    PaginatorComponent,
  ],

  templateUrl: './parameter-list.component.html',
})
export class ParameterComponentList implements OnInit {

  parameters: ParameterResponse[] = [];

  paginator: any;

  hasError = false;

  title: string = 'Parámetros';

  constructor(
    private parameterService: ParameterService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
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

      this.loadParameters(page, size);
    });
  }

  private loadParameters(page: number, size: number): void {

    this.parameterService.getParameters(page, size)
      .subscribe({

        next: (response) => {

          this.parameters = response.content;

          this.paginator = response;

          this.hasError = false;

        },

        error: (err) => {

          this.hasError = true;

          console.error(err);

        },

      });
  }

  public delete(parameter: ParameterResponse): void {

    this.alertService.confirmDelete(parameter.name)
      .then(confirmed => {

        if (!confirmed) {
          return;
        }

        this.parameterService.deleteParameter(parameter.id).subscribe({

          next: (response) => {

            this.parameters = this.parameters.filter(
              p => p.id !== parameter.id
            );

            this.alertService.success(
              response.message,
              'Parámetro eliminado'
            );
          },

          error: (error) => {

            console.error(error);

            this.alertService.error(
              error.error?.message ?? 'No fue posible eliminar el parámetro.'
            );
          }

        });
      });
  }

}
