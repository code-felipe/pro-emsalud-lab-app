import {
  Component,
  OnInit,
  OnDestroy,
  signal,
  computed,
  inject,
  HostListener,
  ElementRef,
} from '@angular/core';

import { DatePipe } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import {
  timer,
  merge,
  filter,
  switchMap,
  catchError,
  of,
  Subscription,
} from 'rxjs';

import { AuthService } from '../../auth/components/auth.service';
import { OrderService } from '../../order/components/order.service';
import { PendingOrderNotificationResponse } from '../../order/components/order.model';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, DatePipe],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit, OnDestroy {

 
  protected readonly now = signal(new Date());

  protected readonly pendingOrderNotifications =
    signal<PendingOrderNotificationResponse[]>([]);

  protected readonly isNotificationsOpen = signal(false);
  protected readonly isUserMenuOpen = signal(false);

  private readonly elementRef = inject(ElementRef);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly orderService = inject(OrderService);

  private readonly subscriptions = new Subscription();


  private readonly imagesUrl =
    'http://localhost:8080/api/users/uploads/img/';

  private readonly defaultPicture =
    'http://localhost:8080/images/no-user.png';

  protected readonly user = this.authService.currentUser;

  protected readonly username = computed(
    () => this.user()?.username ?? null
  );

  protected readonly pictureUrl = computed(() => {
    const picture = this.user()?.picture;

    return picture
      ? this.imagesUrl + picture
      : this.defaultPicture;
  });

  // =====================================================
  // LIFECYCLE
  // =====================================================

  ngOnInit(): void {
    // Reloj
    this.subscriptions.add(
      timer(0, 1000).subscribe(() => this.now.set(new Date()))
    );

    if (!this.authService.isAuthenticated()) {
      return;
    }

    this.authService.loadCurrentUser().subscribe({
      error: err => console.error('No se pudo cargar el usuario', err)
    });

    // Notificaciones: al iniciar, cada 30 s, al cambiar una orden
    // y al terminar cada navegación
    this.subscriptions.add(
      merge(
        timer(0, 30_000),
        this.orderService.ordersChanged$,
        this.router.events.pipe(filter(e => e instanceof NavigationEnd))
      )
        .pipe(
          switchMap(() =>
            this.orderService.getPendingOrderNotifications().pipe(
              catchError(error => {
                console.error('Error al obtener las notificaciones', error);
                return of(null);
              })
            )
          )
        )
        .subscribe(orders => {
          if (orders !== null) {
            this.pendingOrderNotifications.set(orders);
          }
        })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  // =====================================================
  // NOTIFICATIONS
  // =====================================================

  toggleNotifications(): void {
    this.isNotificationsOpen.update(value => !value);

    if (this.isNotificationsOpen()) {
      this.closeUserMenu();
    }
  }

  closeNotifications(): void {
    this.isNotificationsOpen.set(false);
  }

  // =====================================================
  // USER MENU
  // =====================================================

  toggleUserMenu(): void {
    this.isUserMenuOpen.update(value => !value);

    if (this.isUserMenuOpen()) {
      this.closeNotifications();
    }
  }

  closeUserMenu(): void {
    this.isUserMenuOpen.set(false);
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;

    if (img.src.endsWith('no-user.png')) return;

    img.src = this.defaultPicture;
  }

  onLogout(): void {
    this.authService.logout();
    this.closeUserMenu();
    this.closeNotifications();
    this.router.navigate(['/login']);
  }

  // =====================================================
  // CLICK OUTSIDE
  // =====================================================

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.closeUserMenu();
      this.closeNotifications();
    }
  }
}