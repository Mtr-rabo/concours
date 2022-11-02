import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOffre, Offre } from 'app/shared/model/offre.model';
import { OffreService } from './offre.service';
import { IMinisterConcerner } from 'app/shared/model/minister-concerner.model';
import { MinisterConcernerService } from 'app/entities/minister-concerner/minister-concerner.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie/categorie.service';
import { IOption } from 'app/shared/model/option.model';
import { OptionService } from 'app/entities/option/option.service';

type SelectableEntity = IMinisterConcerner | ICategorie | IOption;

@Component({
  selector: 'jhi-offre-update',
  templateUrl: './offre-update.component.html',
})
export class OffreUpdateComponent implements OnInit {
  isSaving = false;
  ministerconcerners: IMinisterConcerner[] = [];
  categories: ICategorie[] = [];
  options: IOption[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    nomPoste: [],
    descrip: [],
    dateOuverture: [],
    dateCloture: [],
    isArchive: [],
    dateConcours: [],
    ageLimite: [],
    ministerConcerner: [],
    categrie: [],
    option: [],
  });

  constructor(
    protected offreService: OffreService,
    protected ministerConcernerService: MinisterConcernerService,
    protected categorieService: CategorieService,
    protected optionService: OptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offre }) => {
      if (!offre.id) {
        const today = moment().startOf('day');
        offre.dateOuverture = today;
        offre.dateCloture = today;
        offre.dateConcours = today;
      }

      this.updateForm(offre);

      this.ministerConcernerService
        .query()
        .subscribe((res: HttpResponse<IMinisterConcerner[]>) => (this.ministerconcerners = res.body || []));

      this.categorieService.query().subscribe((res: HttpResponse<ICategorie[]>) => (this.categories = res.body || []));

      this.optionService.query().subscribe((res: HttpResponse<IOption[]>) => (this.options = res.body || []));
    });
  }

  updateForm(offre: IOffre): void {
    this.editForm.patchValue({
      id: offre.id,
      code: offre.code,
      nomPoste: offre.nomPoste,
      descrip: offre.descrip,
      dateOuverture: offre.dateOuverture ? offre.dateOuverture.format(DATE_TIME_FORMAT) : null,
      dateCloture: offre.dateCloture ? offre.dateCloture.format(DATE_TIME_FORMAT) : null,
      isArchive: offre.isArchive,
      dateConcours: offre.dateConcours ? offre.dateConcours.format(DATE_TIME_FORMAT) : null,
      ageLimite: offre.ageLimite,
      ministerConcerner: offre.ministerConcerner,
      categrie: offre.categrie,
      option: offre.option,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offre = this.createFromForm();
    if (offre.id !== undefined) {
      this.subscribeToSaveResponse(this.offreService.update(offre));
    } else {
      this.subscribeToSaveResponse(this.offreService.create(offre));
    }
  }

  private createFromForm(): IOffre {
    return {
      ...new Offre(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      nomPoste: this.editForm.get(['nomPoste'])!.value,
      descrip: this.editForm.get(['descrip'])!.value,
      dateOuverture: this.editForm.get(['dateOuverture'])!.value
        ? moment(this.editForm.get(['dateOuverture'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateCloture: this.editForm.get(['dateCloture'])!.value
        ? moment(this.editForm.get(['dateCloture'])!.value, DATE_TIME_FORMAT)
        : undefined,
      isArchive: this.editForm.get(['isArchive'])!.value,
      dateConcours: this.editForm.get(['dateConcours'])!.value
        ? moment(this.editForm.get(['dateConcours'])!.value, DATE_TIME_FORMAT)
        : undefined,
      ageLimite: this.editForm.get(['ageLimite'])!.value,
      ministerConcerner: this.editForm.get(['ministerConcerner'])!.value,
      categrie: this.editForm.get(['categrie'])!.value,
      option: this.editForm.get(['option'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffre>>): void {
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
