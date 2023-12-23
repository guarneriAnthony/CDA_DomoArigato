import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthComponent} from "./auth.component";
import {HouseComponent} from "./house/house.component";
import {RoomComponent} from "./room/room.component";
import {LightComponent} from "./light/light.component";

const routes: Routes = [
  {path: '', component: AuthComponent,
    children: [
      {path: 'houses', component: HouseComponent},
      {path: 'house/:houseId/rooms', component: RoomComponent},
      {path: 'house/room/:roomId/lights', component: LightComponent},
    ]},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {

}
