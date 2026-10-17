import { UserResponse } from "../user/components/user.model";

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  user: UserResponse;
  mensaje: string;
}