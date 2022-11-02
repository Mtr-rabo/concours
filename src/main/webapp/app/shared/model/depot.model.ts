import { Moment } from 'moment';
import { IOffre } from 'app/shared/model/offre.model';
import { ICandidat } from 'app/shared/model/candidat.model';

export interface IDepot {
  id?: number;
  dateDepot?: Moment;
  isArchive?: boolean;
  isAcepte?: boolean;
  offre?: IOffre;
  candidat?: ICandidat;
}

export class Depot implements IDepot {
  constructor(
    public id?: number,
    public dateDepot?: Moment,
    public isArchive?: boolean,
    public isAcepte?: boolean,
    public offre?: IOffre,
    public candidat?: ICandidat
  ) {
    this.isArchive = this.isArchive || false;
    this.isAcepte = this.isAcepte || false;
  }
}
