import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { TranslateModule } from '@ngx-translate/core';

import { ArticleControllerService } from '@generated/ordermanagement/api/articleController.service';
import { ArticleDTO, PageArticleDTO } from '@generated/ordermanagement/model/models';
import { CreateArticleDialogComponent } from '../article-details/create-article-dialog/create-article-dialog.component';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-articles-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    TranslateModule,
    MatTableModule,
    MatButtonModule,
    MatDialogModule
  ],
  templateUrl: './articles-list.component.html',
  styleUrl: './articles-list.component.scss'
})
export class ArticlesListComponent {
  articles: ArticleDTO[] = [];
  loading = true;
  error: string | undefined;
  tableColumns = ['id', 'name', 'articleNumber', 'type'];

  constructor(
    private api: ArticleControllerService,
    private dialog: MatDialog,
    private authService: AuthService
  ) {
    this.loadArticles();
  }

  private loadArticles() {
    this.api.listArticles({ page: 0, size: 50 }).subscribe({
      next: (page: PageArticleDTO) => {
        this.articles = page.content ?? [];
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load articles';
        this.loading = false;
      }
    });
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(CreateArticleDialogComponent, {
      width: '560px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe((created: boolean) => {
      if (created) {
        this.loadArticles();
      }
    });
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
}