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
    let token = this.cookieService.get('authToken');
    if (this.lessOneHourLeft(token)){
      this.authService.refreshToken(token).then(user => {
        this.logIn(user)
        token = this.cookieService.get('authToken');
      })
    }
    return token
  }

  getUser = (): User => {
    const user = this.cookieService.get('user');
    return JSON.parse(user||'{}')
  }

  lessOneHourLeft = (token: string): Boolean => {
    const tokenPayload = JSON.parse(atob(token.split('.')[1]))

    const expirationDate = new Date(tokenPayload.exp * 1000)

    const currentDate = new Date()
    const timeDifference = expirationDate.getTime() - currentDate.getTime()
    const remainingTime = timeDifference / (1000 * 60 * 60)

    return remainingTime < 1;
  }
}
