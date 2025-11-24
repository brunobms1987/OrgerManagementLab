import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private key = 'basicAuthToken';
  private baseUrl = 'http://localhost:8092/ordermanagement/api';

  get token(): string | null {
    return localStorage.getItem(this.key);
  }

  isLoggedIn(): boolean {
    return !!this.token;
  }

  setCredentials(username: string, password: string): void {
    const t = btoa(`${username}:${password}`);
    localStorage.setItem(this.key, `Basic ${t}`);
  }

  clear(): void {
    localStorage.removeItem(this.key);
  }

  authHeader(): { [k: string]: string } {
    const t = this.token;
    return t ? { Authorization: t } : {};
  }

  getApiBase(): string {
    return this.baseUrl;
  }

  isAdmin(): boolean {
    if (!this.token) return false;
    const decoded = atob(this.token.split(' ')[1])
    return decoded.includes('admin') ?? false;
  }
}
