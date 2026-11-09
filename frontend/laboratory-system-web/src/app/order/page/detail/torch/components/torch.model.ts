
import { TestMethod } from '../../../../../catalog/test/components/test.model';

export interface TorchIgG {
  toxoplasma: string | null;
  cytomegalovirus: string | null;
  rubella: string | null;
  herpes: string | null;
}

export interface TorchIgM {
  toxoplasma: string | null;
  cytomegalovirus: string | null;
  rubella: string | null;
  herpes: string | null;
}

export interface CreateTorchRequest {
  testId: number;
  torchIgG: TorchIgG;
  torchIgM: TorchIgM;
}

export interface UpdateTorchRequest {
  testId: number;
  torchIgG: TorchIgG;
  torchIgM: TorchIgM;
}

export interface TorchResponse {
  id: number;
  testId: number;
  torchIgG: TorchIgG;
  torchIgM: TorchIgM;
  testName: string;
  categoryName: string;
  testMethod: TestMethod;
  testReference: string;
  orderId: number;
  unitPrice: number;
  createdAt: string;
  updatedAt: string;
}

export interface TorchReportResponse {
  id: number;
  torchIgG: TorchIgG;
  torchIgM: TorchIgM;
  testName: string;
  testMethod: TestMethod;
  reference: string;
}
