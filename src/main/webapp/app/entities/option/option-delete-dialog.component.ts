import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOption } from 'app/shared/model/option.model';
import { OptionService } from './option.service';

@Component({
  templateUrl: './option-delete-dialog.component.html',
})
export class OptionDeleteDialogComponent {
  option?: IOption;

  constructor(protected optionService: OptionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.optionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('optionListModification');
      this.activeModal.close();
    });
  }
}
