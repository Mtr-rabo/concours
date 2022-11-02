import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConcoursFonctionPubliqueSharedModule } from 'app/shared/shared.module';
import { EpreuveAconcourirComponent } from './epreuve-aconcourir.component';
import { EpreuveAconcourirDetailComponent } from './epreuve-aconcourir-detail.component';
import { EpreuveAconcourirUpdateComponent } from './epreuve-aconcourir-update.component';
import { EpreuveAconcourirDeleteDialogComponent } from './epreuve-aconcourir-delete-dialog.component';
import { epreuveAconcourirRoute } from './epreuve-aconcourir.route';

@NgModule({
  imports: [ConcoursFonctionPubliqueSharedModule, RouterModule.forChild(epreuveAconcourirRoute)],
  declarations: [
    EpreuveAconcourirComponent,
    EpreuveAconcourirDetailComponent,
    EpreuveAconcourirUpdateComponent,
    EpreuveAconcourirDeleteDialogComponent,
  ],
  entryComponents: [EpreuveAconcourirDeleteDialogComponent],
})
export class ConcoursFonctionPubliqueEpreuveAconcourirModule {}
