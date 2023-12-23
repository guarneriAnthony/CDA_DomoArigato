import { Injectable } from '@angular/core';
import {createAxiosInstance} from "./axios.interceptor";
import {CookieManagerService} from "./cookie-manager.service";

@Injectable({
  providedIn: 'root'
})
export class HueService {

  private axiosInstance;
  constructor(private cookieManagerService: CookieManagerService) {
    this.axiosInstance = createAxiosInstance(cookieManagerService)
  }

  redirectToOAuth = async () => {
        try {
          const response = await this.axiosInstance.get( "/hue/oauth/redirect");
          return response.data;
        } catch (error: any) {
          console.error('Error during OAuth redirection:', error);
          throw new Error('An error occurred during the OAuth redirection attempt: ' + error.message);
        }
      }



}
