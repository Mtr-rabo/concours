import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOffreDocument } from 'app/shared/model/offre-document.model';
import { OffreDocumentService } from './offre-document.service';

@Component({
  templateUrl: './offre-document-delete-dialog.component.html',
})
export class OffreDocumentDeleteDialogComponent {
  offreDocument?: IOffreDocument;

  constructor(
    protected offreDocumentService: OffreDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.offreDocumentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('offreDocumentListModification');
      this.activeModal.close();
    });
  }
}
