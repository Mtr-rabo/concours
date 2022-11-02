import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEpreuveAconcourir, EpreuveAconcourir } from 'app/shared/model/epreuve-aconcourir.model';
import { EpreuveAconcourirService } from './epreuve-aconcourir.service';
import { IOffre } from 'app/shared/model/offre.model';
import { OffreService } from 'app/entities/offre/offre.service';

@Component({
  selector: 'jhi-epreuve-aconcourir-update',
  templateUrl: './epreuve-aconcourir-update.component.html',
})
export class EpreuveAconcourirUpdateComponent implements OnInit {
  isSaving = false;
  offres: IOffre[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    duree: [],
    coefficiant: [],
    noteEleminatoire: [],
    offre: [],
  });

  constructor(
    protected epreuveAconcourirService: EpreuveAconcourirService,
    protected offreService: OffreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ epreuveAconcourir }) => {
      this.updateForm(epreuveAconcourir);

      this.offreService.query().subscribe((res: HttpResponse<IOffre[]>) => (this.offres = res.body || []));
    });
  }

  updateForm(epreuveAconcourir: IEpreuveAconcourir): void {
    this.editForm.patchValue({
      id: epreuveAconcourir.id,
      nom: epreuveAconcourir.nom,
      duree: epreuveAconcourir.duree,
      coefficiant: epreuveAconcourir.coefficiant,
      noteEleminatoire: epreuveAconcourir.noteEleminatoire,
      offre: epreuveAconcourir.offre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const epreuveAconcourir = this.createFromForm();
    if (epreuveAconcourir.id !== undefined) {
      this.subscribeToSaveResponse(this.epreuveAconcourirService.update(epreuveAconcourir));
    } else {
      this.subscribeToSaveResponse(this.epreuveAconcourirService.create(epreuveAconcourir));
    }
  }

  private createFromForm(): IEpreuveAconcourir {
    return {
      ...new EpreuveAconcourir(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      duree: this.editForm.get(['duree'])!.value,
      coefficiant: this.editForm.get(['coefficiant'])!.value,
      noteEleminatoire: this.editForm.get(['noteEleminatoire'])!.value,
      offre: this.editForm.get(['offre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEpreuveAconcourir>>): void {
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

  trackById(index: number, item: IOffre): any {
    return item.id;
  }
}
