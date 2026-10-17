import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  FormsModule,
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators
} from '@angular/forms';
import { BackupConfiguration, UpdateBackupConfigurationRequest } from '../components/backup.model';
import { BackupConfigurationService } from '../components/backup-configuration.service';
import { AlertService } from '../../utils/alert/alert.service';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/components/auth.service';



@Component({
  selector: 'app-backup-configuration',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],

  templateUrl: './backup-configuration.component.html',
})
export class BackupConfigurationComponent implements OnInit {

  configuration: BackupConfiguration | null = null;

  form!: FormGroup;

  hasError = false;
  loading = false;
  saving = false;
  
  private readonly authService = inject(AuthService);
  protected readonly isAdmin = this.authService.isAdmin;

  title: string = 'Configuración de backups';

  constructor(
    private backupConfigurationService: BackupConfigurationService,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.createForm();

    this.loadConfiguration();
  }

  private createForm(): void {

    this.form = this.formBuilder.group({

      enabled: [false],

      intervalHours: [
        null,
        [
          Validators.required,
          Validators.min(1)
        ]
      ],

      dailyTime: [
        null,
        Validators.required
      ],

      backupDirectory: [
        '',
        Validators.required
      ],

      retentionDays: [
        null,
        [
          Validators.required,
          Validators.min(1)
        ]
      ]

    });
  }

  private loadConfiguration(): void {

    this.loading = true;

    this.backupConfigurationService.getConfiguration()
      .subscribe({

        next: (response) => {

          this.configuration = response;

          this.form.patchValue({

            enabled: response.enabled,
            intervalHours: response.intervalHours,
            dailyTime: response.dailyTime,
            backupDirectory: response.backupDirectory,
            retentionDays: response.retentionDays

          });

          this.hasError = false;
          this.loading = false;
        },

        error: (error) => {

          console.error(error);

          this.hasError = true;
          this.loading = false;

          this.alertService.error(
            error.error?.message ??
            'No fue posible cargar la configuración de backups.'
          );
        }

      });
  }

  public updateConfiguration(): void {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;
    }

    if (!this.configuration) {
      return;
    }

    this.saving = true;

    const request: UpdateBackupConfigurationRequest = {

      id: this.configuration.id,

      enabled: this.form.value.enabled,

      intervalHours: this.form.value.intervalHours,

      dailyTime: this.form.value.dailyTime,

      backupDirectory: this.form.value.backupDirectory,

      retentionDays: this.form.value.retentionDays

    };

    this.backupConfigurationService
      .updateConfiguration(
        this.configuration.id,
        request
      )
      .subscribe({

        next: (response) => {

          this.saving = false;

          this.alertService.success(
            response.message,
            'Configuración actualizada'
          );

          this.loadConfiguration();
        },

        error: (error) => {

          console.error(error);

          this.saving = false;

          this.alertService.error(
            error.error?.message ??
            'No fue posible actualizar la configuración.'
          );
        }

      });
  }

  cancel(): void {
  this.router.navigate(['/settings']);
}

}
