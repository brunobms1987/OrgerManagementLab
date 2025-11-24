import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { TranslatePipe } from '@ngx-translate/core';
import { ArticleFormFieldsComponent } from '@app/components/article-form-fields/article-form-fields.component';
import {ArticleControllerService, ArticleDTO, ArticleUpdateDTO} from "@generated/ordermanagement";

export interface EditArticleDialogData {
  id: number;
  article: ArticleDTO;
}

@Component({
  selector: 'app-edit-article-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    TranslatePipe,
    ArticleFormFieldsComponent
  ],
  templateUrl: './edit-article-dialog.component.html',
  styleUrl: './edit-article-dialog.component.scss'
})
export class EditArticleDialogComponent {
  form: FormGroup;
  saving = false;
  error: string | undefined;

  types = ['PHYSICAL_PRODUCT', 'DIGITAL_PRODUCT'];

  constructor(
    private fb: FormBuilder,
    private api: ArticleControllerService,
    private ref: MatDialogRef<EditArticleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EditArticleDialogData
  ) {
    this.form = this.fb.group({
      name: [data.article?.name ?? '', [Validators.required]],
      articleNumber: [data.article?.articleNumber ?? '', [Validators.required]],
      type: [data.article?.type ?? 'PHYSICAL_PRODUCT', [Validators.required]],
      description: [data.article?.description ?? '']
    });
  }

  cancel() {
    if (this.saving) return;
    this.ref.close({ updated: false });
  }

  submit() {
    if (this.saving || this.form.invalid) return;
    this.saving = true;
    this.error = undefined;
    const update: ArticleUpdateDTO = {
      name: this.form.value.name,
      articleNumber: this.form.value.articleNumber,
      type: this.form.value.type,
      description: this.form.value.description
    };
    this.api.updateArticle({ articleId: this.data.id, articleUpdateDTO: update }).subscribe({
      next: (updated: ArticleDTO) => {
        this.saving = false;
        this.ref.close({ updated: true, article: updated });
      },
      error: () => {
        this.saving = false;
        this.error = 'articles.details.updateError';
      }
    });
  }
}
