import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { DocumentAFournirUpdateComponent } from 'app/entities/document-a-fournir/document-a-fournir-update.component';
import { DocumentAFournirService } from 'app/entities/document-a-fournir/document-a-fournir.service';
import { DocumentAFournir } from 'app/shared/model/document-a-fournir.model';

describe('Component Tests', () => {
  describe('DocumentAFournir Management Update Component', () => {
    let comp: DocumentAFournirUpdateComponent;
    let fixture: ComponentFixture<DocumentAFournirUpdateComponent>;
    let service: DocumentAFournirService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [DocumentAFournirUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DocumentAFournirUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentAFournirUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentAFournirService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentAFournir(123);
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
        const entity = new DocumentAFournir();
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
