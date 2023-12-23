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
isMenuCollapsed = true;
  constructor(private cookies: CookieManagerService, private service: AuthService) {
  }
    logout() {
    this.cookies.logOut()
  }
}
