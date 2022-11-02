import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOffre } from 'app/shared/model/offre.model';
import { OffreService } from './offre.service';

@Component({
  templateUrl: './offre-delete-dialog.component.html',
})
export class OffreDeleteDialogComponent {
  offre?: IOffre;

  constructor(protected offreService: OffreService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.offreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('offreListModification');
      this.activeModal.close();
    });
  }
}
