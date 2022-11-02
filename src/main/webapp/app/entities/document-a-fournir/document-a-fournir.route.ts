import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocumentAFournir, DocumentAFournir } from 'app/shared/model/document-a-fournir.model';
import { DocumentAFournirService } from './document-a-fournir.service';
import { DocumentAFournirComponent } from './document-a-fournir.component';
import { DocumentAFournirDetailComponent } from './document-a-fournir-detail.component';
import { DocumentAFournirUpdateComponent } from './document-a-fournir-update.component';

@Injectable({ providedIn: 'root' })
export class DocumentAFournirResolve implements Resolve<IDocumentAFournir> {
  constructor(private service: DocumentAFournirService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentAFournir> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((documentAFournir: HttpResponse<DocumentAFournir>) => {
          if (documentAFournir.body) {
            return of(documentAFournir.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentAFournir());
  }
}

export const documentAFournirRoute: Routes = [
  {
    path: '',
    component: DocumentAFournirComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'concoursFonctionPubliqueApp.documentAFournir.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentAFournirDetailComponent,
    resolve: {
      documentAFournir: DocumentAFournirResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.documentAFournir.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentAFournirUpdateComponent,
    resolve: {
      documentAFournir: DocumentAFournirResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.documentAFournir.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentAFournirUpdateComponent,
    resolve: {
      documentAFournir: DocumentAFournirResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.documentAFournir.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
