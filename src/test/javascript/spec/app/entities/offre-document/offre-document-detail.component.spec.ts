import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { OffreDocumentDetailComponent } from 'app/entities/offre-document/offre-document-detail.component';
import { OffreDocument } from 'app/shared/model/offre-document.model';

describe('Component Tests', () => {
  describe('OffreDocument Management Detail Component', () => {
    let comp: OffreDocumentDetailComponent;
    let fixture: ComponentFixture<OffreDocumentDetailComponent>;
    const route = ({ data: of({ offreDocument: new OffreDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [OffreDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OffreDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OffreDocumentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load offreDocument on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offreDocument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
