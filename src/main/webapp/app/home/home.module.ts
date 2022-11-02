import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConcoursFonctionPubliqueSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { DetailOffreHomeComponent } from './detail-offre-home/detail-offre-home.component';
import { PostulezOffreHomeComponent } from './postulez-offre-home/postulez-offre-home.component';

@NgModule({
  imports: [ConcoursFonctionPubliqueSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, DetailOffreHomeComponent, PostulezOffreHomeComponent],
})
export class ConcoursFonctionPubliqueHomeModule {}
