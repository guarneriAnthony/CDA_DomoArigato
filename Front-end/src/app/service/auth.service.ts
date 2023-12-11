import { Injectable } from '@angular/core';
import axios from "axios";
import {UserSignUp} from "../interface/user/userSignUp";
import {UserSignIn} from "../interface/user/userSignIn";
import {User} from "../interface/user/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = "http://localhost:8080"
  constructor() { }
  isLoggedIn = () : Boolean => {
    const storedUser : User = JSON.parse(localStorage.getItem('user') || '{}');
    return !!storedUser.token;
  }
  signIn = async (userSignIn: UserSignIn) => {
    try {
      const response = await axios.post(this.apiUrl + "/authorize", userSignIn);
      const user: User = {
        userInfo: {
          username: response.data.user.username,
          email: response.data.user.email,
          hasHueAccount: response.data.user.hasHueAccount,
        },
        token: response.data.token,
      };
      return user;
    } catch (error: any) {
      if (error.response && error.response.status === 401) {
        throw new Error('Username, email, or password is incorrect. Please try again.');
      } else {
        throw new Error('An error occurred during the login attempt: ' + error.message);
      }
    }
  }
  signUp = async (userSignUp: UserSignUp) => {
    try {
      const response = await axios.post(this.apiUrl + "/register", userSignUp);
      const user: User = {
        userInfo: {
          username: response.data.user.username,
          email: response.data.user.email,
          hasHueAccount: response.data.user.hasHueAccount,
        },
        token: response.data.token,
      };
      return user;
    } catch (error: any) {
      if (error.response && error.response.status === 409) {
        throw new Error('An account with this email/username already exists. Please choose another.');
      } else {
        throw new Error('An error occurred while attempting to create an account: ' + error.message);
      }
    }
  }
  logOut= ()  => {
    localStorage.removeItem('user');
  }
}
