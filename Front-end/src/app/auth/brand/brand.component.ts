import { Component } from '@angular/core';
import {AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-brand',
  templateUrl: './brand.component.html',
  styleUrl: './brand.component.css'
})
export class BrandComponent {
 isMenuCollapsed = true;
  constructor(private service: AuthService) {
   }
 onRedirectToOAuthClick() {
     this.service.redirectToOAuth();
   }
}
