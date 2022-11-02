import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICandidat, Candidat } from 'app/shared/model/candidat.model';
import { CandidatService } from './candidat.service';

@Component({
  selector: 'jhi-candidat-update',
  templateUrl: './candidat-update.component.html',
})
export class CandidatUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    telephone: [],
    dateNaissance: [],
    lieuNaissance: [],
    age: [],
    adresse: [],
  });

  constructor(protected candidatService: CandidatService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidat }) => {
      if (!candidat.id) {
        const today = moment().startOf('day');
        candidat.dateNaissance = today;
      }

      this.updateForm(candidat);
    });
  }

  updateForm(candidat: ICandidat): void {
    this.editForm.patchValue({
      id: candidat.id,
      nom: candidat.nom,
      prenom: candidat.prenom,
      telephone: candidat.telephone,
      dateNaissance: candidat.dateNaissance ? candidat.dateNaissance.format(DATE_TIME_FORMAT) : null,
      lieuNaissance: candidat.lieuNaissance,
      age: candidat.age,
      adresse: candidat.adresse,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidat = this.createFromForm();
    if (candidat.id !== undefined) {
      this.subscribeToSaveResponse(this.candidatService.update(candidat));
    } else {
      this.subscribeToSaveResponse(this.candidatService.create(candidat));
    }
  }

  private createFromForm(): ICandidat {
    return {
      ...new Candidat(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value
        ? moment(this.editForm.get(['dateNaissance'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lieuNaissance: this.editForm.get(['lieuNaissance'])!.value,
      age: this.editForm.get(['age'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidat>>): void {
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
