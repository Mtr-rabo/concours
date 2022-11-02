import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { MinisterConcernerDetailComponent } from 'app/entities/minister-concerner/minister-concerner-detail.component';
import { MinisterConcerner } from 'app/shared/model/minister-concerner.model';

describe('Component Tests', () => {
  describe('MinisterConcerner Management Detail Component', () => {
    let comp: MinisterConcernerDetailComponent;
    let fixture: ComponentFixture<MinisterConcernerDetailComponent>;
    const route = ({ data: of({ ministerConcerner: new MinisterConcerner(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [MinisterConcernerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MinisterConcernerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MinisterConcernerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ministerConcerner on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ministerConcerner).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
