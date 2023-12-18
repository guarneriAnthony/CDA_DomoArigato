import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UserSignUp} from "../interface/user/userSignUp";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {AuthService} from "../service/auth.service";
import {UserSignIn} from "../interface/user/userSignIn";
import {User} from "../interface/user/user";

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
  errorMessage: string = ''
  logoPath: String = "../../assets/logo.png";
  catchPhrase: string = "\"Unlock the Future of Smart Living with Our Universal Domotic Solution\""
  signInCatchPhrase: string = "\"Welcome back! Log in to your account and continue your seamless experience with us.\""
  signUpCatchPhrase: string = "\"Join our community and unlock a world of possibilities! Create your account and start your journey with us.\""
  userSignUp: UserSignUp = {
    username: '',
    password: '',
    email: '',
    house: ''
  }
  userSignIn: UserSignIn = {
    username: '',
    password: ''
  }
  user: User = {
    userInfo: {
      username: '',
      email: '',
      hasHueAccount: false
    },
    token: ''
  }
  constructor(private route: ActivatedRoute, private service: AuthService, private router: Router) {
  }
  ngOnInit() {
    if (this.service.isLoggedIn()) {
      this.router.navigate(['/auth'])
    }
    this.sign = this.route.snapshot.url[0].path === "sign-in";
  }
  signIn() {
    this.service.signIn(this.userSignIn)
      .then(res => {
        this.user = res
        this.saveUser()
        this.router.navigate(['/auth'])
      })
      .catch(error => {
          this.errorMessage = error.message.startsWith("Error:") ?
            error.message.slice("Error:".length).trim()
            : error.message;
        }
      )
  }
  signUp() {
    this.service.signUp(this.userSignUp)
      .then(res => {
        this.user = res
        this.saveUser()
        this.router.navigate(['/auth'])

      })
      .catch(error => {
          this.errorMessage = error.message.startsWith("Error:") ?
            error.message.slice("Error:".length).trim()
            : error.message;
        }
      )
  }
  logout() {
    this.service.logOut()
  }
  private saveUser() {
    localStorage.setItem('user', JSON.stringify(this.user))
  }
}
