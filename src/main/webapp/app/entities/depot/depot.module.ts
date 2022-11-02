import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConcoursFonctionPubliqueSharedModule } from 'app/shared/shared.module';
import { DepotComponent } from './depot.component';
import { DepotDetailComponent } from './depot-detail.component';
import { DepotUpdateComponent } from './depot-update.component';
import { DepotDeleteDialogComponent } from './depot-delete-dialog.component';
import { depotRoute } from './depot.route';

@NgModule({
  imports: [ConcoursFonctionPubliqueSharedModule, RouterModule.forChild(depotRoute)],
  declarations: [DepotComponent, DepotDetailComponent, DepotUpdateComponent, DepotDeleteDialogComponent],
  entryComponents: [DepotDeleteDialogComponent],
})
export class ConcoursFonctionPubliqueDepotModule {}
