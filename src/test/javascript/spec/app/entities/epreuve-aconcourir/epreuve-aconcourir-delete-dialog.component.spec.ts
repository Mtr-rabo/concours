import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { EpreuveAconcourirDeleteDialogComponent } from 'app/entities/epreuve-aconcourir/epreuve-aconcourir-delete-dialog.component';
import { EpreuveAconcourirService } from 'app/entities/epreuve-aconcourir/epreuve-aconcourir.service';

describe('Component Tests', () => {
  describe('EpreuveAconcourir Management Delete Component', () => {
    let comp: EpreuveAconcourirDeleteDialogComponent;
    let fixture: ComponentFixture<EpreuveAconcourirDeleteDialogComponent>;
    let service: EpreuveAconcourirService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [EpreuveAconcourirDeleteDialogComponent],
      })
        .overrideTemplate(EpreuveAconcourirDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EpreuveAconcourirDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EpreuveAconcourirService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
