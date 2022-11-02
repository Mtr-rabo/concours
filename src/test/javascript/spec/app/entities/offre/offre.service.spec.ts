import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OffreService } from 'app/entities/offre/offre.service';
import { IOffre, Offre } from 'app/shared/model/offre.model';

describe('Service Tests', () => {
  describe('Offre Service', () => {
    let injector: TestBed;
    let service: OffreService;
    let httpMock: HttpTestingController;
    let elemDefault: IOffre;
    let expectedResult: IOffre | IOffre[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OffreService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Offre(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, false, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOuverture: currentDate.format(DATE_TIME_FORMAT),
            dateCloture: currentDate.format(DATE_TIME_FORMAT),
            dateConcours: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Offre', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOuverture: currentDate.format(DATE_TIME_FORMAT),
            dateCloture: currentDate.format(DATE_TIME_FORMAT),
            dateConcours: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOuverture: currentDate,
            dateCloture: currentDate,
            dateConcours: currentDate,
          },
          returnedFromService
        );

        service.create(new Offre()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Offre', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            nomPoste: 'BBBBBB',
            descrip: 'BBBBBB',
            dateOuverture: currentDate.format(DATE_TIME_FORMAT),
            dateCloture: currentDate.format(DATE_TIME_FORMAT),
            isArchive: true,
            dateConcours: currentDate.format(DATE_TIME_FORMAT),
            ageLimite: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOuverture: currentDate,
            dateCloture: currentDate,
            dateConcours: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Offre', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            nomPoste: 'BBBBBB',
            descrip: 'BBBBBB',
            dateOuverture: currentDate.format(DATE_TIME_FORMAT),
            dateCloture: currentDate.format(DATE_TIME_FORMAT),
            isArchive: true,
            dateConcours: currentDate.format(DATE_TIME_FORMAT),
            ageLimite: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOuverture: currentDate,
            dateCloture: currentDate,
            dateConcours: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Offre', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
