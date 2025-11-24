import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-article-form-fields',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    TranslatePipe
  ],
  templateUrl: './article-form-fields.component.html',
  styleUrl: './article-form-fields.component.scss'
})
export class ArticleFormFieldsComponent {
  @Input({ required: true }) form!: FormGroup;
  @Input({ required: true }) types: string[] = [];
  @Input() error: string | undefined;
}
