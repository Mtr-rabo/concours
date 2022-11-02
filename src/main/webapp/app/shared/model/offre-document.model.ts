import { IOffre } from 'app/shared/model/offre.model';
import { IDocumentAFournir } from 'app/shared/model/document-a-fournir.model';

export interface IOffreDocument {
  id?: number;
  offre?: IOffre;
  documentAFournir?: IDocumentAFournir;
}

export class OffreDocument implements IOffreDocument {
  constructor(public id?: number, public offre?: IOffre, public documentAFournir?: IDocumentAFournir) {}
}
