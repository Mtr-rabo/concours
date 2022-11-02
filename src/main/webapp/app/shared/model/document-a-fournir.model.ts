export interface IDocumentAFournir {
  id?: number;
  code?: string;
  libelle?: string;
}

export class DocumentAFournir implements IDocumentAFournir {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
