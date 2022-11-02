import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConcoursFonctionPubliqueSharedModule } from 'app/shared/shared.module';
import { CandidatComponent } from './candidat.component';
import { CandidatDetailComponent } from './candidat-detail.component';
import { CandidatUpdateComponent } from './candidat-update.component';
import { CandidatDeleteDialogComponent } from './candidat-delete-dialog.component';
import { candidatRoute } from './candidat.route';

@NgModule({
  imports: [ConcoursFonctionPubliqueSharedModule, RouterModule.forChild(candidatRoute)],
  declarations: [CandidatComponent, CandidatDetailComponent, CandidatUpdateComponent, CandidatDeleteDialogComponent],
  entryComponents: [CandidatDeleteDialogComponent],
})
export class ConcoursFonctionPubliqueCandidatModule {}
