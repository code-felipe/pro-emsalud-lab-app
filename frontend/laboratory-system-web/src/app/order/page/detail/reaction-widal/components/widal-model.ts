import { TestMethod } from "../../../../../catalog/test/components/test.model";

export enum WidalTitulation {
  EN_1_20 = 'EN_1_20',
  EN_1_40 = 'EN_1_40',
  EN_1_80 = 'EN_1_80',
  EN_1_160 = 'EN_1_160',
  EN_1_320 = 'EN_1_320'
}

export interface WidalResult {
  salmonellaTyphiO_1_20: boolean;
  salmonellaTyphiO_1_40: boolean;
  salmonellaTyphiO_1_80: boolean;
  salmonellaTyphiO_1_160: boolean;
  salmonellaTyphiO_1_320: boolean;

  salmonellaTyphiH_1_20: boolean;
  salmonellaTyphiH_1_40: boolean;
  salmonellaTyphiH_1_80: boolean;
  salmonellaTyphiH_1_160: boolean;
  salmonellaTyphiH_1_320: boolean;

  salmonellaTyphiOResult: boolean;
  salmonellaTyphiHResult: boolean;

  salmonellaTyphiOResultTitulation: WidalTitulation;
  salmonellaTyphiHResultTitulation: WidalTitulation;

  observation: string;
}

export interface CreateWidalReactionRequest {
  unitPrice: number;
  testId: number;
  widalResult: WidalResult;
}

export interface UpdateWidalReactionRequest {
  unitPrice: number;
  testId: number;
  widalResult: WidalResult;
}

export interface WidalReactionResponse {
  id: number;
  widalResult: WidalResult;
  testName: string;
  categoryName: string;
  testMethod: TestMethod;
  testReference: string;
  orderId: number;
  unitPrice: number;
  createdAt: string;
  updatedAt: string;
  testId: number;
}

export interface WidalReactionReportResponse {
  id: number;
  widalResult: WidalResult;
  testName: string;
  testMethod: TestMethod;
  reference: string;
  
}
