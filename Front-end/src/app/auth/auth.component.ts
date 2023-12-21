import {Component} from '@angular/core';
import {AuthService} from "../service/auth.service";
import {CookieService} from "ngx-cookie-service";
import {CookieManagerService} from "../service/cookie-manager.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {
  constructor(private cookies: CookieManagerService) {
  }

  logout() {
    this.cookies.logOut()
  }
}
