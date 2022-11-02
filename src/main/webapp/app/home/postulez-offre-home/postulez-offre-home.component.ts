import { Component, OnInit } from '@angular/core';
import Stepper from 'bs-stepper';

@Component({
  selector: 'jhi-postulez-offre-home',
  templateUrl: './postulez-offre-home.component.html',
  styleUrls: ['./postulez-offre-home.component.scss']
})
export class PostulezOffreHomeComponent implements OnInit {
  name = 'Angular';
  private stepper!: Stepper;
  constructor() { }

  ngOnInit(): void {
    const stepperElement = document.querySelector('#stepper1');

    if(stepperElement !== null){
    this.stepper = new Stepper(stepperElement , {
          linear: false,
          animation: true
        })
    }
  }


  next():void {
    this.stepper.next();
  }

  onSubmit():any {
    return false;
  }


}
