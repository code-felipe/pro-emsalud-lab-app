export interface CreateTestCategoryRequest{
    name: string;
}

export interface UpdateTestCategoryRequest{
    name?: string;
}

export interface TestCategoryResponse{
    id: number;

    name: string;

    active: boolean;
    
    createdAt: string;   // Instant de Java llega como string ISO

    updatedAt: string;

    testCategoryId: number;

    testCategoryName: string;
}