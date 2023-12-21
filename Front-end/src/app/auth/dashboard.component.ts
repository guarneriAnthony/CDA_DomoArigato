import {Component} from '@angular/core';
import {AuthService} from "../service/auth.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  constructor(private service: AuthService) {
  }

  logout() {
    this.service.logOut()
  }
}
