import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOffre } from 'app/shared/model/offre.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { OffreService } from './offre.service';
import { OffreDeleteDialogComponent } from './offre-delete-dialog.component';

@Component({
  selector: 'jhi-offre',
  templateUrl: './offre.component.html',
})
export class OffreComponent implements OnInit, OnDestroy {
  offres: IOffre[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected offreService: OffreService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.offres = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.offreService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IOffre[]>) => this.paginateOffres(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.offres = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOffres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOffre): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOffres(): void {
    this.eventSubscriber = this.eventManager.subscribe('offreListModification', () => this.reset());
  }

  delete(offre: IOffre): void {
    const modalRef = this.modalService.open(OffreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.offre = offre;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateOffres(data: IOffre[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.offres.push(data[i]);
      }
    }
  }
}
