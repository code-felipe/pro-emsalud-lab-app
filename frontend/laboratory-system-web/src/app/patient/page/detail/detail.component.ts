import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { Gender, PatientResponse } from '../../components/patient.model';
import { PatientService } from '../../components/patient.service';

@Component({
  selector: 'app-patient-detail',
  standalone: true,
  imports: [
    RouterLink, FormsModule,
    CommonModule,
    RouterLinkActive,
    RouterOutlet
  ],
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.css',
})
export class PatientDetailComponent implements OnInit  {

    patient: PatientResponse | undefined;

    title: string =  "Detalle del Paciente";

    pictureSelected!: File;

    hasError = false;

    genderLabels: Record<Gender, string> = {
      [Gender.FEMALE]: 'Femenino',
      [Gender.MALE]: 'Masculino'
    };


  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService
    // public modalService: ModalService//later if i want modal
  ){}

  ngOnInit(){
    this.loadTest();
  }

loadTest():void{
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.patientService.getPatient(id).subscribe({
        next: (response) => {
            this.patient = response;
            this.hasError = false;
        },
        error: (error) => {
            this.hasError = true;
            Swal.fire('Error', error.error.message ?? 'El id no existe', 'error');
        }
    })
}
//   uploadPicture(){

//     if(!this.pictureSelected){
//       Swal.fire('Error Upload: ', 'You should select a picture', 'error');
//     }else{

//     this.cabinetService.uploadPicture(this.pictureSelected, this.cabinet.id! )
//     .subscribe(response => {
//       this.cabinet = response.cabinet;
//       console.log(response)

//       //Emit the client with the new picture to update it to the cabinet list
//       this.modalService.notificationUpload.emit(this.cabinet);

//       Swal.fire('Image uploaded successfully', response.message, 'success')
//     })
//     }
//   }

//   selectedPicture(event: any): void{
//     this.pictureSelected = event.target.files[0];

//     if(this.pictureSelected.type.indexOf('image') < 0){
//       Swal.fire('Error on selecting image', 'The file should be image type', 'error')
//     }
//     console.log(this.pictureSelected);
//   }

//   closeModal(){
//     this.modalService.closeModal();
    
//   }
}
