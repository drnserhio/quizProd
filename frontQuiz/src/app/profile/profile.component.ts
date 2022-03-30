import { Component, OnInit } from '@angular/core';
import {AuthService} from "../service/auth-service.service";
import {UserService} from "../service/user.service";
import {User} from "../model/user";
import {HttpErrorResponse} from "@angular/common/http";
import {Quiz} from "../model/quiz";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profile?: User;
  profileQuiz?: Quiz[];

  constructor(private authService: AuthService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.getProfile();

  }

  private getProfile() {
    let username = this.authService.getUsernameFromLocalCache();
    this.userService.getUserByUsername(username).subscribe(
      (response: User) => {
        console.log(response);
        this.profile = response;
        if (this.profile) {
          this.getFreeQuiz();
        }
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }

  private getFreeQuiz() {
    const idUser = this.profile?.id;
    console.log(idUser);
    this.userService.getFreeQuizByUserId(idUser!).subscribe(
      (response: Quiz[]) => {
        this.profileQuiz = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }
}
