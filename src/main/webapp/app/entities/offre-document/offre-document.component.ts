import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOffreDocument } from 'app/shared/model/offre-document.model';
import { OffreDocumentService } from './offre-document.service';
import { OffreDocumentDeleteDialogComponent } from './offre-document-delete-dialog.component';

@Component({
  selector: 'jhi-offre-document',
  templateUrl: './offre-document.component.html',
})
export class OffreDocumentComponent implements OnInit, OnDestroy {
  offreDocuments?: IOffreDocument[];
  eventSubscriber?: Subscription;

  constructor(
    protected offreDocumentService: OffreDocumentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.offreDocumentService.query().subscribe((res: HttpResponse<IOffreDocument[]>) => (this.offreDocuments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOffreDocuments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOffreDocument): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOffreDocuments(): void {
    this.eventSubscriber = this.eventManager.subscribe('offreDocumentListModification', () => this.loadAll());
  }

  delete(offreDocument: IOffreDocument): void {
    const modalRef = this.modalService.open(OffreDocumentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.offreDocument = offreDocument;
  }
}
