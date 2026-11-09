import { ExamField } from "../../../../../../utils/exam-field/exam-field.component";
import { UrinalysisResult } from "../../component/urinalysis.model";

export const URINALYSIS_FIELDS: ExamField<keyof UrinalysisResult>[] = [

  // EXAMEN FISICO

  {
    name: 'color',
    label: 'Color',
    type: 'select',
    options: [
      'AMARILLO',
      'AMARILLO CLARO',
      'AMBAR',
      'ROJO',
      'NARANJA',
      'MARRON',
      'INCOLORO'
    ],
    section: 'Examen físico',
    order: 1
  },

  {
    name: 'appearance',
    label: 'Aspecto',
    type: 'select',
    options: [
      'TURBIO',
      'LIGERAMENTE TURBIO',
      'LIMPIDO'
    ],
    section: 'Examen físico',
    order: 2
  },

  {
    name: 'specificGravity',
    label: 'Densidad',
    type: 'select',
    options: [
      '1,000',
      '1,005',
      '1,010',
      '1,015',
      '1,020',
      '1,025',
      '1,030'
    ],
    section: 'Examen físico',
    order: 3
  },

  // EXAMEN QUIMICO

  {
    name: 'pH',
    label: 'pH',
    type: 'radio',
    options: [
      '5',
      '6',
      '6.5',
      '7',
      '7.5',
      '8',
      '9'
    ],
    section: 'Examen químico',
    order: 4
  },

  {
    name: 'leukocytes',
    label: 'Leucocitos',
    type: 'radio',
    options: [
      'NEG',
      '+',
      '++',
      '+++'
    ],
    section: 'Examen químico',
    order: 5
  },

  {
    name: 'nitrites',
    label: 'Nitrito',
    type: 'radio',
    options: [
      'POSITIVO',
      'NEGATIVO'
    ],
    section: 'Examen químico',
    order: 6
  },

  {
    name: 'urobilinogen',
    label: 'Urobilinógeno',
    type: 'radio',
    options: [
      'NEG',
      '+',
      '++',
      '+++'
    ],
    section: 'Examen químico',
    order: 7
  },

  {
    name: 'protein',
    label: 'Proteínas',
    type: 'text',
    section: 'Examen químico',
    order: 8
  },

  {
    name: 'glucose',
    label: 'Glucosa',
    type: 'radio',
    options: [
      'NEG',
      '+',
      '++',
      '+++'
    ],
    section: 'Examen químico',
    order: 9
  },

  {
    name: 'ketones',
    label: 'Cetonas',
    type: 'text',
    section: 'Examen químico',
    order: 10
  },

  {
    name: 'bilirubin',
    label: 'Bilirrubinas',
    type: 'text',
    section: 'Examen químico',
    order: 11
  },

  {
    name: 'erythrocytes',
    label: 'Eritrocitos',
    type: 'radio',
    options: [
      'NEG',
      '+',
      '++',
      '+++'
    ],
    section: 'Examen químico',
    order: 12
  },
  // EXAMEN MICROSCOPICO
  {
    name: 'microscopicLeukocytes',
    label: 'Leucocitos',
    type: 'text',
    section: 'Examen microscópico',
    order: 13
  },

  {
    name: 'microscopicErythrocytes',
    label: 'Eritrocitos',
    type: 'text',
    section: 'Examen microscópico',
    order: 14
  },

  {
    name: 'epithelialCells',
    label: 'Células epiteliales',
    type: 'radio',
    options: [
      'NEG',
      '+',
      '++',
      '+++'
    ],
    section: 'Examen microscópico',
    order: 15
  },

  {
    name: 'bacteria',
    label: 'Bacterias',
    type: 'radio',
    options: [
      'NEG',
      '+',
      '++',
      '+++'
    ],
    section: 'Examen microscópico',
    order: 16
  },

  {
    name: 'mucus',
    label: 'Moco',
    type: 'radio',
    options: [
      'NEG',
      '+',
      '++',
      '+++'
    ],
    section: 'Examen microscópico',
    order: 17
  },

  {
    name: 'crystals',
    label: 'Cristales',
    type: 'text',
    section: 'Examen microscópico',
    order: 18
  },

  {
    name: 'yeast',
    label: 'Levaduras',
    type: 'radio',
    options: [
      'NEG',
      '+',
      '++',
      '+++'
    ],
    section: 'Examen microscópico',
    order: 19
  },

  {
    name: 'others',
    label: 'Otros',
    type: 'text',
    section: 'Examen microscópico',
    order: 20
  }

];
