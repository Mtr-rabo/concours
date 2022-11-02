import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEpreuveAconcourir } from 'app/shared/model/epreuve-aconcourir.model';

@Component({
  selector: 'jhi-epreuve-aconcourir-detail',
  templateUrl: './epreuve-aconcourir-detail.component.html',
})
export class EpreuveAconcourirDetailComponent implements OnInit {
  epreuveAconcourir: IEpreuveAconcourir | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ epreuveAconcourir }) => (this.epreuveAconcourir = epreuveAconcourir));
  }

  previousState(): void {
    window.history.back();
  }
}
