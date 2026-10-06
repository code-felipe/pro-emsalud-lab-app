export interface CreateTestResultRequest{

    testId: number;
    
    result: string;

    unitPrice: number;
}

export interface UpdateTestResultRequest{

    testId: number;
    
    result: string;

    unitPrice?: number;
}

export interface TestResultResponse{

    id: number;

    testId: number;
    
    testName: string;

    categoryName: string;

    testType: TestType;

    testReference: string;

    orderId: number;

    result: string;

    unitPrice: number;

    createdAt: string;

    updatedAt: string;

}

export interface TestResultSearchResponse{

    message: string;

    testResults: TestResultResponse[]
}

export enum TestType {
    CUANTITATIVA = 'CUANTITATIVA',
    RAPIDA = 'RAPIDA'
}


