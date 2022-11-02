import { Moment } from 'moment';

export interface ICandidat {
  id?: number;
  nom?: string;
  prenom?: string;
  telephone?: string;
  dateNaissance?: Moment;
  lieuNaissance?: string;
  age?: number;
  adresse?: string;
}

export class Candidat implements ICandidat {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public telephone?: string,
    public dateNaissance?: Moment,
    public lieuNaissance?: string,
    public age?: number,
    public adresse?: string
  ) {}
}
