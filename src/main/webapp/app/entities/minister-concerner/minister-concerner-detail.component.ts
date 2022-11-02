import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMinisterConcerner } from 'app/shared/model/minister-concerner.model';

@Component({
  selector: 'jhi-minister-concerner-detail',
  templateUrl: './minister-concerner-detail.component.html',
})
export class MinisterConcernerDetailComponent implements OnInit {
  ministerConcerner: IMinisterConcerner | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ministerConcerner }) => (this.ministerConcerner = ministerConcerner));
  }

  previousState(): void {
    window.history.back();
  }
}
