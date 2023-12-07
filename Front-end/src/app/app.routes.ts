import { Routes } from '@angular/router';
import {AccueilComponent} from "./accueil/accueil.component";
import {AuthModule} from "./auth/auth.module";

export const routes: Routes = [
  {path: 'sign-in', component : AccueilComponent},
  {path: 'sign-up', component : AccueilComponent},
  {path: 'auth', loadChildren: () => AuthModule},

  { path: 'auth/**', redirectTo: 'auth'},
  { path: '**', redirectTo: 'sign-in'},
];
