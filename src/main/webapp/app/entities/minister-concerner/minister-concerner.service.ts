import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMinisterConcerner } from 'app/shared/model/minister-concerner.model';

type EntityResponseType = HttpResponse<IMinisterConcerner>;
type EntityArrayResponseType = HttpResponse<IMinisterConcerner[]>;

@Injectable({ providedIn: 'root' })
export class MinisterConcernerService {
  public resourceUrl = SERVER_API_URL + 'api/minister-concerners';

  constructor(protected http: HttpClient) {}

  create(ministerConcerner: IMinisterConcerner): Observable<EntityResponseType> {
    return this.http.post<IMinisterConcerner>(this.resourceUrl, ministerConcerner, { observe: 'response' });
  }

  update(ministerConcerner: IMinisterConcerner): Observable<EntityResponseType> {
    return this.http.put<IMinisterConcerner>(this.resourceUrl, ministerConcerner, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMinisterConcerner>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMinisterConcerner[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
