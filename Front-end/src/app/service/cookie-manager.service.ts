import { Injectable } from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {User} from "../interface/user/user";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class CookieManagerService {

  constructor(private cookieService: CookieService, private authService: AuthService) { }

  logIn= (user : User) => {
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + 1);

    this.cookieService.set('user', JSON.stringify(user.userInfo), {
      expires: expirationDate,
      path: '/',
      domain: 'localhost',
      sameSite: 'Strict'
    });
    this.cookieService.set('authToken', user.token, {
      expires: expirationDate,
      path: '/',
      sameSite: 'Strict'
    });
  }

  logOut = () => {
    this.cookieService.delete('user');
    this.cookieService.delete('authToken');
  }

  isLogged = (): Boolean => {
    return !!this.cookieService.get('authToken');
  }

  getToken = (): string => {
    // si mon cookies est bientôt expiré ( moins de une heure ) j'appel "this.authService.refreshToken();" pour rafraichir le token

    return this.cookieService.get('authToken');
  }
}
