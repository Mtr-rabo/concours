import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDepot, Depot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';
import { DepotComponent } from './depot.component';
import { DepotDetailComponent } from './depot-detail.component';
import { DepotUpdateComponent } from './depot-update.component';

@Injectable({ providedIn: 'root' })
export class DepotResolve implements Resolve<IDepot> {
  constructor(private service: DepotService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDepot> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((depot: HttpResponse<Depot>) => {
          if (depot.body) {
            return of(depot.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Depot());
  }
}

export const depotRoute: Routes = [
  {
    path: '',
    component: DepotComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'concoursFonctionPubliqueApp.depot.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DepotDetailComponent,
    resolve: {
      depot: DepotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.depot.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DepotUpdateComponent,
    resolve: {
      depot: DepotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.depot.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DepotUpdateComponent,
    resolve: {
      depot: DepotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.depot.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
