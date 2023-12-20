import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthComponent} from "./auth.component";
import {HouseComponent} from "./house/house.component";

const routes: Routes = [
  {path: '', component: AuthComponent,
    children: [
      {path: 'house', component: HouseComponent},
      {path: 'house /:id', component: HouseComponent},
    ]},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {

}
