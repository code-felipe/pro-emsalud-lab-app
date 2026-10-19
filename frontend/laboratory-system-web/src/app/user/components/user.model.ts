// Modelo del formulario: roleId puede ser null mientras no se elige un rol
export interface UserForm {
  id?: number;
  username: string;
  password: string;
  roleId: number | null;
}

export interface CreateUserRequest {

  username: string;

  password: string;

  roleId: number;
}

export interface UpdateUserRequest {

  id: number;

  username: string;

  password?: string;

  roleId: number;
}

export interface UserResponse {

  id: number;

  username: string;

  createdAt: string;

  updatedAt: string;

  picture: string | null;

  role: RoleResponse;
}

export interface UploadPictureResponse {

  message: string;

  user: UserResponse;
}

export interface CreateRole {

  authority: string;
}

export interface UpdatedteRole {

  id: number;

  authority: string;
}

export interface RoleResponse {

  id: number;

  authority: string;
}