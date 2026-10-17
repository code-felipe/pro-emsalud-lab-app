import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { Injectable, signal, computed } from "@angular/core";
import { UserResponse } from "../../user/components/user.model";
import { LoginRequest, LoginResponse } from "../auth.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly url = 'http://localhost:8080/api/login';
  private readonly meUrl = 'http://localhost:8080/api/users/me';
  private readonly tokenKey = 'token';
  private readonly userKey = 'user';

  private readonly _user = signal<UserResponse | null>(this.readStoredUser());
  public readonly currentUser = this._user.asReadonly();

  public readonly isAdmin = computed(() =>
    this._user()?.roles?.some(r => r.authority === 'ROLE_ADMIN') ?? false
  );

   public readonly isUser = computed(() =>
    this._user()?.roles?.some(r => r.authority === 'ROLE_USER') ?? false
  );

  constructor(private http: HttpClient) {}

  public login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.url, credentials).pipe(
      tap(response => {
        sessionStorage.setItem(this.tokenKey, response.token);
        this.saveUser(response.user);
      })
    );
  }

  public loadCurrentUser(): Observable<UserResponse> {
    return this.http.get<UserResponse>(this.meUrl).pipe(
      tap(user => this.saveUser(user))
    );
  }

  public getToken(): string | null {
    return sessionStorage.getItem(this.tokenKey);
  }

  public logout(): void {
    sessionStorage.removeItem(this.tokenKey);
    sessionStorage.removeItem(this.userKey);
    this._user.set(null);
  }

  public getCurrentUsername(): string | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.sub ?? null;
    } catch {
      return null;
    }
  }

  public isAuthenticated(): boolean {
    return !!this.getToken();
  }

  private saveUser(user: UserResponse): void {
    sessionStorage.setItem(this.userKey, JSON.stringify(user));
    this._user.set(user);
  }

  private readStoredUser(): UserResponse | null {
    const raw = sessionStorage.getItem(this.userKey);
    if (!raw) return null;

    try {
      return JSON.parse(raw) as UserResponse;
    } catch {
      return null;
    }
  }
}