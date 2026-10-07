import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { BackupService } from '../components/backup.service';
import { Backup } from '../components/backup.model';
import { AlertService } from '../../utils/alert/alert.service';

@Component({
  selector: 'app-backup',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink
  ],

  templateUrl: './backup.component.html',
  styleUrl: './backup.component.css'
})
export class BackupComponent implements OnInit {

  backup: Backup | null = null;

  backups: Backup[] = [];

  loading = true;
  saving = false;

  title = 'Respaldo de base de datos';

  constructor(
    private backupService: BackupService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadBackups();
  }

  private loadBackups(): void {

    this.loading = true;

    this.backupService.getAllbackups().subscribe({

      next: (response) => {

        this.backups = response;

        this.setLatestBackup();

        this.loading = false;
      },

      error: (error) => {

        this.loading = false;

        console.error(error);

        this.alertService.error(
          'No fue posible obtener la información de los backups.'
        );
      }

    });
  }

  private setLatestBackup(): void {

    if (this.backups.length === 0) {
      this.backup = null;
      return;
    }

    this.backup = this.backups.reduce((latest, current) => {

      return new Date(current.createdAt) > new Date(latest.createdAt)
        ? current
        : latest;

    });
  }

  createBackup(): void {

    if (this.saving) {
      return;
    }

    this.saving = true;

    this.backupService.createBackup().subscribe({

      next: (response) => {

        this.backup = response;

        this.backups = [
          response,
          ...this.backups
        ];

        this.setLatestBackup();

        this.saving = false;

        this.alertService.success(
          'El backup fue creado correctamente.',
          'Backup creado'
        );

      },

      error: (error) => {

        this.saving = false;

        console.error(error);

        this.alertService.error(
          error.error?.message ??
          'No fue posible crear el backup.'
        );
      }

    });
  }

  formatSize(size: number | null): string {

    if (size === null || size === undefined) {
      return '-';
    }

    if (size < 1024) {
      return `${size} B`;
    }

    if (size < 1024 * 1024) {
      return `${(size / 1024).toFixed(2)} KB`;
    }

    return `${(size / (1024 * 1024)).toFixed(2)} MB`;
  }
}
