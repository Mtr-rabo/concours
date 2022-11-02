import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEpreuveAconcourir } from 'app/shared/model/epreuve-aconcourir.model';

type EntityResponseType = HttpResponse<IEpreuveAconcourir>;
type EntityArrayResponseType = HttpResponse<IEpreuveAconcourir[]>;

@Injectable({ providedIn: 'root' })
export class EpreuveAconcourirService {
  public resourceUrl = SERVER_API_URL + 'api/epreuve-aconcourirs';

  constructor(protected http: HttpClient) {}

  create(epreuveAconcourir: IEpreuveAconcourir): Observable<EntityResponseType> {
    return this.http.post<IEpreuveAconcourir>(this.resourceUrl, epreuveAconcourir, { observe: 'response' });
  }

  update(epreuveAconcourir: IEpreuveAconcourir): Observable<EntityResponseType> {
    return this.http.put<IEpreuveAconcourir>(this.resourceUrl, epreuveAconcourir, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEpreuveAconcourir>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEpreuveAconcourir[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
