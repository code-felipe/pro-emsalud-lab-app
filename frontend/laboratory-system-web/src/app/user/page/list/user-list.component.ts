
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AlertService } from '../../../utils/alert/alert.service';
import { UserResponse } from '../../components/user.model';
import { UserService } from '../../components/user.service';


@Component({
  selector: 'app-user-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    // ModalComponent,
    
  ],

  templateUrl: './user-list.component.html',
  
})
export class UserComponentList implements OnInit {

  users: UserResponse[] = [];

  hasError = false;

  title: string = 'Usuarios'

  uploadingUserId: number | null = null;

  constructor(
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private alertService: AlertService
  ) {}


  ngOnInit(): void {

      this.loadUsers();
  }


public delete(user: UserResponse): void {

  this.alertService.confirmDelete(user.username)
    .then(confirmed => {

      if (!confirmed) {
        return;
      }

      this.userService.deleteUser(user.id).subscribe({

        next: (response) => {

        this.users = this.users.filter(
            u => u.id !== user.id
        );

        this.alertService.success(
          response.message,
          'Usuario eliminado'
          );
        },

        error: (error) => {

          console.error(error);

          this.alertService.error(
            error.error?.message ?? 'No fue posible eliminar la ficha del Usuario.'
          );
        }

      });
    });
}

private loadUsers(): void {
    
    this.userService.getUsers()
      .subscribe({

        next: (response) => {

          this.users = response;
          
          this.hasError = false;
            console.log(this.users)

        },

        error: (err) => {

          this.hasError = true;

          console.error(err);

        },

      });
  }

//   /** Selección y subida de la imagen */
// onPictureSelected(event: Event, user: UserResponse): void {

//   const input = event.target as HTMLInputElement;
//   const file = input.files?.[0];

//   // Permitir seleccionar el mismo archivo nuevamente
//   input.value = '';

//   if (!file) {
//     return;
//   }

//   if (!file.type.startsWith('image/')) {
//     this.alertService.error('Selecciona un archivo de imagen válido.');
//     return;
//   }

//   this.uploadingUserId = user.id;

//   this.userService.uploadPicture(user.id, file).subscribe({

//     next: (response) => {

//       // Actualizar el usuario en la lista
//       this.users = this.users.map(u =>
//         u.id === user.id
//           ? { ...u, ...response.user }
//           : u
//       );

//       this.uploadingUserId = null;

//       this.alertService.success(
//         response.message,
//         'Imagen actualizada'
//       );

//     },

//     error: (error) => {

//       console.error(error);

//       this.uploadingUserId = null;

//       this.alertService.error(
//         error.error?.message
//           ?? 'No fue posible subir la imagen del usuario.'
//       );

//     }

//   });
// }

// /** URL pública del endpoint que devuelve la imagen */
// getPictureUrl(fileName: string): string {
//   return this.userService.getPictureUrl(fileName);
// }

}




