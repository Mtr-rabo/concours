import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/shared/constants/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { DetailOffreHomeComponent } from './home/detail-offre-home/detail-offre-home.component';
import { OffreResolve } from './entities/offre/offre.route';
import { PostulezOffreHomeComponent } from './home/postulez-offre-home/postulez-offre-home.component';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: ':id/detail',
          resolve: {
            offre: OffreResolve,
          },
          component:DetailOffreHomeComponent
        },
        {
          path: ':id/postulez',
          resolve: {
            offre: OffreResolve,
          },
          component:PostulezOffreHomeComponent
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        ...LAYOUT_ROUTES,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class ConcoursFonctionPubliqueAppRoutingModule {}
