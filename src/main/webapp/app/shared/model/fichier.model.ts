import { IDepot } from 'app/shared/model/depot.model';

export interface IFichier {
  id?: number;
  nomFichier?: string;
  fichContentType?: string;
  fich?: any;
  depot?: IDepot;
}

export class Fichier implements IFichier {
  constructor(public id?: number, public nomFichier?: string, public fichContentType?: string, public fich?: any, public depot?: IDepot) {}
}
