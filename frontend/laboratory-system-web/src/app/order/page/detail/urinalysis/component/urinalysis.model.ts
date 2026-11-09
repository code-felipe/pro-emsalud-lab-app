import { TestMethod } from "../../../../../catalog/test/components/test.model";

export interface UrinalysisResult{
     // EXAMEN FISICO
  color: string;
  appearance: string;
  specificGravity: string;

  // EXAMEN QUIMICO
  pH: string;
  leukocytes: string;
  nitrites: string;
  urobilinogen: string;
  protein: string;
  glucose: string;
  ketones: string;
  bilirubin: string;
  erythrocytes: string;

  // EXAMEN MICROSCOPICO
  microscopicLeukocytes: string;
  microscopicErythrocytes: string;
  epithelialCells: string;
  bacteria: string;
  mucus: string;
  crystals: string;
  yeast: string;
  others: string;

  // OBSERVACIONES
  observation?: string;
}
export interface CreateUrinalysisRequest{

    testId: number;

    urinalysisResult: UrinalysisResult;

}

export interface UpdateUrinalysisRequest{

    testId: number;

    urinalysisResult: UrinalysisResult;
}

export interface UrinalysisResponse{

    id: number;

    testId: number;

    urinalysisResult: UrinalysisResult;

    testName: string;

    categoryName: string;

    testMethod: TestMethod;

    testReference: string;

    orderId: number;

    unitPrice: number;

    createdAt: string;

    updatedAt: string;

}

export interface UrinalysisReportResponse{

    id: number;

    urinalysisResult: UrinalysisResult;

    testName: string;

    testMethod: TestMethod;

    reference: string;

}