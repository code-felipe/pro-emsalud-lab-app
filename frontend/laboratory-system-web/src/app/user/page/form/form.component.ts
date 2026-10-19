import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import {
  CreateUserRequest,
  UpdateUserRequest,
  UserResponse,
  RoleResponse,
  UserForm
} from '../../components/user.model';

import { UserService } from '../../components/user.service';
import { AlertService } from '../../../utils/alert/alert.service';
import { AuthService } from '../../../auth/components/auth.service';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './form.component.html',
})
export class UserFormComponent implements OnInit {

  public user: UserForm = {
    username: '',
    password: '',
    roleId: null
  };

  public roles: RoleResponse[] = [];

  public title = 'Nuevo Usuario';

  public apiError: any = null;

  public loading = false;
  
  private readonly authService = inject(AuthService);
  protected readonly isAdmin = this.authService.isAdmin;

  constructor(
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadRoles();
    this.loadUser();
  }

  public loadRoles(): void {
    this.userService.getRoles().subscribe({
      next: (response: RoleResponse[]) => {
        this.roles = response;
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error al cargar roles:', err);

        this.alertService.error(
          err.error?.message ?? 'No se pudieron cargar los roles',
          'Error'
        );
      }
    });
  }

  // Cargar usuario cuando se está editando
  public loadUser(): void {
    this.activatedRoute.params.subscribe(params => {
      const id = params['id'];

      if (!id) {
        return;
      }

      this.userService.getUser(+id).subscribe({
        next: (response: UserResponse) => {
          this.user = {
            id: response.id,
            username: response.username,
            password: '',
            roleId: response.role.id
          };

          this.title = 'Editar Usuario';
        },
        error: (err: HttpErrorResponse) => {
          this.alertService.error(
            err.error?.message ?? 'No se pudo cargar el usuario',
            'Error'
          );
        }
      });
    });
  }

  // Crear usuario
  public create(): void {
    this.apiError = null;

    if (!this.user.username.trim() || !this.user.password) {
      this.alertService.error(
        'El nombre de usuario y la contraseña son obligatorios',
        'Validación'
      );
      return;
    }

    if (this.user.roleId === null) {
      this.alertService.error('Selecciona un rol', 'Validación');
      return;
    }

    this.loading = true;

    const request: CreateUserRequest = {
      username: this.user.username.trim(),
      password: this.user.password,
      roleId: this.user.roleId
    };

    this.userService.createUser(request).subscribe({
      next: response => {
        this.loading = false;

        this.alertService.success(
          response.message ?? 'Usuario creado exitosamente',
          'Usuario'
        );

        this.router.navigate(['/users']);
      },
      error: (err: HttpErrorResponse) => {
        this.loading = false;
        this.apiError = err.error;

        this.alertService.error(
          err.error?.message ?? 'No se pudo crear el usuario',
          'Error'
        );
      }
    });
  }

  // Actualizar usuario
  public update(): void {
    this.apiError = null;

    if (!this.user.id) {
      this.alertService.error(
        'El ID es requerido para actualizar el usuario',
        'Error'
      );
      return;
    }

    if (!this.user.username.trim()) {
      this.alertService.error(
        'El nombre de usuario es obligatorio',
        'Validación'
      );
      return;
    }

    if (this.user.roleId === null) {
      this.alertService.error('Selecciona un rol', 'Validación');
      return;
    }

    this.loading = true;

    const request: UpdateUserRequest = {
      id: this.user.id,
      username: this.user.username.trim(),
      roleId: this.user.roleId
    };

    // Solo se envía si el usuario escribió una nueva; si no, el backend conserva la actual
    if (this.user.password) {
      request.password = this.user.password;
    }

    this.userService.updateUser(this.user.id, request).subscribe({
      next: response => {
        this.loading = false;

        this.alertService.success(
          response.message ?? 'Usuario actualizado exitosamente',
          'Usuario'
        );

        this.router.navigate(['/users']);
      },
      error: (err: HttpErrorResponse) => {
        this.loading = false;
        this.apiError = err.error;

        this.alertService.error(
          err.error?.message ?? 'No se pudo actualizar el usuario',
          'Error'
        );
      }
    });
  }
}