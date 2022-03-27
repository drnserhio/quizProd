import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../service/auth-service.service";
import {User} from "../model/user";
import { Subscription } from 'rxjs';
import {Router} from "@angular/router";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {HeaderType} from "../model/header-type";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private loadingIcon?: boolean;
  private subscription: Subscription[] = [];


  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
  }

  public signIn(user: User): void {
    console.log(user);
    this.loadingIcon = true;
    this.subscription.push(
      this.authService.login(user).subscribe(
        (response: HttpResponse<User>) => {
          console.log(response);
          const token = response.headers.get(HeaderType.JWT_TOKEN);
          this.authService.saveToken(token!);
          this.authService.saveUserToLocalCahe(response.body?.username!);
          this.router.navigateByUrl('/profile');
          this.loadingIcon = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.loadingIcon = false;
          console.log(errorResponse);
        }
      )
    );
  }



}
