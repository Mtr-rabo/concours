import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEpreuveAconcourir } from 'app/shared/model/epreuve-aconcourir.model';
import { EpreuveAconcourirService } from './epreuve-aconcourir.service';

@Component({
  templateUrl: './epreuve-aconcourir-delete-dialog.component.html',
})
export class EpreuveAconcourirDeleteDialogComponent {
  epreuveAconcourir?: IEpreuveAconcourir;

  constructor(
    protected epreuveAconcourirService: EpreuveAconcourirService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.epreuveAconcourirService.delete(id).subscribe(() => {
      this.eventManager.broadcast('epreuveAconcourirListModification');
      this.activeModal.close();
    });
  }
}
