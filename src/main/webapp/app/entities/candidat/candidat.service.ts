import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICandidat } from 'app/shared/model/candidat.model';

type EntityResponseType = HttpResponse<ICandidat>;
type EntityArrayResponseType = HttpResponse<ICandidat[]>;

@Injectable({ providedIn: 'root' })
export class CandidatService {
  public resourceUrl = SERVER_API_URL + 'api/candidats';

  constructor(protected http: HttpClient) {}

  create(candidat: ICandidat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidat);
    return this.http
      .post<ICandidat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(candidat: ICandidat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidat);
    return this.http
      .put<ICandidat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICandidat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICandidat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(candidat: ICandidat): ICandidat {
    const copy: ICandidat = Object.assign({}, candidat, {
      dateNaissance: candidat.dateNaissance && candidat.dateNaissance.isValid() ? candidat.dateNaissance.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance ? moment(res.body.dateNaissance) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((candidat: ICandidat) => {
        candidat.dateNaissance = candidat.dateNaissance ? moment(candidat.dateNaissance) : undefined;
      });
    }
    return res;
  }
}
