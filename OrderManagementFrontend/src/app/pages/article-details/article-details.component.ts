import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ArticleControllerService } from '../../generated/ordermanagement/api/articleController.service';
import { ArticleDTO } from '../../generated/ordermanagement/model/models';
import {TranslatePipe} from "@ngx-translate/core";
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EditArticleDialogComponent } from './edit-article-dialog/edit-article-dialog.component';
import {AuthService} from "../../auth.service";

@Component({
  selector: 'app-article-details',
  standalone: true,
  imports: [CommonModule, RouterLink, TranslatePipe, MatButtonModule, MatDialogModule],
  templateUrl: './article-details.component.html',
  styleUrl: './article-details.component.scss'
})
export class ArticleDetailsComponent {
  article: ArticleDTO | undefined;
  loading = true;
  error: string | undefined;
  private articleIdNum: number | undefined;
  private readonly authService = inject(AuthService);

  constructor(private route: ActivatedRoute, private api: ArticleControllerService, private dialog: MatDialog) {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = idParam ? Number(idParam) : undefined;
    if (id === undefined || Number.isNaN(id)) {
      this.error = 'Invalid article id';
      this.loading = false;
      return;
    }
    this.articleIdNum = id;
    this.api.getArticleById({ articleId: id }).subscribe({
      next: (data) => {
        this.article = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load article';
        this.loading = false;
      }
    });
  }

  openEdit() {
    if (!this.article || this.articleIdNum === undefined) return;
    const ref = this.dialog.open(EditArticleDialogComponent, {
      width: '520px',
      data: { id: this.articleIdNum, article: this.article }
    });
    ref.afterClosed().subscribe(result => {
      if (result && result.updated) {
        this.article = result.article ?? this.article;
      }
    });
  }

  canEdit() {
    return this.authService.isAdmin();
  }
}
