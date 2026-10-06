import { Injectable, signal } from '@angular/core';

export type ModalType = 'filter' | 'form' | 'report';

export interface ModalConfig {
  type: ModalType;
  title: string;
}

@Injectable({
  providedIn: 'root',
})
export class ModalService {

  private _modalOpen = signal(false);

  private _modalConfig = signal<ModalConfig | null>(null);

  readonly modalOpen = this._modalOpen.asReadonly();
  readonly modalConfig = this._modalConfig.asReadonly();

  openModal(config: ModalConfig): void {
    this._modalConfig.set(config);
    this._modalOpen.set(true);
  }

  closeModal(): void {
    this._modalOpen.set(false);
    this._modalConfig.set(null);
  }

}
