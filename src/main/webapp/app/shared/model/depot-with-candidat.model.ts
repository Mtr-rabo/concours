
import { IDepot } from '../model/depot.model';
import { IFichier } from '../model/fichier.model';
export interface IDepotWithCandidat {
  depot?: IDepot;
  fichiers?:IFichier[];

}

export class DepotWithCandidat implements IDepotWithCandidat {
  constructor(
    public depot?: IDepot,
    public fichiers?: IFichier[],

  ) {

  }
}
