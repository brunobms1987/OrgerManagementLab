import {Component, inject} from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from './auth.service';
import {TranslateService} from "@ngx-translate/core";
import translationEN from "./assets/i18n/en.json";

@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'OrderManagementFrontend';
  private translate = inject(TranslateService);

  constructor(private auth: AuthService, private router: Router) {
    this.translate.addLangs(['en']);
    this.translate.setFallbackLang('en');
    this.translate.setTranslation('en', translationEN);
    this.translate.use('en');
  }

  get loggedIn(): boolean {
    return this.auth.isLoggedIn();
  }

  logout() {
    this.auth.clear();
    this.router.navigate(['/login']);
  }
}
