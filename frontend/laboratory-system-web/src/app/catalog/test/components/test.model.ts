export interface TestForm {
  id?: number;
  name: string;
  testType: TestMethod;
  reference?: string;
  price: number;
  testCategoryId: number;
}

export interface CreateTestRequest {
  name: string;

  group: string;

  testMethod: TestMethod;

  reference?: string;

  price: number;

  testCategoryId: number;
}

export interface UpdateTestRequest {
  name: string;

  group: string;

  testMethod: TestMethod;

  reference?: string;

  price: number;

  testCategoryId: number;
}


export interface TestResponse {
    id: number;

    name: string;

    group: string | null;

    testMethod: TestMethod;

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

export enum TestMethod {
    CUANTITATIVA = 'CUANTITATIVA',
    RAPIDA = 'RAPIDA',
    SEMI_CUANTITATIVA= 'SEMI_CUANTITATIVA',
    AGLUTINACION_PORTA = 'AGLUTINACION_PORTA',
    OTROS = 'OTROS'
    
}
