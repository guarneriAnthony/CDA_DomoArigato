import {CanActivateFn} from '@angular/router';
import {AuthService} from "../service/auth.service";

export const authGuard: CanActivateFn = (route, state) => {

  const authService = new AuthService();
  return !!authService.isLoggedIn();

};
