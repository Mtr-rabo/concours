import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentAFournir } from 'app/shared/model/document-a-fournir.model';

@Component({
  selector: 'jhi-document-a-fournir-detail',
  templateUrl: './document-a-fournir-detail.component.html',
})
export class DocumentAFournirDetailComponent implements OnInit {
  documentAFournir: IDocumentAFournir | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentAFournir }) => (this.documentAFournir = documentAFournir));
  }

  previousState(): void {
    window.history.back();
  }
}
