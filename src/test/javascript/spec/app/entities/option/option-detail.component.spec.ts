import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { OptionDetailComponent } from 'app/entities/option/option-detail.component';
import { Option } from 'app/shared/model/option.model';

describe('Component Tests', () => {
  describe('Option Management Detail Component', () => {
    let comp: OptionDetailComponent;
    let fixture: ComponentFixture<OptionDetailComponent>;
    const route = ({ data: of({ option: new Option(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [OptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load option on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.option).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
