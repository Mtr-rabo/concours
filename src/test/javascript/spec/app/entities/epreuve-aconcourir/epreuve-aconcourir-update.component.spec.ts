import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { EpreuveAconcourirUpdateComponent } from 'app/entities/epreuve-aconcourir/epreuve-aconcourir-update.component';
import { EpreuveAconcourirService } from 'app/entities/epreuve-aconcourir/epreuve-aconcourir.service';
import { EpreuveAconcourir } from 'app/shared/model/epreuve-aconcourir.model';

describe('Component Tests', () => {
  describe('EpreuveAconcourir Management Update Component', () => {
    let comp: EpreuveAconcourirUpdateComponent;
    let fixture: ComponentFixture<EpreuveAconcourirUpdateComponent>;
    let service: EpreuveAconcourirService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [EpreuveAconcourirUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EpreuveAconcourirUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EpreuveAconcourirUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EpreuveAconcourirService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EpreuveAconcourir(123);
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
        const entity = new EpreuveAconcourir();
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
