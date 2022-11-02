import { IOffre } from 'app/shared/model/offre.model';

export interface IEpreuveAconcourir {
  id?: number;
  nom?: string;
  duree?: number;
  coefficiant?: number;
  noteEleminatoire?: string;
  offre?: IOffre;
}

export class EpreuveAconcourir implements IEpreuveAconcourir {
  constructor(
    public id?: number,
    public nom?: string,
    public duree?: number,
    public coefficiant?: number,
    public noteEleminatoire?: string,
    public offre?: IOffre
  ) {}
}
