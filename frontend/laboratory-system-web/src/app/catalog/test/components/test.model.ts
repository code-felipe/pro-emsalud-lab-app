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

  testMethods: TestMethod[];

  reference?: string;

  price: number;

  testCategoryId: number;
}

export interface UpdateTestRequest {
  name: string;

  testMethods: TestMethod[];

  reference?: string;

  price: number;

  testCategoryId: number;
}


export interface TestResponse {
    id: number;

    name: string;

    testMethods: TestMethod[];

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
  SEMI_CUANTITATIVA = 'SEMI_CUANTITATIVA',
  AGLUTINACION_PORTA = 'AGLUTINACION_PORTA',
  OTROS = 'OTROS',
  PHYSICAL = 'PHYSICAL',
  CHEMISTRY = 'CHEMISTRY',
  MICROSCOPY = 'MICROSCOPY'
    
}

export const TEST_METHOD_LABELS: Record<TestMethod, string> = {
  [TestMethod.CUANTITATIVA]: 'Cuantitativa',
  [TestMethod.RAPIDA]: 'Rápida',
  [TestMethod.SEMI_CUANTITATIVA]: 'Semi-cuantitativa',
  [TestMethod.AGLUTINACION_PORTA]: 'Aglutinación en porta',
  [TestMethod.OTROS]: 'Otros',
  [TestMethod.PHYSICAL]: 'Físico',
  [TestMethod.CHEMISTRY]: 'Químico',
  [TestMethod.MICROSCOPY]: 'Microscopía'
};

export const TEST_METHOD_OPTIONS = Object.values(TestMethod).map(value => ({
  value,
  label: TEST_METHOD_LABELS[value]
}));
