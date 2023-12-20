import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';

import {AuthRoutingModule} from './auth-routing.module';
import {AuthComponent} from './auth.component';
import { HouseComponent } from './house/house.component';
import {CardComponent} from "./card/card.component";


@NgModule({
  declarations: [
    AuthComponent,
    HouseComponent,
    CardComponent,
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
