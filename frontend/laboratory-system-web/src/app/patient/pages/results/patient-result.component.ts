import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { OrderComponentList } from "../../../order/page/list/order-list.component";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-patient-results',
  standalone: true,

  imports: [
    CommonModule,
    OrderComponentList
  ],

  templateUrl: './patient-result.component.html'
})
export class PatientResultsComponent {

  patientId!: number;

  constructor(
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {

    this.patientId = Number(
      this.activatedRoute.parent?.snapshot.paramMap.get('id')
    );
        console.log('PatientResults patientId:', this.patientId);

  }
}
