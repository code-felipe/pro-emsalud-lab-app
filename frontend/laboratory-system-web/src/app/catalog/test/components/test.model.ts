export interface TestForm {
  id?: number;
  name: string;
  testType: TestType;
  reference?: string;
  price: number;
  testCategoryId: number;
}

export interface CreateTestRequest {
  name: string;
  testType: TestType;
  reference?: string;
  price: number;
  testCategoryId: number;
}

export interface UpdateTestRequest {
  name: string;
  testType: TestType;
  reference?: string;
  price: number;
  testCategoryId: number;
}


export interface TestResponse {
    id: number;
    name: string;
    testType: TestType;
    reference: string | null;   // el backend puede devolver null explícito
    price: number;
    active: boolean;
    createdAt: string;   // Instant de Java llega como string ISO
    updatedAt: string;
    testCategoryId: number;
    testCategoryName: string;
}

export interface TestSearchResponse{
  
  message: string;
  
  tests: TestResponse[]
}

export enum TestType {
    CUANTITATIVA = 'CUANTITATIVA',
    RAPIDA = 'RAPIDA',
    SEMI_CUANTITATIVA= 'SEMI_CUANTITATIVA',
    OTROS = 'OTROS'
    
}
