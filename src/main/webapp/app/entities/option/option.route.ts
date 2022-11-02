import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOption, Option } from 'app/shared/model/option.model';
import { OptionService } from './option.service';
import { OptionComponent } from './option.component';
import { OptionDetailComponent } from './option-detail.component';
import { OptionUpdateComponent } from './option-update.component';

@Injectable({ providedIn: 'root' })
export class OptionResolve implements Resolve<IOption> {
  constructor(private service: OptionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOption> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((option: HttpResponse<Option>) => {
          if (option.body) {
            return of(option.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Option());
  }
}

export const optionRoute: Routes = [
  {
    path: '',
    component: OptionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'concoursFonctionPubliqueApp.option.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OptionDetailComponent,
    resolve: {
      option: OptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.option.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OptionUpdateComponent,
    resolve: {
      option: OptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.option.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OptionUpdateComponent,
    resolve: {
      option: OptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'concoursFonctionPubliqueApp.option.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
