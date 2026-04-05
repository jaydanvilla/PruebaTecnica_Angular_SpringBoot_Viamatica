import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  error: string = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastService: ToastService
  ) {}

  onSubmit(): void {
    if (this.authService.login(this.username, this.password)) {
      this.toastService.success(`✅ ¡Bienvenido ${this.username}! Has iniciado sesión correctamente.`);
      this.router.navigate(['/dashboard']);
    } else {
      this.error = '❌ Credenciales incorrectas. Use admin/admin';
      this.toastService.error('❌ Credenciales incorrectas. Intenta nuevamente.');
    }
  }
}