export interface IMinisterConcerner {
  id?: number;
  nom?: string;
  domaine?: string;
  description?: string;
}

export class MinisterConcerner implements IMinisterConcerner {
  constructor(public id?: number, public nom?: string, public domaine?: string, public description?: string) {}
}
