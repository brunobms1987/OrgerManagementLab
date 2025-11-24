import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { AuthService } from '../../auth.service';
import { ArticleControllerService } from '../../generated/ordermanagement/api/articleController.service';
import {TranslatePipe} from "@ngx-translate/core";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TranslatePipe],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  form: FormGroup;
  loading = false;
  error: string | undefined;
  returnUrl = '/';

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router, private route: ActivatedRoute, private api: ArticleControllerService) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    const q = this.route.snapshot.queryParamMap.get('returnUrl');
    if (q) this.returnUrl = q;
    if (this.auth.isLoggedIn()) this.router.navigateByUrl(this.returnUrl);
  }

  submit() {
    if (this.form.invalid || this.loading) return;
    this.loading = true;
    this.error = undefined;
    const u = this.form.value.username as string;
    const p = this.form.value.password as string;
    this.auth.setCredentials(u, p);
    this.api.listArticles({ page: 0, size: 1 }).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigateByUrl(this.returnUrl);
      },
      error: () => {
        this.loading = false;
        this.auth.clear();
        this.error = 'Invalid credentials';
      }
    });
  }
}
