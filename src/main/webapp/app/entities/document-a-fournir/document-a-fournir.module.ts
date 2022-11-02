import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConcoursFonctionPubliqueSharedModule } from 'app/shared/shared.module';
import { DocumentAFournirComponent } from './document-a-fournir.component';
import { DocumentAFournirDetailComponent } from './document-a-fournir-detail.component';
import { DocumentAFournirUpdateComponent } from './document-a-fournir-update.component';
import { DocumentAFournirDeleteDialogComponent } from './document-a-fournir-delete-dialog.component';
import { documentAFournirRoute } from './document-a-fournir.route';

@NgModule({
  imports: [ConcoursFonctionPubliqueSharedModule, RouterModule.forChild(documentAFournirRoute)],
  declarations: [
    DocumentAFournirComponent,
    DocumentAFournirDetailComponent,
    DocumentAFournirUpdateComponent,
    DocumentAFournirDeleteDialogComponent,
  ],
  entryComponents: [DocumentAFournirDeleteDialogComponent],
})
export class ConcoursFonctionPubliqueDocumentAFournirModule {}
