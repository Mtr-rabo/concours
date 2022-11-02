import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOption } from 'app/shared/model/option.model';

type EntityResponseType = HttpResponse<IOption>;
type EntityArrayResponseType = HttpResponse<IOption[]>;

@Injectable({ providedIn: 'root' })
export class OptionService {
  public resourceUrl = SERVER_API_URL + 'api/options';

  constructor(protected http: HttpClient) {}

  create(option: IOption): Observable<EntityResponseType> {
    return this.http.post<IOption>(this.resourceUrl, option, { observe: 'response' });
  }

  update(option: IOption): Observable<EntityResponseType> {
    return this.http.put<IOption>(this.resourceUrl, option, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOption>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOption[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
