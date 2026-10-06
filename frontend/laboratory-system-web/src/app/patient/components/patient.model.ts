
export interface CreatePatientRequest {
  gender: Gender;
  firstName: string;
  middleName: string;
  fatherLastName: string;
  motherLastName: string;
  dateOfBirth: string; // LocalDate → "YYYY-MM-DD"
  primaryPhoneNumber: string;
  optionalPhoneNumber?: string;
  city: string;
}

export interface PatientResponse {
  id: number;
  code: string;
  gender: Gender;
  firstName: string;
  middleName: string;
  fatherLastName: string;
  motherLastName: string;
  dateOfBirth: string; // LocalDate → "YYYY-MM-DD"
  age: string;
  primaryPhoneNumber: string;
  optionalPhoneNumber?: string;
  city: string;
  createdAt: string; // Instant → ISO 8601
  updatedAt: string; // Instant → ISO 8601
}

export interface UpdatePatientRequest {
  gender: Gender;
  firstName: string;
  middleName: string;
  fatherLastName: string;
  motherLastName: string;
  dateOfBirth: string;
  primaryPhoneNumber: string;
  optionalPhoneNumber?: string;
  city: string;
}

export enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}