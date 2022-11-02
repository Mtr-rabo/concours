import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { MinisterConcernerUpdateComponent } from 'app/entities/minister-concerner/minister-concerner-update.component';
import { MinisterConcernerService } from 'app/entities/minister-concerner/minister-concerner.service';
import { MinisterConcerner } from 'app/shared/model/minister-concerner.model';

describe('Component Tests', () => {
  describe('MinisterConcerner Management Update Component', () => {
    let comp: MinisterConcernerUpdateComponent;
    let fixture: ComponentFixture<MinisterConcernerUpdateComponent>;
    let service: MinisterConcernerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [MinisterConcernerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MinisterConcernerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MinisterConcernerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MinisterConcernerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MinisterConcerner(123);
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
        const entity = new MinisterConcerner();
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
