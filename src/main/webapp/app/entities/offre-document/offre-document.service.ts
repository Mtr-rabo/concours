import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOffreDocument } from 'app/shared/model/offre-document.model';

type EntityResponseType = HttpResponse<IOffreDocument>;
type EntityArrayResponseType = HttpResponse<IOffreDocument[]>;

@Injectable({ providedIn: 'root' })
export class OffreDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/offre-documents';

  constructor(protected http: HttpClient) {}

  create(offreDocument: IOffreDocument): Observable<EntityResponseType> {
    return this.http.post<IOffreDocument>(this.resourceUrl, offreDocument, { observe: 'response' });
  }

  update(offreDocument: IOffreDocument): Observable<EntityResponseType> {
    return this.http.put<IOffreDocument>(this.resourceUrl, offreDocument, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOffreDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOffreDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
