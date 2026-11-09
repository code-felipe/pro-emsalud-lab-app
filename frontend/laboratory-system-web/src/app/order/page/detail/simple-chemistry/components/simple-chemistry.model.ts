import { TestMethod } from "../../../../../catalog/test/components/test.model";

export interface CreateSimpleChemistryRequest{

    testId: number;
    
    result: string;

    unitPrice: number;
}

export interface UpdateSimpleChemistryRequest{

    testId: number;
    
    result: string;

    unitPrice?: number;
}

export interface SimpleChemistryResponse{

    id: number;

    testId: number;
    
    testName: string;
    
    categoryName: string;

    testMethod: TestMethod;

    testReference: string;

    orderId: number;

    result: string;

    unitPrice: number;

    createdAt: string;

    updatedAt: string;

}

export interface SimpleChemistryReportResponse{
    id: number;

    testName: string;

    testMethod: TestMethod;

    result: string;

    reference: string;
}

export interface SimpleChemistrySearchResponse{

    message: string;

    testResults: SimpleChemistryResponse[]
}
