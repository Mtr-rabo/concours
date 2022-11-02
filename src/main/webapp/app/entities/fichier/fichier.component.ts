import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFichier } from 'app/shared/model/fichier.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FichierService } from './fichier.service';
import { FichierDeleteDialogComponent } from './fichier-delete-dialog.component';

@Component({
  selector: 'jhi-fichier',
  templateUrl: './fichier.component.html',
})
export class FichierComponent implements OnInit, OnDestroy {
  fichiers: IFichier[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected fichierService: FichierService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.fichiers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.fichierService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IFichier[]>) => this.paginateFichiers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.fichiers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFichiers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFichier): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInFichiers(): void {
    this.eventSubscriber = this.eventManager.subscribe('fichierListModification', () => this.reset());
  }

  delete(fichier: IFichier): void {
    const modalRef = this.modalService.open(FichierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fichier = fichier;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFichiers(data: IFichier[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.fichiers.push(data[i]);
      }
    }
  }
}
