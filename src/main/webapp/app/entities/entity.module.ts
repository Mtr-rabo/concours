import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'categorie',
        loadChildren: () => import('./categorie/categorie.module').then(m => m.ConcoursFonctionPubliqueCategorieModule),
      },
      {
        path: 'epreuve-aconcourir',
        loadChildren: () =>
          import('./epreuve-aconcourir/epreuve-aconcourir.module').then(m => m.ConcoursFonctionPubliqueEpreuveAconcourirModule),
      },
      {
        path: 'fichier',
        loadChildren: () => import('./fichier/fichier.module').then(m => m.ConcoursFonctionPubliqueFichierModule),
      },
      {
        path: 'minister-concerner',
        loadChildren: () =>
          import('./minister-concerner/minister-concerner.module').then(m => m.ConcoursFonctionPubliqueMinisterConcernerModule),
      },
      {
        path: 'candidat',
        loadChildren: () => import('./candidat/candidat.module').then(m => m.ConcoursFonctionPubliqueCandidatModule),
      },
      {
        path: 'offre',
        loadChildren: () => import('./offre/offre.module').then(m => m.ConcoursFonctionPubliqueOffreModule),
      },
      {
        path: 'offre-document',
        loadChildren: () => import('./offre-document/offre-document.module').then(m => m.ConcoursFonctionPubliqueOffreDocumentModule),
      },
      {
        path: 'option',
        loadChildren: () => import('./option/option.module').then(m => m.ConcoursFonctionPubliqueOptionModule),
      },
      {
        path: 'document-a-fournir',
        loadChildren: () =>
          import('./document-a-fournir/document-a-fournir.module').then(m => m.ConcoursFonctionPubliqueDocumentAFournirModule),
      },
      {
        path: 'depot',
        loadChildren: () => import('./depot/depot.module').then(m => m.ConcoursFonctionPubliqueDepotModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ConcoursFonctionPubliqueEntityModule {}
