import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { OptionUpdateComponent } from 'app/entities/option/option-update.component';
import { OptionService } from 'app/entities/option/option.service';
import { Option } from 'app/shared/model/option.model';

describe('Component Tests', () => {
  describe('Option Management Update Component', () => {
    let comp: OptionUpdateComponent;
    let fixture: ComponentFixture<OptionUpdateComponent>;
    let service: OptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [OptionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Option(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Option();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
