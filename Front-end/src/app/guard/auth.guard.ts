import { CanActivateFn } from '@angular/router';
import {AuthService} from "../service/auth.service";

export const authGuard: CanActivateFn = (route, state) => {

  const authService = new AuthService(); // Instancier votre service d'authentification
  const isLoggedIn = authService.isLoggedIn(); // Utiliser la fonction isLoggedIn de votre service
  console.log(isLoggedIn)
  return !!isLoggedIn;

};
