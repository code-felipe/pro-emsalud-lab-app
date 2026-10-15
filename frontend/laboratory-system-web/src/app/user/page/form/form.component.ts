import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import {
  CreateUserRequest,
  UpdateUserRequest,
  UserResponse,
  RoleResponse
} from '../../components/user.model';

import { UserService } from '../../components/user.service';
import { AlertService } from '../../../utils/alert/alert.service';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './form.component.html',
})
export class UserFormComponent implements OnInit {

  public user: CreateUserRequest & { id?: number } = {
    username: '',
    password: '',
    enabled: true,
    roleIds: []
  };

  public roles: RoleResponse[] = [];

  public title = 'Nuevo Usuario';

  public apiError: any = null;

  public loading = false;

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
            enabled: response.enabled,
            roleIds: response.roles.map(role => role.id)
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

  // Seleccionar o deseleccionar un rol
  public toggleRole(roleId: number, checked: boolean): void {
    if (checked) {
      if (!this.user.roleIds.includes(roleId)) {
        this.user.roleIds = [...this.user.roleIds, roleId];
      }
    } else {
      this.user.roleIds = this.user.roleIds.filter(id => id !== roleId);
    }
  }

  public hasRole(roleId: number): boolean {
    return this.user.roleIds.includes(roleId);
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

    this.loading = true;

    const request: CreateUserRequest = {
      username: this.user.username.trim(),
      password: this.user.password,
      enabled: this.user.enabled,
      roleIds: [...this.user.roleIds]
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

    this.loading = true;

    const request: UpdateUserRequest = {
      id: this.user.id,
      username: this.user.username.trim(),
      enabled: this.user.enabled,
      roleIds: [...this.user.roleIds]
    };

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