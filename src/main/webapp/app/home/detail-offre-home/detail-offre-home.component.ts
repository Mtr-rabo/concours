import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IOffre } from 'app/shared/model/offre.model';

@Component({
  selector: 'jhi-detail-offre-home',
  templateUrl: './detail-offre-home.component.html',
  styleUrls: ['./detail-offre-home.component.scss']
})
export class DetailOffreHomeComponent implements OnInit {

  offre: IOffre = {};

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offre }) => (this.offre = offre));
  }

  previousState(): void {
    window.history.back();
  }
}
