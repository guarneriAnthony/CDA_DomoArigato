import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { UserAuthentification } from "../interface/userAuthentification";
import { FormsModule } from "@angular/forms";
import {NgIf} from "@angular/common";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {AuthService} from "../service/auth.service";

@Component({
  selector: 'app-accueil',
  standalone: true,
  imports: [FormsModule, NgIf, RouterLink,],
  templateUrl: './accueil.component.html',
  styleUrl: './accueil.component.css',
  encapsulation: ViewEncapsulation.None
})
export class AccueilComponent implements OnInit {
  sign: boolean = true;

  constructor(private route : ActivatedRoute, private service: AuthService) {}

  ngOnInit() {
    this.sign = this.route.snapshot.url[0].path === "sign-in";
  }

  logoPath: String = "../../assets/logo.png";
  catchPhrase: string = "\"Unlock the Future of Smart Living with Our Universal Domotic Solution\""
  signInCatchPhrase: string = "\"Welcome back! Log in to your account and continue your seamless experience with us.\""
  signUpCatchPhrase: string = "\"Join our community and unlock a world of possibilities! Create your account and start your journey with us.\""

  user: UserAuthentification = {
    email: '',
    password: '',
    username: ''
  }

  signIn() {
    this.service.signIn(this.user)
      .then( res => {
        console.log(res)
      }
    )
  }

  signUp() {
    this.service.signUp(this.user)
      .then( res => {
        this.user = res
        console.log(this.user)
      }
    )
  }
}
