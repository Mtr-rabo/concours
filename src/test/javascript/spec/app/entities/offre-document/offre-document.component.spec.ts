import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { OffreDocumentComponent } from 'app/entities/offre-document/offre-document.component';
import { OffreDocumentService } from 'app/entities/offre-document/offre-document.service';
import { OffreDocument } from 'app/shared/model/offre-document.model';

describe('Component Tests', () => {
  describe('OffreDocument Management Component', () => {
    let comp: OffreDocumentComponent;
    let fixture: ComponentFixture<OffreDocumentComponent>;
    let service: OffreDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [OffreDocumentComponent],
      })
        .overrideTemplate(OffreDocumentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OffreDocumentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OffreDocumentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OffreDocument(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.offreDocuments && comp.offreDocuments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
