export interface ICategorie {
  id?: number;
  code?: string;
  libelle?: string;
}

export class Categorie implements ICategorie {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
