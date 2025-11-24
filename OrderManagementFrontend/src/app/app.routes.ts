import { Routes } from '@angular/router';
import { ArticlesListComponent } from './pages/articles-list/articles-list.component';
import { ArticleDetailsComponent } from './pages/article-details/article-details.component';
import { LoginComponent } from './pages/login/login.component';
import { authGuard } from './auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: ArticlesListComponent, canActivate: [authGuard] },
  { path: 'articles/:id', component: ArticleDetailsComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: '' }
];
