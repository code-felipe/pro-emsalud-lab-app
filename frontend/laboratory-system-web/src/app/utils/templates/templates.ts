
export interface TestTemplate {
  name: string;
  description: string;
  content: string;
}

export const TEST_TEMPLATES: TestTemplate[] = [
  {
    name: 'Embarazo',
    description: 'Valores de referencia por semana',
    content: [
      '3a. SEMANA:\t5 - 50',
      '4a. SEMANA:\t5 - 426',
      '5a. SEMANA:\t18 - 7,340',
      '6a. SEMANA:\t1,080 - 56,500',
      '7-8a. SEMANA:\t7,650 - 229,000',
      '9-12a. SEMANA:\t25,700 - 288,000',
      '13-16a. SEMANA:\t13,300 - 254,000',
      '17-24a. SEMANA:\t4,060 - 165,400',
      '25-40a. SEMANA:\t3,640 - 117,000'
    ].join('\n')
  },

];

export const TEST_TEMPLATE_RESULT: TestTemplate[] = [
{
  name: 'Reacción Widal',
  description: 'Titulación e interpretación de Salmonella Typhi O y H',
  content: [
    'SALMONELLA TYPHI(O)\tSALMONELLA TYPHI(H)',
    'Titulación\tInterp\t\tTitulación\tInterp',
    '1/20\t\tPOSITIVO\t1/20\t\tPOSITIVO',
    '1/40\t\tNEGATIVO\t1/40\t\tNEGATIVO',
    '1/80\t\tNEGATIVO\t1/80\t\tNEGATIVO',
    '1/160\t\tNEGATIVO\t1/160\t\tNEGATIVO',
    '1/320\t\tNEGATIVO\t1/320\t\tNEGATIVO\n',
    'Salmonella Typhi "O"\tPOSITIVO\tEN 1/20',
    'Salmonella Typhi "H"\tPOSITIVO\tEN 1/20',
    'Observacion: O y H para Salmonella Typhi,  Resultado Significativo apartir de  1/80'

  ].join('\n')
},
{
    name: 'Uroanalisis',
    description: 'Examen químico y microscópico',
    content: [
      'EXAMEN FISICO',
      'COLOR: Amarillo\tASPECTO: Turbio\tDENSIDAD: 1,030',
      '',
      'EXAMEN QUÍMICO:\t\tEXAMEN MICROSCÓPICO:',
      'PH:\t\t6\tLEUCOCITOS:\t\t4xCampo',
      'LEUCOCITOS:     NEG\tERITROCITOS:\t\tNEG',
      'NITRITO:\tNEG\tCÉLULAS EPITELIALES:\t++',
      'UROBILINÓGENO:\tNEG\tBACTERIAS:\t\t++',
      'PROTEÍNAS:\tNEG\tMOCO:\t\t\t++',
      'GLUCOSA:\tNEG\tCRISTALES: Oxalato de Calcio ++',
      'CETONAS:\tNEG\tLEVADURAS:\t\tNEG',
      'BILIRRUBINAS:\tNEG\tOTROS:',
      'ERITROCITOS:\tNEG'
    ].join('\n')
  },

 {
  name: 'Coproanalisis',
  description: 'Examen macroscópico y microscópico',
  content: [
    'MACROSCÓPICO\t\t\tMICROSCÓPICO',
    'COLOR:\t\tMarrón\tLEUCOCITOS:\t\tNEG',
    'ASPECTO:\tBlando\tERITROCITOS:\t\tNEG',
    'MOCO:\t\tNEG\tLEVADURAS:\t\tNEG',
    'PH:\t\t8\tCÉLULAS VEGETALES:\t++',
    'SANGRE:\t\tNEG\tFIBRAS MUSCULARES:\t+',
    'RESTOS ALIMENT:\tNEG\tGRASAS:\t\t\t+',
    '\t\t\tALMIDONES:\t\t+',
    '\t\t\tPARÁSITOS: No se observan',
    '\t\t\tBACTERIAS:\t\t+++'
  ].join('\n')
}

];
/*
{
  name: 'Reacción Widal',
  description: 'Titulación e interpretación de Salmonella Typhi O y H',
  content: [
    'SALMONELLA TYPHI "O"\t\tSALMONELLA TYPHI "H"',
    'Titulación\tInterpretación\tTitulación\tInterpretación',
    '1/20\t\tPOSITIVO\t1/20\t\tPOSITIVO',
    '1/40\t\tNEGATIVO\t1/40\t\tNEGATIVO',
    '1/80\t\tNEGATIVO\t1/80\t\tNEGATIVO',
    '1/160\t\tNEGATIVO\t1/160\t\tNEGATIVO',
    '1/320\t\tNEGATIVO\t1/320\t\tNEGATIVO'
  ].join('\n')
}
*/

/*
{
    name: 'Orina',
    description: 'Examen químico y microscópico',
    content: [
      'ORINA',
      '',
      'COLOR: Amarillo\tASPECTO: Turbio\tDENSIDAD: 1,030',
      '',
      'EXAMEN QUÍMICO:\t\tEXAMEN MICROSCÓPICO:',
      'PH: 6\t\t\tLEUCOCITOS:         4 X CAMPO',
      'LEUCOCITOS:     NEG\tERITROCITOS:        NEG',
      'NITRITO:\tNEG\tCÉLULAS EPITELIALES: ++',
      'UROBILINÓGENO:\tNEG\tBACTERIAS:\t    ++',
      'PROTEÍNAS:\tNEG\tMOCO:\t\t    ++',
      'GLUCOSA:\tNEG\tCRISTALES:\t    Oxalato de Calcio ++',
      'CETONAS:\tNEG\tLEVADURAS:\t    NEG',
      'BILIRRUBINAS:\tNEG\tOTROS:',
      'ERITROCITOS:\tNEG\t—'
    ].join('\n')
  },
  {
    name: 'Heces',
    description: 'Examen macroscópico y microscópico',
    content: [
      'HECES',
      '',
      'MACROSCÓPICO\t\t\tMICROSCÓPICO',
      'COLOR:\t\t\tMarrón\tLEUCOCITOS:\t\tNEG',
      'ASPECTO:\t\tBlando\tERITROCITOS:\t\tNEG',
      'MOCO:\t\t\tNEG\tLEVADURAS:\t\tNEG',
      'PH:\t\t\t8\tCÉLULAS VEGETALES:\t++',
      'SANGRE:\t\t\tNEG\tFIBRAS MUSCULARES:\t+',
      'RESTOS ALIMENTICIOS:\tNEG\tGRASAS:\t\t\t+',
      '\t\t\t\tALMIDONES:\t\t+',
      '\t\t\t\tPARÁSITOS:\t\tNo se observan',
      '\t\t\t\tBACTERIAS:\t\t+++'
    ].join('\n')
  },
*/