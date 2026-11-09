export interface CreateParameterOptionRequest{

    value: string;

    displayOrder: number;

    parameterId: number;


}

export interface UpdateParameterOptionRequest{

    value: string;

    displayOrder: number;

    parameterId: number;
}

export interface ParameterOptionResponse{

    id: number;

    value: string;

    displayOrder: number;

    createdAt: string;

    updatedAt: string;
}

export interface ParameterOptionForm {
  id?: number;
  value: string;
  displayOrder: number;
}