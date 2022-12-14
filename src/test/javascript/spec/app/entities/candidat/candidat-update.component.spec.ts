import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { CandidatUpdateComponent } from 'app/entities/candidat/candidat-update.component';
import { CandidatService } from 'app/entities/candidat/candidat.service';
import { Candidat } from 'app/shared/model/candidat.model';

describe('Component Tests', () => {
  describe('Candidat Management Update Component', () => {
    let comp: CandidatUpdateComponent;
    let fixture: ComponentFixture<CandidatUpdateComponent>;
    let service: CandidatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [CandidatUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CandidatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CandidatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CandidatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Candidat(123);
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
        const entity = new Candidat();
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
