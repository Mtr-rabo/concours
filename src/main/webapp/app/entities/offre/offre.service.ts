import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOffre } from 'app/shared/model/offre.model';

type EntityResponseType = HttpResponse<IOffre>;
type EntityArrayResponseType = HttpResponse<IOffre[]>;

@Injectable({ providedIn: 'root' })
export class OffreService {
  public resourceUrl = SERVER_API_URL + 'api/offres';

  constructor(protected http: HttpClient) {}

  create(offre: IOffre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offre);
    return this.http
      .post<IOffre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(offre: IOffre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offre);
    return this.http
      .put<IOffre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOffre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOffre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(offre: IOffre): IOffre {
    const copy: IOffre = Object.assign({}, offre, {
      dateOuverture: offre.dateOuverture && offre.dateOuverture.isValid() ? offre.dateOuverture.toJSON() : undefined,
      dateCloture: offre.dateCloture && offre.dateCloture.isValid() ? offre.dateCloture.toJSON() : undefined,
      dateConcours: offre.dateConcours && offre.dateConcours.isValid() ? offre.dateConcours.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOuverture = res.body.dateOuverture ? moment(res.body.dateOuverture) : undefined;
      res.body.dateCloture = res.body.dateCloture ? moment(res.body.dateCloture) : undefined;
      res.body.dateConcours = res.body.dateConcours ? moment(res.body.dateConcours) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((offre: IOffre) => {
        offre.dateOuverture = offre.dateOuverture ? moment(offre.dateOuverture) : undefined;
        offre.dateCloture = offre.dateCloture ? moment(offre.dateCloture) : undefined;
        offre.dateConcours = offre.dateConcours ? moment(offre.dateConcours) : undefined;
      });
    }
    return res;
  }
}
