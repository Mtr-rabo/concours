import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConcoursFonctionPubliqueSharedModule } from 'app/shared/shared.module';
import { MinisterConcernerComponent } from './minister-concerner.component';
import { MinisterConcernerDetailComponent } from './minister-concerner-detail.component';
import { MinisterConcernerUpdateComponent } from './minister-concerner-update.component';
import { MinisterConcernerDeleteDialogComponent } from './minister-concerner-delete-dialog.component';
import { ministerConcernerRoute } from './minister-concerner.route';

@NgModule({
  imports: [ConcoursFonctionPubliqueSharedModule, RouterModule.forChild(ministerConcernerRoute)],
  declarations: [
    MinisterConcernerComponent,
    MinisterConcernerDetailComponent,
    MinisterConcernerUpdateComponent,
    MinisterConcernerDeleteDialogComponent,
  ],
  entryComponents: [MinisterConcernerDeleteDialogComponent],
})
export class ConcoursFonctionPubliqueMinisterConcernerModule {}
