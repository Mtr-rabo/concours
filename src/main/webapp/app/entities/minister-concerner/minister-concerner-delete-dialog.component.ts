import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMinisterConcerner } from 'app/shared/model/minister-concerner.model';
import { MinisterConcernerService } from './minister-concerner.service';

@Component({
  templateUrl: './minister-concerner-delete-dialog.component.html',
})
export class MinisterConcernerDeleteDialogComponent {
  ministerConcerner?: IMinisterConcerner;

  constructor(
    protected ministerConcernerService: MinisterConcernerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ministerConcernerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ministerConcernerListModification');
      this.activeModal.close();
    });
  }
}
