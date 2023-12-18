import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AuthRoutingModule} from './auth-routing.module';
import {DashboardComponent} from './dashboard.component';
import { HouseComponent } from './house/house.component';


@NgModule({
  declarations: [
    DashboardComponent,
    HouseComponent,
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
  ]
})
export class AuthModule {
}
