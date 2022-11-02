import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { OffreDocumentUpdateComponent } from 'app/entities/offre-document/offre-document-update.component';
import { OffreDocumentService } from 'app/entities/offre-document/offre-document.service';
import { OffreDocument } from 'app/shared/model/offre-document.model';

describe('Component Tests', () => {
  describe('OffreDocument Management Update Component', () => {
    let comp: OffreDocumentUpdateComponent;
    let fixture: ComponentFixture<OffreDocumentUpdateComponent>;
    let service: OffreDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [OffreDocumentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OffreDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OffreDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OffreDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OffreDocument(123);
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
        const entity = new OffreDocument();
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
