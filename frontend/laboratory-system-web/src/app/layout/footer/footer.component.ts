import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { environment } from './components/model';
import { BackupService } from '../../backup/components/backup.service';
import { Backup } from '../../backup/components/backup.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent  implements OnInit {

  protected readonly currentYear = new Date().getFullYear();

  latestSuccessfulBackup: Backup | null = null;

   hasError = false;

  constructor(
    private backupService: BackupService
  ){}

  ngOnInit(): void {
    this.loadLatestSuccessfulBackup();
  }

  private loadLatestSuccessfulBackup(): void{
    
    this.backupService.getLatestBackup().subscribe({

      next: (response) => {

          this.latestSuccessfulBackup = response;

          this.hasError = false;

        },

        error: (err) => {

          this.hasError = true;

          console.error(err);

        },
    })
  }


}