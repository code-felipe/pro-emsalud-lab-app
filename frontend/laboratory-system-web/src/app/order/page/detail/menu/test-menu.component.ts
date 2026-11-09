
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

interface TestMenuItem {
  code: string;
  name: string;
  description: string;
  icon: string;
  route: string;
}

@Component({
  selector: 'app-test-menu',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './test-menu.component.html',
  styleUrl: '/test-menu.component.css'
})
export class TestMenuComponent implements OnInit {

  patientId!: number;
  orderId!: number;

  hasError = false;

  tests: TestMenuItem[] = [
    {
      code: 'SIMPLE_CHEMISTRY',
      name: 'Químicas simples',
      description: 'Resultados y valores de referencia',
      icon: 'bi bi-droplet',
      route: 'simple-chemistry'
    },
    {
      code: 'WIDAL',
      name: 'Widal',
      description: 'Aglutinaciones y diluciones',
      icon: 'bi bi-grid-3x3',
      route: 'widal'
    },
    {
      code: 'TORCH',
      name: 'Panel TORCH',
      description: 'Anticuerpos IgG e IgM',
      icon: 'bi bi-clipboard2-pulse',
      route: 'torch'
    },
    {
      code: 'STOOL',
      name: 'Heces',
      description: 'Examen macroscópico y microscópico',
      icon: 'bi bi-search',
      route: 'stool'
    },
    {
      code: 'URINALYSIS',
      name: 'Orina',
      description: 'Examen físico, químico y microscópico',
      icon: 'bi bi-moisture',
      route: 'urinalysis'
    },
    {
      code: 'BETA_HCG',
      name: 'Beta-hCG',
      description: 'Resultado de subunidad beta-hCG',
      icon: 'bi bi-eyedropper',
      route: 'beta-hcg'
    }
  ];

  constructor(
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {

    const patientId = Number(
      this.activatedRoute.parent?.snapshot.paramMap.get('id')
    );

    const orderId = Number(
      this.activatedRoute.snapshot.paramMap.get('orderId')
    );

    if (
      !Number.isInteger(patientId) ||
      patientId <= 0 ||
      !Number.isInteger(orderId) ||
      orderId <= 0
    ) {
      this.hasError = true;
      console.error('Invalid patientId or orderId');
      return;
    }

    this.patientId = patientId;
    this.orderId = orderId;
  }
}
