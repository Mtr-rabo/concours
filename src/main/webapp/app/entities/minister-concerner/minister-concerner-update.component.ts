import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMinisterConcerner, MinisterConcerner } from 'app/shared/model/minister-concerner.model';
import { MinisterConcernerService } from './minister-concerner.service';

@Component({
  selector: 'jhi-minister-concerner-update',
  templateUrl: './minister-concerner-update.component.html',
})
export class MinisterConcernerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [],
    domaine: [],
    description: [],
  });

  constructor(
    protected ministerConcernerService: MinisterConcernerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ministerConcerner }) => {
      this.updateForm(ministerConcerner);
    });
  }

  updateForm(ministerConcerner: IMinisterConcerner): void {
    this.editForm.patchValue({
      id: ministerConcerner.id,
      nom: ministerConcerner.nom,
      domaine: ministerConcerner.domaine,
      description: ministerConcerner.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ministerConcerner = this.createFromForm();
    if (ministerConcerner.id !== undefined) {
      this.subscribeToSaveResponse(this.ministerConcernerService.update(ministerConcerner));
    } else {
      this.subscribeToSaveResponse(this.ministerConcernerService.create(ministerConcerner));
    }
  }

  private createFromForm(): IMinisterConcerner {
    return {
      ...new MinisterConcerner(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      domaine: this.editForm.get(['domaine'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMinisterConcerner>>): void {
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
