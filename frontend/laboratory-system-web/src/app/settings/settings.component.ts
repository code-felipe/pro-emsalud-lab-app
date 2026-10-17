import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../auth/components/auth.service';

@Component({
  selector: 'app-settings',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink
  ],

  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent {

  title: string = 'Ajustes';
  
  private readonly authService = inject(AuthService);
  protected readonly isAdmin = this.authService.isAdmin;

}
