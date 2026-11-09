import { ParameterOptionForm, ParameterOptionResponse } from "../../parameter-option/component/parameter-option.model";

export interface CreateParameterRequest{

    name: string;

    examType: ExamType;

    inputType: InputType;

    displayOrder: number;

    options: ParameterOptionForm[];

}

export interface UpdateParameterRequest{

    name: string;

    examType: ExamType;

    inputType: InputType;

    displayOrder: number;

    options: ParameterOptionForm[];

}

export interface ParameterResponse{

    id: number;

    name: string;

    examType: ExamType;

    inputType: InputType;

    displayOrder: number;

    createdAt: string;

    updatedAt: string;

    options: ParameterOptionResponse[];

}

export enum ExamType{
    URINALYSIS = "URINALYSIS",
	STOOL = "STOOL"
}

export enum InputType{
    SELECT = "SELECT",
	RADIO = "RADIO",
	TEXT = "TEXT",
	NUMBER = "NUMBER"
}