export interface IOption {
  id?: number;
  code?: string;
  libelle?: string;
}

export class Option implements IOption {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
