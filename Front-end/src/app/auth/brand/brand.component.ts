import { Component } from '@angular/core';
import {HueService} from '../../service/hue.service';

@Component({
  selector: 'app-brand',
  templateUrl: './brand.component.html',
  styleUrl: './brand.component.css'
})
export class BrandComponent {
 isMenuCollapsed = true;
  constructor(private service: HueService) {
   }
 onRedirectToOAuthClick() {
 console.log("onRedirectToOAuthClick")
     this.service.redirectToOAuth().then((response) => {
       window.location.href = response
     })
   }
}
