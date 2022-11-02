import { Moment } from 'moment';
import { IMinisterConcerner } from 'app/shared/model/minister-concerner.model';
import { ICategorie } from 'app/shared/model/categorie.model';
import { IOption } from 'app/shared/model/option.model';

export interface IOffre {
  id?: number;
  code?: string;
  nomPoste?: string;
  descrip?: string;
  dateOuverture?: Moment;
  dateCloture?: Moment;
  isArchive?: boolean;
  dateConcours?: Moment;
  ageLimite?: number;
  ministerConcerner?: IMinisterConcerner;
  categrie?: ICategorie;
  option?: IOption;
}

export class Offre implements IOffre {
  constructor(
    public id?: number,
    public code?: string,
    public nomPoste?: string,
    public descrip?: string,
    public dateOuverture?: Moment,
    public dateCloture?: Moment,
    public isArchive?: boolean,
    public dateConcours?: Moment,
    public ageLimite?: number,
    public ministerConcerner?: IMinisterConcerner,
    public categrie?: ICategorie,
    public option?: IOption
  ) {
    this.isArchive = this.isArchive || false;
  }
}
