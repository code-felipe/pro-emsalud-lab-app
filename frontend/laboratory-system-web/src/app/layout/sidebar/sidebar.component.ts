import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../auth/components/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  
  private readonly authService = inject(AuthService);
  protected readonly isAdmin = this.authService.isAdmin;
  private readonly router = inject(Router);

   onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }


  
}