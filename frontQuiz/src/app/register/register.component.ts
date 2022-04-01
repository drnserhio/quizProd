import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../model/user";
import {HttpErrorResponse} from "@angular/common/http";
import {Subscription} from "rxjs";
import {AuthService} from "../service/auth-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {

  private loadingIcon?: boolean;
  private subscription: Subscription[] = [];

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
  }


  ngOnDestroy(): void {
    this.subscription.forEach(sub => sub.unsubscribe());
  }

  public registration(user: User): void {
    this.loadingIcon = true;
    this.subscription.push(
      this.authService.register(user).subscribe(
        (response: User) => {
          this.loadingIcon = false;
          this.router.navigateByUrl("/login");
        },
        (errorResponse: HttpErrorResponse) => {
          alert(errorResponse.error.message);
          this.loadingIcon = false;
        }
      )
    );
  }
}
