
import { ExamField } from "../../../../../../utils/exam-field/exam-field.component";
import { TorchIgG, TorchIgM } from "../../components/torch.model";

export const TORCH_IGG_FIELDS: ExamField<keyof TorchIgG>[] = [

  {
    name: 'toxoplasma',
    label: 'Toxoplasmosis',
    type: 'radio',
    options: ['POSITIVO', 'NEGATIVO'],
    section: 'TORCH IgG',
    order: 1
  },

  {
    name: 'cytomegalovirus',
    label: 'Citomegalovirus (CMV)',
    type: 'radio',
    options: ['POSITIVO', 'NEGATIVO'],
    section: 'TORCH IgG',
    order: 2
  },

  {
    name: 'rubella',
    label: 'Rubéola',
    type: 'radio',
    options: ['POSITIVO', 'NEGATIVO'],
    section: 'TORCH IgG',
    order: 3
  },

  {
    name: 'herpes',
    label: 'Virus del Herpes Simple (HSV-1/HSV-2)',
    type: 'radio',
    options: ['POSITIVO', 'NEGATIVO'],
    section: 'TORCH IgG',
    order: 4
  }

];


export const TORCH_IGM_FIELDS: ExamField<keyof TorchIgM>[] = [

  {
    name: 'toxoplasma',
    label: 'Toxoplasmosis',
    type: 'radio',
    options: ['POSITIVO', 'NEGATIVO'],
    section: 'TORCH IgM',
    order: 1
  },

  {
    name: 'cytomegalovirus',
    label: 'Citomegalovirus (CMV)',
    type: 'radio',
    options: ['POSITIVO', 'NEGATIVO'],
    section: 'TORCH IgM',
    order: 2
  },

  {
    name: 'rubella',
    label: 'Rubéola',
    type: 'radio',
    options: ['POSITIVO', 'NEGATIVO'],
    section: 'TORCH IgM',
    order: 3
  },

  {
    name: 'herpes',
    label: 'Virus del Herpes Simple (HSV-1/HSV-2)',
    type: 'radio',
    options: ['POSITIVO', 'NEGATIVO'],
    section: 'TORCH IgM',
    order: 4
  }

];
