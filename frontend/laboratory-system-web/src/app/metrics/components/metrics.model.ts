export interface DashboardSummaryResponse{

    totalSales: number;

    currentTotalSales: number;

    totalTestResults: number;

    totalPatients: number;

}


export interface TestResultMonthlyCountProjection{

    month: number;

    monthName: string;

    sale: number;

    total: number;
}

export interface TopRequestedTestProjection{

    testId: number;

    testName: string;

    total: number;

    totalSales: number
}