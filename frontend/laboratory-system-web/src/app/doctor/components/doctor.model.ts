export enum Title {
  Dr = 'Dr',
  Dra = 'Dra',
  EA = 'EA',
  EP = 'EP',
  NA = 'NA'
}

export interface CreateDoctorRequest{

    firstName: string;

    lastName: string;

    title: Title;

    
}

export interface UpdateDoctorRequest{
    
    firstName: string;

    lastName: string;

    title: Title;
}

export interface DoctorResponse {
  id: number;
  firstName: string;
  lastName: string;
  fullName: string;
  title: Title;
  createdAt: string;
  updatedAt: string;
}

export interface DoctorSearchResponse{

    message: string;

    doctors: DoctorResponse[]
}
