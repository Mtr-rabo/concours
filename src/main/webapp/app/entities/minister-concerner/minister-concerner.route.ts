import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMinisterConcerner, MinisterConcerner } from 'app/shared/model/minister-concerner.model';
import { MinisterConcernerService } from './minister-concerner.service';
import { MinisterConcernerComponent } from './minister-concerner.component';
import { MinisterConcernerDetailComponent } from './minister-concerner-detail.component';
import { MinisterConcernerUpdateComponent } from './minister-concerner-update.component';

@Injectable({ providedIn: 'root' })
export class MinisterConcernerResolve implements Resolve<IMinisterConcerner> {
  constructor(private service: MinisterConcernerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMinisterConcerner> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ministerConcerner: HttpResponse<MinisterConcerner>) => {
          if (ministerConcerner.body) {
            return of(ministerConcerner.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MinisterConcerner());
  }
}

export const ministerConcernerRoute: Routes = [
  {
    path: '',
    component: MinisterConcernerComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'concoursFonctionPubliqueApp.ministerConcerner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MinisterConcernerDetailComponent,
    resolve: {
      ministerConcerner: MinisterConcernerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.ministerConcerner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MinisterConcernerUpdateComponent,
    resolve: {
      ministerConcerner: MinisterConcernerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.ministerConcerner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MinisterConcernerUpdateComponent,
    resolve: {
      ministerConcerner: MinisterConcernerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.ministerConcerner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
