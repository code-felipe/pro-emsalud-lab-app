
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginRequest } from '../../auth.model';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-style.component.css'
})
export class LoginComponent {

  public username = '';
  public password = '';

  public errorMessage = '';
  public loading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  public onSubmit(): void {
    this.errorMessage = '';

    if (!this.username.trim() || !this.password) {
      this.errorMessage = 'Ingresa tu usuario y contraseña.';
      return;
    }

    this.loading = true;

    const credentials: LoginRequest = {
      username: this.username.trim(),
      password: this.password
    };

    this.authService.login(credentials).subscribe({
      next: (response) => {
        this.loading = false;

        // El AuthService ya guardó el token.
        console.log(response.mensaje);

        this.router.navigate(['/']);
      },
      error: (error: HttpErrorResponse) => {
        this.loading = false;

        if (error.status === 401) {
          this.errorMessage = 'Usuario o contraseña incorrectos.';
        } else if (error.status === 0) {
          this.errorMessage =
            'No se pudo conectar con el servidor. Intenta nuevamente.';
        } else {
          this.errorMessage =
            error.error?.mensaje ??
            error.error?.message ??
            'Ocurrió un error al iniciar sesión.';
        }
      }
    });
  }
}
