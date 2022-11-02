import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConcoursFonctionPubliqueSharedModule } from 'app/shared/shared.module';
import { FichierComponent } from './fichier.component';
import { FichierDetailComponent } from './fichier-detail.component';
import { FichierUpdateComponent } from './fichier-update.component';
import { FichierDeleteDialogComponent } from './fichier-delete-dialog.component';
import { fichierRoute } from './fichier.route';

@NgModule({
  imports: [ConcoursFonctionPubliqueSharedModule, RouterModule.forChild(fichierRoute)],
  declarations: [FichierComponent, FichierDetailComponent, FichierUpdateComponent, FichierDeleteDialogComponent],
  entryComponents: [FichierDeleteDialogComponent],
})
export class ConcoursFonctionPubliqueFichierModule {}
