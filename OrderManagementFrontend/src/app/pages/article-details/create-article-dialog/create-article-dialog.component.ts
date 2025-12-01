import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { ArticleFormFieldsComponent } from '@app/components/article-form-fields/article-form-fields.component';
import { ArticleControllerService, ArticleUpdateDTO } from '@generated/ordermanagement';

@Component({
  selector: 'app-create-article-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    TranslateModule,
    ArticleFormFieldsComponent
  ],
  templateUrl: './create-article-dialog.component.html',
  styleUrl: './create-article-dialog.component.scss'
})
export class CreateArticleDialogComponent {
  form: FormGroup;
  saving = false;
  error?: string;

  types = ['PHYSICAL_PRODUCT', 'DIGITAL_PRODUCT'];

  constructor(
    private fb: FormBuilder,
    private api: ArticleControllerService,
    private dialogRef: MatDialogRef<CreateArticleDialogComponent>
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      articleNumber: ['', Validators.required],
      type: ['PHYSICAL_PRODUCT', Validators.required],
      description: ['']
    });
  }

  cancel() {
    if (this.saving) return;
    this.dialogRef.close();
  }

  submit() {
    if (this.form.invalid || this.saving) return;

    this.saving = true;
    this.error = undefined;

    const dto: ArticleUpdateDTO = this.form.getRawValue();

    this.api.createArticleUsingPOST({ articleUpdateDTO: dto }).subscribe({
      next: () => {
        this.dialogRef.close(true);
      },
      error: () => {
        this.saving = false;
        this.error = 'articles.create.error';
      }
    });
  }
}