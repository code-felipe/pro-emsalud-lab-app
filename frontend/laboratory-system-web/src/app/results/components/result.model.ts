import { TestMethod } from "../../catalog/test/components/test.model";

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

    testGroup: string;

    categoryName: string;

    testMethod: TestMethod;

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


