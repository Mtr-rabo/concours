import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConcoursFonctionPubliqueTestModule } from '../../../test.module';
import { DocumentAFournirDetailComponent } from 'app/entities/document-a-fournir/document-a-fournir-detail.component';
import { DocumentAFournir } from 'app/shared/model/document-a-fournir.model';

describe('Component Tests', () => {
  describe('DocumentAFournir Management Detail Component', () => {
    let comp: DocumentAFournirDetailComponent;
    let fixture: ComponentFixture<DocumentAFournirDetailComponent>;
    const route = ({ data: of({ documentAFournir: new DocumentAFournir(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConcoursFonctionPubliqueTestModule],
        declarations: [DocumentAFournirDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DocumentAFournirDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentAFournirDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load documentAFournir on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.documentAFournir).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
