import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { UserResponse } from '../../components/user.model';
import { UserService } from '../../components/user.service';
import Swal from 'sweetalert2';

interface UploadPictureResponse {
  message: string;
  user: UserResponse;
}

@Component({
  selector: 'app-user-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.css'
})
export class UserDetailComponent implements OnInit {

  user: UserResponse | null = null;

  pictureSelected: File | null = null;

  constructor(
    private userService: UserService,
    private activateRoute: ActivatedRoute
  ) {}

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
      this.pictureSelected = input.files[0];
    }
  }

  public uploadPicture(): void {
    if (!this.user) {
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

    this.userService
      .uploadPicture(this.pictureSelected, this.user.id)
      .subscribe({
        next: (response: UploadPictureResponse) => {
          this.user = response.user;
          this.pictureSelected = null;

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
            text: error.error?.message ||
              'No se pudo subir la imagen.'
          });
        }
      });
  }
}