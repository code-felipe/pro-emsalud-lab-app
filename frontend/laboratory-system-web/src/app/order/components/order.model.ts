import { TestMethod } from "../../catalog/test/components/test.model";
import { Title } from "../../doctor/components/doctor.model";

export interface CreateOrderRequest{

    doctorId: number;
}

export interface UpdateOrderRequest{

    doctorId: number;
}

export enum OrderStatus{
    PENDING = 'PENDING',
    IN_PROGRESS = 'IN_PROGRESS',
    COMPLETED = 'COMPLETED'
}

export interface OrderResponse{

    id: number;

    status: OrderStatus;

    patientId: number;

    doctorId: number;

    doctorName: string;

    doctorFullName: string;

    totalOrder: number;

    doctorTitle: Title;

    createdAt: string;

}

export interface PendingOrderNotificationResponse{
    
    patientId: number;

    patientFullName: string;

    pendingOrdersCount: number;

}

export interface TestResultReportResponse {
    id: number;

    testName: string;

    testGroup: string;

    testMethod: TestMethod;

    result: string;

    reference: string;
}

export interface OrderReportResponse {
  id: number;

  patientId: number;

  patientFullName: string;

  patientPhoneNumber: string;

  patientAge: string;

  doctorFullName: string;

  doctorTitle: string;

  createdAt: string;

  results: TestResultReportResponse[];
}


