import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDocumentAFournir, DocumentAFournir } from 'app/shared/model/document-a-fournir.model';
import { DocumentAFournirService } from './document-a-fournir.service';

@Component({
  selector: 'jhi-document-a-fournir-update',
  templateUrl: './document-a-fournir-update.component.html',
})
export class DocumentAFournirUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
  });

  constructor(
    protected documentAFournirService: DocumentAFournirService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentAFournir }) => {
      this.updateForm(documentAFournir);
    });
  }

  updateForm(documentAFournir: IDocumentAFournir): void {
    this.editForm.patchValue({
      id: documentAFournir.id,
      code: documentAFournir.code,
      libelle: documentAFournir.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentAFournir = this.createFromForm();
    if (documentAFournir.id !== undefined) {
      this.subscribeToSaveResponse(this.documentAFournirService.update(documentAFournir));
    } else {
      this.subscribeToSaveResponse(this.documentAFournirService.create(documentAFournir));
    }
  }

  private createFromForm(): IDocumentAFournir {
    return {
      ...new DocumentAFournir(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentAFournir>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
