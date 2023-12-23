import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthComponent} from "./auth.component";
import {HouseComponent} from "./house/house.component";
import {RoomComponent} from "./room/room.component";
import {LightComponent} from "./light/light.component";
import {SettingComponent} from './setting/setting.component';
import {BrandComponent} from "./brand/brand.component";

const routes: Routes = [
  {path: '', component: AuthComponent,
    children: [
      {path: 'houses', component: HouseComponent},
      {path: 'house/:houseId/rooms', component: RoomComponent},
      {path: 'house/room/:roomId/lights', component: LightComponent},
      { path: 'brand', component: BrandComponent },
      {path: 'setting', component: SettingComponent },
    ]},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {

}
