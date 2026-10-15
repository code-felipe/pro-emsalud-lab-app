export interface CreateUserRequest {
  
  username: string;

  password: string;

  enabled: boolean;

  roleIds: number[];
}

export interface UpdateUserRequest{
    
    id: number;

    username: string;

    enabled: boolean;

    roleIds: number[];
}

export interface UserResponse{

    id: number;
    
    username: string;

    enabled: boolean;

    createdAt: string;

    updatedAt: string

    picture: string | null;

    roles: RoleResponse[];
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