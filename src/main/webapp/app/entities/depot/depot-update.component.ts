import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDepot, Depot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';
import { IOffre } from 'app/shared/model/offre.model';
import { OffreService } from 'app/entities/offre/offre.service';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat/candidat.service';

type SelectableEntity = IOffre | ICandidat;

@Component({
  selector: 'jhi-depot-update',
  templateUrl: './depot-update.component.html',
})
export class DepotUpdateComponent implements OnInit {
  isSaving = false;
  offres: IOffre[] = [];
  candidats: ICandidat[] = [];

  editForm = this.fb.group({
    id: [],
    dateDepot: [],
    isArchive: [],
    isAcepte: [],
    offre: [],
    candidat: [],
  });

  constructor(
    protected depotService: DepotService,
    protected offreService: OffreService,
    protected candidatService: CandidatService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ depot }) => {
      if (!depot.id) {
        const today = moment().startOf('day');
        depot.dateDepot = today;
      }

      this.updateForm(depot);

      this.offreService.query().subscribe((res: HttpResponse<IOffre[]>) => (this.offres = res.body || []));

      this.candidatService.query().subscribe((res: HttpResponse<ICandidat[]>) => (this.candidats = res.body || []));
    });
  }

  updateForm(depot: IDepot): void {
    this.editForm.patchValue({
      id: depot.id,
      dateDepot: depot.dateDepot ? depot.dateDepot.format(DATE_TIME_FORMAT) : null,
      isArchive: depot.isArchive,
      isAcepte: depot.isAcepte,
      offre: depot.offre,
      candidat: depot.candidat,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const depot = this.createFromForm();
    if (depot.id !== undefined) {
      this.subscribeToSaveResponse(this.depotService.update(depot));
    } else {
      this.subscribeToSaveResponse(this.depotService.create(depot));
    }
  }

  private createFromForm(): IDepot {
    return {
      ...new Depot(),
      id: this.editForm.get(['id'])!.value,
      dateDepot: this.editForm.get(['dateDepot'])!.value ? moment(this.editForm.get(['dateDepot'])!.value, DATE_TIME_FORMAT) : undefined,
      isArchive: this.editForm.get(['isArchive'])!.value,
      isAcepte: this.editForm.get(['isAcepte'])!.value,
      offre: this.editForm.get(['offre'])!.value,
      candidat: this.editForm.get(['candidat'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepot>>): void {
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
