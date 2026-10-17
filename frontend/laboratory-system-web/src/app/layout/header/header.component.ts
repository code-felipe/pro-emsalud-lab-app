import {
  Component,
  OnInit,
  signal,
  computed,
  inject,
  HostListener,
  ElementRef
} from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../auth/components/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  private readonly elementRef = inject(ElementRef);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  private readonly imagesUrl = 'http://localhost:8080/api/users/uploads/img/';
  private readonly defaultPicture = 'assets/images/no-user.png';

  protected readonly isUserMenuOpen = signal(false);

  protected readonly user = this.authService.currentUser;
  protected readonly username = computed(() => this.user()?.username ?? null);
  protected readonly pictureUrl = computed(() => {
    const picture = this.user()?.picture;
    return picture ? this.imagesUrl + picture : this.defaultPicture;
  });

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.authService.loadCurrentUser().subscribe({
        error: err => console.error('No se pudo cargar el usuario', err)
      });
    }
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    if (img.src.endsWith('no-user.png')) return; // el fallback también falló, no insistas
    img.src = this.defaultPicture;
  }

  toggleUserMenu(): void {
    this.isUserMenuOpen.update(value => !value);
  }

  closeUserMenu(): void {
    this.isUserMenuOpen.set(false);
  }

  onLogout(): void {
    this.authService.logout();
    this.closeUserMenu();
    this.router.navigate(['/login']);
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.closeUserMenu();
    }
  }
}