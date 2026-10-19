import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { UserResponse, UploadPictureResponse } from '../../components/user.model';
import { UserService } from '../../components/user.service';
import Swal from 'sweetalert2';
import { AuthService } from '../../../auth/components/auth.service';

@Component({
  selector: 'app-user-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.css'
})
export class UserDetailComponent implements OnInit {

  private readonly userService = inject(UserService);
  private readonly activateRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);

  protected readonly isAdmin = this.authService.isAdmin;

  user: UserResponse | null = null;
  pictureSelected: File | null = null;

  // ¿Es el perfil del usuario logueado?
  protected get isSelf(): boolean {
    return !!this.user && this.authService.currentUser()?.id === this.user.id;
  }

  // Admin sobre cualquiera, o el usuario sobre sí mismo
  protected get canManage(): boolean {
    return this.isAdmin() || this.isSelf;
  }

  ngOnInit(): void {
    this.activateRoute.paramMap.subscribe(params => {
      const id = Number(params.get('id'));

      if (id > 0) {
        this.userService.getUser(id).subscribe({
          next: (user: UserResponse) => {
            this.user = user;
          },
          error: (error) => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: error.error?.message ||
                'No se pudo cargar la información del usuario.'
            });
          }
        });
      }
    });
  }

  public selectPicture(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      const file = input.files[0];

      if (!file.type.startsWith('image/')) {
        this.pictureSelected = null;
        input.value = '';

        Swal.fire({
          icon: 'warning',
          title: 'Tipo de archivo',
          text: 'El archivo debe ser una imagen.'
        });
        return;
      }

      this.pictureSelected = file;
    }
  }

  public uploadPicture(): void {
    if (!this.user || !this.canManage) {
      return;
    }

    if (!this.pictureSelected) {
      Swal.fire({
        icon: 'warning',
        title: 'Imagen requerida',
        text: 'Debes seleccionar una imagen antes de continuar.'
      });
      return;
    }

    this.userService.uploadPicture(this.pictureSelected, this.user.id).subscribe({
      next: (response: UploadPictureResponse) => {
        this.user = response.user;
        this.pictureSelected = null;

        // Si cambió su propia foto, refresca el header
        if (this.isSelf) {
          this.authService.loadCurrentUser().subscribe({
            error: err => console.error('No se pudo refrescar el usuario', err)
          });
        }

        Swal.fire({
          icon: 'success',
          title: '¡Éxito!',
          text: response.message
        });
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error al subir la imagen',
          text: error.error?.message || 'No se pudo subir la imagen.'
        });
      }
    });
  }
}