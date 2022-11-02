import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEpreuveAconcourir, EpreuveAconcourir } from 'app/shared/model/epreuve-aconcourir.model';
import { EpreuveAconcourirService } from './epreuve-aconcourir.service';
import { EpreuveAconcourirComponent } from './epreuve-aconcourir.component';
import { EpreuveAconcourirDetailComponent } from './epreuve-aconcourir-detail.component';
import { EpreuveAconcourirUpdateComponent } from './epreuve-aconcourir-update.component';

@Injectable({ providedIn: 'root' })
export class EpreuveAconcourirResolve implements Resolve<IEpreuveAconcourir> {
  constructor(private service: EpreuveAconcourirService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEpreuveAconcourir> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((epreuveAconcourir: HttpResponse<EpreuveAconcourir>) => {
          if (epreuveAconcourir.body) {
            return of(epreuveAconcourir.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EpreuveAconcourir());
  }
}

export const epreuveAconcourirRoute: Routes = [
  {
    path: '',
    component: EpreuveAconcourirComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'concoursFonctionPubliqueApp.epreuveAconcourir.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EpreuveAconcourirDetailComponent,
    resolve: {
      epreuveAconcourir: EpreuveAconcourirResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.epreuveAconcourir.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EpreuveAconcourirUpdateComponent,
    resolve: {
      epreuveAconcourir: EpreuveAconcourirResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.epreuveAconcourir.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EpreuveAconcourirUpdateComponent,
    resolve: {
      epreuveAconcourir: EpreuveAconcourirResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.epreuveAconcourir.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
