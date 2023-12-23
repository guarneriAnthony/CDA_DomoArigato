import {Routes} from '@angular/router';
import {AccueilComponent} from "./accueil/accueil.component";
import {AuthModule} from "./auth/auth.module";
import {authGuard} from "./guard/auth.guard";

export const routes: Routes = [
  {path: 'sign-in', component: AccueilComponent},
  {path: 'sign-up', component: AccueilComponent},

  {path: 'auth', loadChildren: () => AuthModule, canActivate: [authGuard], runGuardsAndResolvers: 'always'},

  {path: '', redirectTo: 'sign-in', pathMatch: 'full'},
  {path: '**', redirectTo: 'sign-in'}
];
