import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICandidat, Candidat } from 'app/shared/model/candidat.model';
import { CandidatService } from './candidat.service';
import { CandidatComponent } from './candidat.component';
import { CandidatDetailComponent } from './candidat-detail.component';
import { CandidatUpdateComponent } from './candidat-update.component';

@Injectable({ providedIn: 'root' })
export class CandidatResolve implements Resolve<ICandidat> {
  constructor(private service: CandidatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICandidat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((candidat: HttpResponse<Candidat>) => {
          if (candidat.body) {
            return of(candidat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Candidat());
  }
}

export const candidatRoute: Routes = [
  {
    path: '',
    component: CandidatComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'concoursFonctionPubliqueApp.candidat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CandidatDetailComponent,
    resolve: {
      candidat: CandidatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.candidat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CandidatUpdateComponent,
    resolve: {
      candidat: CandidatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.candidat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CandidatUpdateComponent,
    resolve: {
      candidat: CandidatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.candidat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
