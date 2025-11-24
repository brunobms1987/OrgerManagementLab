import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ArticleControllerService } from '@generated/ordermanagement/api/articleController.service';
import { ArticleDTO, PageArticleDTO } from '@generated/ordermanagement/model/models';
import {TranslatePipe} from "@ngx-translate/core";
import { MatTableModule } from "@angular/material/table";

@Component({
  selector: 'app-articles-list',
  standalone: true,
  imports: [CommonModule, RouterLink, TranslatePipe, MatTableModule],
  templateUrl: './articles-list.component.html',
  styleUrl: './articles-list.component.scss'
})
export class ArticlesListComponent {
  articles: ArticleDTO[] = [];
  loading = true;
  error: string | undefined;
  tableColumns: string[] = ['id', 'name', 'articleNumber', 'type'];

  constructor(private api: ArticleControllerService) {
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
}
