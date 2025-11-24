import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const headers = auth.authHeader();
  const usesApi = req.url.startsWith(auth.getApiBase());
  const withAuth = usesApi && headers["Authorization"] ? req.clone({ setHeaders: headers }) : req;
  return next(withAuth).pipe(
    catchError((err: unknown) => {
      if (err instanceof HttpErrorResponse && err.status === 401) {
        auth.clear();
        const returnUrl = typeof window !== 'undefined' ? window.location.pathname + window.location.search : '/';
        router.navigate(['/login'], { queryParams: { returnUrl } });
      }
      return throwError(() => err);
    })
  );
};
