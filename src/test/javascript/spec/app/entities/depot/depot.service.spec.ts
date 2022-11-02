import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DepotService } from 'app/entities/depot/depot.service';
import { IDepot, Depot } from 'app/shared/model/depot.model';

describe('Service Tests', () => {
  describe('Depot Service', () => {
    let injector: TestBed;
    let service: DepotService;
    let httpMock: HttpTestingController;
    let elemDefault: IDepot;
    let expectedResult: IDepot | IDepot[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DepotService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Depot(0, currentDate, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateDepot: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Depot', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateDepot: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDepot: currentDate,
          },
          returnedFromService
        );

        service.create(new Depot()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Depot', () => {
        const returnedFromService = Object.assign(
          {
            dateDepot: currentDate.format(DATE_TIME_FORMAT),
            isArchive: true,
            isAcepte: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDepot: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Depot', () => {
        const returnedFromService = Object.assign(
          {
            dateDepot: currentDate.format(DATE_TIME_FORMAT),
            isArchive: true,
            isAcepte: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDepot: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Depot', () => {
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
