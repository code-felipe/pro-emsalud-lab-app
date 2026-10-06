import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  success(message: string, title: string = 'Éxito'): void {

    Swal.fire({
      title,
      text: message,
      icon: 'success',
      confirmButtonColor: '#2f6f5e'
    });
  }

  error(message: string, title: string = 'Error'): void {

    Swal.fire({
      title,
      text: message,
      icon: 'error',
      confirmButtonColor: '#b5502f'
    });
  }

  warning(message: string, title: string = 'Advertencia'): void {

    Swal.fire({
      title,
      text: message,
      icon: 'warning',
      confirmButtonColor: '#9c6b14'
    });
  }

  info(message: string, title: string = 'Información'): void {

    Swal.fire({
      title,
      text: message,
      icon: 'info',
      confirmButtonColor: '#2f6f5e'
    });
  }

     confirmSaveDelete(itemName: string): Promise<boolean> {

    return Swal.fire({
      title: '¿Eliminar elemento?',

      html: `
        <p>
        Esta acción eliminará permanentemente
        <strong>${itemName}</strong>
        y al parecer no esta siendo usando en ningun paciente y/o sus resultados.
        </p>

        <p>
        Esta información no podrá recuperarse posteriormente.
        </p>


        <p class="mb-2">
          Escribe <strong>ELIMINAR</strong> para confirmar:
        </p>

        <input
          id="delete-confirmation"
          class="swal2-input"
          placeholder="ELIMINAR"
          autocomplete="off"
        >
      `,

      icon: 'warning',

      showCancelButton: true,

      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',

      confirmButtonColor: '#b5502f',
      cancelButtonColor: '#5b6660',

      preConfirm: () => {

        const input = (
          document.getElementById(
            'delete-confirmation'
          ) as HTMLInputElement
        )?.value;

        if (input !== 'ELIMINAR') {

          Swal.showValidationMessage(
            'Debes escribir exactamente ELIMINAR'
          );

          return false;
        }

        return true;
      }

    }).then(result => result.isConfirmed);
  }

   confirmDelete(itemName: string): Promise<boolean> {

    return Swal.fire({
      title: '¿Eliminar elemento?',

      html: `
        <p>
        <span>&#x26A0;</span>
        Esta acción eliminará permanentemente el item
        '<strong>${itemName}</strong>'
        siempre y cuando no este siendo utilizado en algun paciente
        y/o sus resultados. Cerciorese antes de eliminar, de lo contrario
        no podra eliminar este item.
        </p>

        <p>
        Esta información no podrá recuperarse posteriormente.
        </p>


        <p class="mb-2">
          Escribe <strong>ELIMINAR</strong> para confirmar:
        </p>

        <input
          id="delete-confirmation"
          class="swal2-input"
          placeholder="ELIMINAR"
          autocomplete="off"
        >
      `,

      icon: 'warning',

      showCancelButton: true,

      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',

      confirmButtonColor: '#b5502f',
      cancelButtonColor: '#5b6660',

      preConfirm: () => {

        const input = (
          document.getElementById(
            'delete-confirmation'
          ) as HTMLInputElement
        )?.value;

        if (input !== 'ELIMINAR') {

          Swal.showValidationMessage(
            'Debes escribir exactamente ELIMINAR'
          );

          return false;
        }

        return true;
      }

    }).then(result => result.isConfirmed);
  }
}
