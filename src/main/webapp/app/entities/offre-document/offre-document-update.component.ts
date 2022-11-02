import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOffreDocument, OffreDocument } from 'app/shared/model/offre-document.model';
import { OffreDocumentService } from './offre-document.service';
import { IOffre } from 'app/shared/model/offre.model';
import { OffreService } from 'app/entities/offre/offre.service';
import { IDocumentAFournir } from 'app/shared/model/document-a-fournir.model';
import { DocumentAFournirService } from 'app/entities/document-a-fournir/document-a-fournir.service';

type SelectableEntity = IOffre | IDocumentAFournir;

@Component({
  selector: 'jhi-offre-document-update',
  templateUrl: './offre-document-update.component.html',
})
export class OffreDocumentUpdateComponent implements OnInit {
  isSaving = false;
  offres: IOffre[] = [];
  documentafournirs: IDocumentAFournir[] = [];

  editForm = this.fb.group({
    id: [],
    offre: [],
    documentAFournir: [],
  });

  constructor(
    protected offreDocumentService: OffreDocumentService,
    protected offreService: OffreService,
    protected documentAFournirService: DocumentAFournirService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offreDocument }) => {
      this.updateForm(offreDocument);

      this.offreService.query().subscribe((res: HttpResponse<IOffre[]>) => (this.offres = res.body || []));

      this.documentAFournirService.query().subscribe((res: HttpResponse<IDocumentAFournir[]>) => (this.documentafournirs = res.body || []));
    });
  }

  updateForm(offreDocument: IOffreDocument): void {
    this.editForm.patchValue({
      id: offreDocument.id,
      offre: offreDocument.offre,
      documentAFournir: offreDocument.documentAFournir,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offreDocument = this.createFromForm();
    if (offreDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.offreDocumentService.update(offreDocument));
    } else {
      this.subscribeToSaveResponse(this.offreDocumentService.create(offreDocument));
    }
  }

  private createFromForm(): IOffreDocument {
    return {
      ...new OffreDocument(),
      id: this.editForm.get(['id'])!.value,
      offre: this.editForm.get(['offre'])!.value,
      documentAFournir: this.editForm.get(['documentAFournir'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffreDocument>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
