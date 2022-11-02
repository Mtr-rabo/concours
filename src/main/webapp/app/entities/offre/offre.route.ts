import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOffre, Offre } from 'app/shared/model/offre.model';
import { OffreService } from './offre.service';
import { OffreComponent } from './offre.component';
import { OffreDetailComponent } from './offre-detail.component';
import { OffreUpdateComponent } from './offre-update.component';

@Injectable({ providedIn: 'root' })
export class OffreResolve implements Resolve<IOffre> {
  constructor(private service: OffreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOffre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((offre: HttpResponse<Offre>) => {
          if (offre.body) {
            return of(offre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Offre());
  }
}

export const offreRoute: Routes = [
  {
    path: '',
    component: OffreComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.offre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OffreDetailComponent,
    resolve: {
      offre: OffreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.offre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OffreUpdateComponent,
    resolve: {
      offre: OffreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.offre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OffreUpdateComponent,
    resolve: {
      offre: OffreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.offre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
