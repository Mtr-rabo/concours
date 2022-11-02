import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { EpreuveAconcourirDetailComponent } from 'app/entities/epreuve-aconcourir/epreuve-aconcourir-detail.component';
import { EpreuveAconcourir } from 'app/shared/model/epreuve-aconcourir.model';

describe('Component Tests', () => {
  describe('EpreuveAconcourir Management Detail Component', () => {
    let comp: EpreuveAconcourirDetailComponent;
    let fixture: ComponentFixture<EpreuveAconcourirDetailComponent>;
    const route = ({ data: of({ epreuveAconcourir: new EpreuveAconcourir(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [EpreuveAconcourirDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EpreuveAconcourirDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EpreuveAconcourirDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load epreuveAconcourir on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.epreuveAconcourir).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
