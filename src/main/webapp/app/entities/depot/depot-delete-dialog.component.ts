import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';

@Component({
  templateUrl: './depot-delete-dialog.component.html',
})
export class DepotDeleteDialogComponent {
  depot?: IDepot;

  constructor(protected depotService: DepotService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.depotService.delete(id).subscribe(() => {
      this.eventManager.broadcast('depotListModification');
      this.activeModal.close();
    });
  }
}
