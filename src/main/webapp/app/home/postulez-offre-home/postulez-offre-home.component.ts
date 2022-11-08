import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CandidatService } from 'app/entities/candidat/candidat.service';
import { DepotService } from 'app/entities/depot/depot.service';
import { FichierService } from 'app/entities/fichier/fichier.service';
import { OffreDocumentService } from 'app/entities/offre-document/offre-document.service';

import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { Candidat, ICandidat } from 'app/shared/model/candidat.model';
import { IDepotWithCandidat } from 'app/shared/model/depot-with-candidat.model';
import { IDepot } from 'app/shared/model/depot.model';
import { Fichier, IFichier } from 'app/shared/model/fichier.model';
import { IOffreDocument } from 'app/shared/model/offre-document.model';
import { IOffre } from 'app/shared/model/offre.model';
import { count } from 'console';
import * as moment from 'moment';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import Swal from 'sweetalert2';


@Component({
  selector: 'jhi-postulez-offre-home',
  templateUrl: './postulez-offre-home.component.html',
  styleUrls: ['./postulez-offre-home.component.scss']
})
export class PostulezOffreHomeComponent implements OnInit {
  editForm!: FormGroup;
  editFormPiece = this.formBuilder.group({
    id: [],
    nomFichier: [],
    fich: [],
    fichContentType: [],
    depot: [],
  });
  piecesForm!: FormGroup;
  educationalDetails!: FormGroup;
  personalStep:boolean;
  addresSstep = false;
  educationStep = false;
  step = 1;
  offre: IOffre = {};
  candidat: ICandidat = {};
  listOffreDocuments:IOffreDocument[]=[];
  listDocuments:IFichier[]=[];
  depotWithCandidat:IDepotWithCandidat = {};
  depot:IDepot = {};
  countn=0;
  constructor(
    private formBuilder: FormBuilder,
    protected activatedRoute: ActivatedRoute,
    private offreDocumentService :OffreDocumentService,
    private depotService :DepotService,
    private candidatService :CandidatService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected fichierService: FichierService) {
    this.personalStep =false
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offre }) => (this.offre = offre));

    this.editForm = this.formBuilder.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      telephone: ['',Validators.required],
      dateNaissance: ['',Validators.required],
      adresse: ['',Validators.required],
      lieuNaissance: ['',Validators.required]
  });

  this.piecesForm = this.formBuilder.group({
       });

  this.educationalDetails = this.formBuilder.group({
      highestQualification: ['', Validators.required],
      university: ['', Validators.required],
      totalMarks: ['',Validators.required]
  });
  if (this.offre.id) {
      this.offreDocumentService.queryByOffr(this.offre.id).subscribe(res =>{
    this.listOffreDocuments = res.body ||[]
  })
  }

  }
  setFileData(libellefichier:string | undefined,event: any, field: string, isImage: boolean): void {
   const fichie:IFichier = new Fichier();
   fichie.nomFichier = libellefichier+'_'+this.candidat.nom+'_'+this.candidat.prenom+'_'+this.candidat.dateNaissance+'_'+this.candidat.lieuNaissance;
   this.dataUtils.setFileData(event, fichie, field, isImage);
   this.listDocuments.push(fichie)
  console.error(this.listDocuments);
  }

  private createFromForm(): ICandidat {
    return {
      ...new Candidat(),
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value
        ? moment(this.editForm.get(['dateNaissance'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lieuNaissance: this.editForm.get(['lieuNaissance'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
    };}

   personal():any { return this.createFromForm(); }

  address():any { if (this.listDocuments.length === this.listOffreDocuments.length) {
     return this.piecesForm.controls;
  }
   }

   previousState(): void {
    window.history.back();
  }
  next():any{
    this.candidat = this.createFromForm();
    console.error(this.candidat);
    if(this.step===1){
         // this.personal_step = true;
         this.personalStep = true
          if (this.editForm.invalid) {

             return
            }
          this.step++
    }

    else if(this.step===2){

      this.candidat = this.createFromForm();
      console.error(this.candidat);
        this.addresSstep = true;
        if (this.piecesForm.invalid) { return }
            this.step++;
    }
    else if(this.step===3){
      this.candidat = this.createFromForm();
        this.addresSstep = true;
        if (this.educationalDetails.invalid) { return }
            this.step++;
    }


  }

save():void{

  this.depot.offre = this.offre;
  const datedepot= new Date();
  this.depot.dateDepot = moment(datedepot);
  this.depotWithCandidat.depot = this.depot;
  this.depotWithCandidat.fichiers = this.listDocuments;
  this.candidatService.create(this.candidat).subscribe(res1=>{

    if (res1.body?.id) {
      this.depot.candidat = res1.body;
      this.depotService.create(this.depot).subscribe(res2=>{
        if (res2.body?.id) {
          const dp=res2.body;

          this.listDocuments.forEach(element => {
            element.depot = dp;
            this.fichierService.create(element).subscribe(res3=>{
             if (res3.body?.id) {
              this.countn++;
             }
            })
          });

        Swal.fire('votre candidature au concours a bien été soumise !!! ');
        this.previousState()

        }
      })


    }
  })
}
  previous():any{
    this.step--

    if(this.step=== 1){
      this.addresSstep = false;
    }
    if(this.step===2){
      this.educationStep = false;
    }

  }
  private createFromFormPiece(): IFichier {
    return {
      ...new Fichier(),
      id: this.editForm.get(['id'])!.value,
      nomFichier: this.editForm.get(['nomFichier'])!.value,
      fichContentType: this.editForm.get(['fichContentType'])!.value,
      fich: this.editForm.get(['fich'])!.value,
      depot: this.editForm.get(['depot'])!.value,
    };
  }
  submit():any{

    if(this.step===3){
      this.educationStep = true;
      if (this.educationalDetails.invalid) { return }
      alert("Well done!!")
    }
  }



}
