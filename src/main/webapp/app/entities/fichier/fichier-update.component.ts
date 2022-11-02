import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFichier, Fichier } from 'app/shared/model/fichier.model';
import { FichierService } from './fichier.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDepot } from 'app/shared/model/depot.model';
import { DepotService } from 'app/entities/depot/depot.service';

@Component({
  selector: 'jhi-fichier-update',
  templateUrl: './fichier-update.component.html',
})
export class FichierUpdateComponent implements OnInit {
  isSaving = false;
  depots: IDepot[] = [];

  editForm = this.fb.group({
    id: [],
    nomFichier: [],
    fich: [],
    fichContentType: [],
    depot: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected fichierService: FichierService,
    protected depotService: DepotService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fichier }) => {
      this.updateForm(fichier);

      this.depotService.query().subscribe((res: HttpResponse<IDepot[]>) => (this.depots = res.body || []));
    });
  }

  updateForm(fichier: IFichier): void {
    this.editForm.patchValue({
      id: fichier.id,
      nomFichier: fichier.nomFichier,
      fich: fichier.fich,
      fichContentType: fichier.fichContentType,
      depot: fichier.depot,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('concoursFonctionPubliqueApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fichier = this.createFromForm();
    if (fichier.id !== undefined) {
      this.subscribeToSaveResponse(this.fichierService.update(fichier));
    } else {
      this.subscribeToSaveResponse(this.fichierService.create(fichier));
    }
  }

  private createFromForm(): IFichier {
    return {
      ...new Fichier(),
      id: this.editForm.get(['id'])!.value,
      nomFichier: this.editForm.get(['nomFichier'])!.value,
      fichContentType: this.editForm.get(['fichContentType'])!.value,
      fich: this.editForm.get(['fich'])!.value,
      depot: this.editForm.get(['depot'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFichier>>): void {
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

  trackById(index: number, item: IDepot): any {
    return item.id;
  }
}
