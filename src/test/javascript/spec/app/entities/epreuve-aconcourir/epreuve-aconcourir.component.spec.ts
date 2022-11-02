import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { EpreuveAconcourirComponent } from 'app/entities/epreuve-aconcourir/epreuve-aconcourir.component';
import { EpreuveAconcourirService } from 'app/entities/epreuve-aconcourir/epreuve-aconcourir.service';
import { EpreuveAconcourir } from 'app/shared/model/epreuve-aconcourir.model';

describe('Component Tests', () => {
  describe('EpreuveAconcourir Management Component', () => {
    let comp: EpreuveAconcourirComponent;
    let fixture: ComponentFixture<EpreuveAconcourirComponent>;
    let service: EpreuveAconcourirService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [EpreuveAconcourirComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(EpreuveAconcourirComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EpreuveAconcourirComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EpreuveAconcourirService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EpreuveAconcourir(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.epreuveAconcourirs && comp.epreuveAconcourirs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EpreuveAconcourir(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.epreuveAconcourirs && comp.epreuveAconcourirs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
