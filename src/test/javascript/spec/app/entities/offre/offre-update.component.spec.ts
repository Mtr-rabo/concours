import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { OffreUpdateComponent } from 'app/entities/offre/offre-update.component';
import { OffreService } from 'app/entities/offre/offre.service';
import { Offre } from 'app/shared/model/offre.model';

describe('Component Tests', () => {
  describe('Offre Management Update Component', () => {
    let comp: OffreUpdateComponent;
    let fixture: ComponentFixture<OffreUpdateComponent>;
    let service: OffreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [OffreUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OffreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OffreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OffreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Offre(123);
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
        const entity = new Offre();
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
