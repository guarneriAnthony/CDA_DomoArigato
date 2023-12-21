import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';

import {AuthRoutingModule} from './auth-routing.module';
import {AuthComponent} from './auth.component';
import { HouseComponent } from './house/house.component';
import {CardComponent} from "./card/card.component";
import { SettingComponent } from './setting/setting.component';
import { BrandComponent } from './brand/brand.component';


@NgModule({
  declarations: [
    AuthComponent,
    HouseComponent,
    CardComponent,
    SettingComponent,
    BrandComponent,
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    NgOptimizedImage,
  ],
  bootstrap: [AuthComponent]
})
export class AuthModule {
}
