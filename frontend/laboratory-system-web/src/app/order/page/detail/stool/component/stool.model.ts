import { TestMethod } from '../../../../../catalog/test/components/test.model';

export interface StoolResult {
  // EXAMEN MACROSCÓPICO
  color: string;
  consistency: string;
  mucus: string;
  pH: string;
  blood: string;
  foodRemains: string;

  // EXAMEN MICROSCÓPICO
  leukocytes: string;
  erythrocytes: string;
  yeast: string;
  vegetableCells: string;
  muscleFibers: string;
  fats: string;
  starches: string;
  parasites: string;
  bacteria: string;

  // OBSERVACIONES
  observation?: string;
}

export interface CreateStoolRequest {
  testId: number;
  stoolResult: StoolResult;
}

export interface UpdateStoolRequest {
  testId: number;
  stoolResult: StoolResult;
}

export interface StoolResponse {
  id: number;
  testId: number;
  stoolResult: StoolResult;
  testName: string;
  categoryName: string;
  testMethod: TestMethod;
  testReference: string;
  orderId: number;
  unitPrice: number;
  createdAt: string;
  updatedAt: string;
}

export interface StoolReportResponse {
  id: number;
  stoolResult: StoolResult;
  testName: string;
  testMethod: TestMethod;
  reference: string;
}