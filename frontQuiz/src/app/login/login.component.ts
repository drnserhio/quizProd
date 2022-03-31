import {Component, OnInit} from '@angular/core';
import {AuthService} from "../service/auth-service.service";
import {User} from "../model/user";
import {Subscription} from 'rxjs';
import {Router} from "@angular/router";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {HeaderType} from "../model/header-type";
import {NotifyService} from "../service/notify-service.service";
import {NotifyType} from "../model/notify-type.enum";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private loadingIcon?: boolean;
  private subscription: Subscription[] = [];


  constructor(private authService: AuthService,
              private router: Router,
              private notify: NotifyService) { }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()) {
      this.router.navigateByUrl('/profile');
    } else {
      this.router.navigateByUrl('/login');
    }
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
          this.authService.saveUsernameToLocalCahe(response.body?.username!);
          this.authService.saveCurrentUserId(response.body?.id!);
          this.authService.saveRoleAccess(response.body?.role!);
          this.router.navigateByUrl('/profile');
          this.loadingIcon = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.loadingIcon = false;
          this.notify.sendNotify(NotifyType.ERROR, errorResponse.error.message);
          console.log(errorResponse);
        }
      )
    );
  }



}
