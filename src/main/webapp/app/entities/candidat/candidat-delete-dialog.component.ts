import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from './candidat.service';

@Component({
  templateUrl: './candidat-delete-dialog.component.html',
})
export class CandidatDeleteDialogComponent {
  candidat?: ICandidat;

  constructor(protected candidatService: CandidatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.candidatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('candidatListModification');
      this.activeModal.close();
    });
  }
}
