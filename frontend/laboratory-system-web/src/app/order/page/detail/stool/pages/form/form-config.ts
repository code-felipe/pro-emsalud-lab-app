
import { ExamField } from "../../../../../../utils/exam-field/exam-field.component";
import { StoolResult } from "../../component/stool.model";

export const STOOL_FIELDS: ExamField<keyof StoolResult>[] = [

  // =================================================
  // EXAMEN MACROSCÓPICO
  // =================================================

  {
    name: 'color',
    label: 'Color',
    type: 'select',
    options: [
      'MARRON',
      'NEGRO',
      'ROJO',
      'BLANCO',
      'AMARILLO',
      'VERDE'
    ],
    section: 'Examen macroscópico',
    order: 1
  },

  {
    name: 'consistency',
    label: 'Consistencia',
    type: 'select',
    options: [
      'PASTOSO',
      'DURO',
      'DIARREICO',
      'SEMI-DIARREICO'
    ],
    section: 'Examen macroscópico',
    order: 2
  },

  {
    name: 'mucus',
    label: 'Moco',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen macroscópico',
    order: 3
  },

  {
    name: 'pH',
    label: 'pH',
    type: 'radio',
    options: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
    section: 'Examen macroscópico',
    order: 4
  },

  {
    name: 'blood',
    label: 'Sangre',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen macroscópico',
    order: 5
  },

  {
    name: 'foodRemains',
    label: 'Restos alimenticios',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen macroscópico',
    order: 6
  },

  // =================================================
  // EXAMEN MICROSCÓPICO
  // =================================================

  {
    name: 'leukocytes',
    label: 'Leucocitos',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen microscópico',
    order: 7
  },

  {
    name: 'erythrocytes',
    label: 'Eritrocitos',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen microscópico',
    order: 8
  },

  {
    name: 'yeast',
    label: 'Levaduras',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen microscópico',
    order: 9
  },

  {
    name: 'vegetableCells',
    label: 'Células vegetales',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen microscópico',
    order: 10
  },

  {
    name: 'muscleFibers',
    label: 'Fibras musculares',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen microscópico',
    order: 11
  },

  {
    name: 'fats',
    label: 'Grasas',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen microscópico',
    order: 12
  },

  {
    name: 'starches',
    label: 'Almidones',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen microscópico',
    order: 13
  },

  {
    name: 'parasites',
    label: 'Parásitos',
    type: 'text',
    section: 'Examen microscópico',
    order: 14
  },

  {
    name: 'bacteria',
    label: 'Bacterias',
    type: 'radio',
    options: ['NEG', '+', '++', '+++'],
    section: 'Examen microscópico',
    order: 15
  }

];
