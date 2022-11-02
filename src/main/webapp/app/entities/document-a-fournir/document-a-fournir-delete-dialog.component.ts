import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentAFournir } from 'app/shared/model/document-a-fournir.model';
import { DocumentAFournirService } from './document-a-fournir.service';

@Component({
  templateUrl: './document-a-fournir-delete-dialog.component.html',
})
export class DocumentAFournirDeleteDialogComponent {
  documentAFournir?: IDocumentAFournir;

  constructor(
    protected documentAFournirService: DocumentAFournirService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentAFournirService.delete(id).subscribe(() => {
      this.eventManager.broadcast('documentAFournirListModification');
      this.activeModal.close();
    });
  }
}
