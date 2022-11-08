import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepot } from 'app/shared/model/depot.model';
import { IDepotWithCandidat } from 'app/shared/model/depot-with-candidat.model';

type EntityResponseType = HttpResponse<IDepot>;
type EntityArrayResponseType = HttpResponse<IDepot[]>;

@Injectable({ providedIn: 'root' })
export class DepotService {
  public resourceUrl = SERVER_API_URL + 'api/depots';

  constructor(protected http: HttpClient) {}

  create(depot: IDepot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(depot);
    return this.http
      .post<IDepot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  createWithCandidat(depot: IDepotWithCandidat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClientWithCandidat(depot);
    return this.http
      .post<IDepot>(this.resourceUrl.concat('/candidat'), copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(depot: IDepot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(depot);
    return this.http
      .put<IDepot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDepot>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDepot[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(depot: IDepot): IDepot {
    const copy: IDepot = Object.assign({}, depot, {
      dateDepot: depot.dateDepot && depot.dateDepot.isValid() ? depot.dateDepot.toJSON() : undefined,
    });
    return copy;
  }
  protected convertDateFromClientWithCandidat(depotWithCandidat: IDepotWithCandidat): IDepot {
    const copy: IDepot = Object.assign({}, depotWithCandidat.depot, {
      dateDepot: depotWithCandidat.depot?.dateDepot && depotWithCandidat.depot?.dateDepot.isValid() ? depotWithCandidat.depot?.dateDepot.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDepot = res.body.dateDepot ? moment(res.body.dateDepot) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((depot: IDepot) => {
        depot.dateDepot = depot.dateDepot ? moment(depot.dateDepot) : undefined;
      });
    }
    return res;
  }
}
