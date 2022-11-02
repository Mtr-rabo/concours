import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOffreDocument } from 'app/shared/model/offre-document.model';

@Component({
  selector: 'jhi-offre-document-detail',
  templateUrl: './offre-document-detail.component.html',
})
export class OffreDocumentDetailComponent implements OnInit {
  offreDocument: IOffreDocument | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offreDocument }) => (this.offreDocument = offreDocument));
  }

  previousState(): void {
    window.history.back();
  }
}
