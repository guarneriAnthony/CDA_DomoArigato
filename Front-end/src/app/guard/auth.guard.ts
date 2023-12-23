import {CanActivateFn} from '@angular/router';
import {CookieManagerService} from "../service/cookie-manager.service";
import {inject} from "@angular/core";

export const authGuard: CanActivateFn = (route, state) => {
  const cookieManagerService = inject(CookieManagerService);
  return !!cookieManagerService.isLogged();

};
