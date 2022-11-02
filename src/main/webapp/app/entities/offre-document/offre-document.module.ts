import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConcoursFonctionPubliqueSharedModule } from 'app/shared/shared.module';
import { OffreDocumentComponent } from './offre-document.component';
import { OffreDocumentDetailComponent } from './offre-document-detail.component';
import { OffreDocumentUpdateComponent } from './offre-document-update.component';
import { OffreDocumentDeleteDialogComponent } from './offre-document-delete-dialog.component';
import { offreDocumentRoute } from './offre-document.route';

@NgModule({
  imports: [ConcoursFonctionPubliqueSharedModule, RouterModule.forChild(offreDocumentRoute)],
  declarations: [OffreDocumentComponent, OffreDocumentDetailComponent, OffreDocumentUpdateComponent, OffreDocumentDeleteDialogComponent],
  entryComponents: [OffreDocumentDeleteDialogComponent],
})
export class ConcoursFonctionPubliqueOffreDocumentModule {}
