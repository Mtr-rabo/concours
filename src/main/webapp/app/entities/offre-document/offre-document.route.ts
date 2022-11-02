import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOffreDocument, OffreDocument } from 'app/shared/model/offre-document.model';
import { OffreDocumentService } from './offre-document.service';
import { OffreDocumentComponent } from './offre-document.component';
import { OffreDocumentDetailComponent } from './offre-document-detail.component';
import { OffreDocumentUpdateComponent } from './offre-document-update.component';

@Injectable({ providedIn: 'root' })
export class OffreDocumentResolve implements Resolve<IOffreDocument> {
  constructor(private service: OffreDocumentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOffreDocument> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((offreDocument: HttpResponse<OffreDocument>) => {
          if (offreDocument.body) {
            return of(offreDocument.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OffreDocument());
  }
}

export const offreDocumentRoute: Routes = [
  {
    path: '',
    component: OffreDocumentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.offreDocument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OffreDocumentDetailComponent,
    resolve: {
      offreDocument: OffreDocumentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.offreDocument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OffreDocumentUpdateComponent,
    resolve: {
      offreDocument: OffreDocumentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.offreDocument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OffreDocumentUpdateComponent,
    resolve: {
      offreDocument: OffreDocumentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.offreDocument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
