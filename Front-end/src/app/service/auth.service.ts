import { Injectable } from '@angular/core';
import axios from "axios";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = "http://localhost:8080"
  constructor() { }
  signIn = async (user: any) => {
    try {
      const response = await axios.post(this.apiUrl + "/authorize", user);

      return response.data;
    } catch (error) {
      throw new Error('Une erreur s\'est produite lors de la tentative de connexion : ' + error);
    }
  }
  signUp = async (user: any) => {
    try {
      const response = await axios.post(this.apiUrl + "/register", user);

      return response.data;
    } catch (error) {
      throw new Error('Une erreur s\'est produite lors de la tentative de création de compte : ' + error);
    }
  }

}
