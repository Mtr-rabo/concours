import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { OffreDetailComponent } from 'app/entities/offre/offre-detail.component';
import { Offre } from 'app/shared/model/offre.model';

describe('Component Tests', () => {
  describe('Offre Management Detail Component', () => {
    let comp: OffreDetailComponent;
    let fixture: ComponentFixture<OffreDetailComponent>;
    const route = ({ data: of({ offre: new Offre(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [OffreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OffreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OffreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load offre on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
