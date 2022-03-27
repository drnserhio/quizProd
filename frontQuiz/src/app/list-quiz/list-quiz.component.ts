import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user.service";
import {Quiz} from "../model/quiz";
import {HttpErrorResponse} from "@angular/common/http";
import {AuthService} from "../service/auth-service.service";
import {User} from "../model/user";

@Component({
  selector: 'app-list-quiz',
  templateUrl: './list-quiz.component.html',
  styleUrls: ['./list-quiz.component.css']
})
export class ListQuizComponent implements OnInit {

   profileQuiz?: Quiz[];

  constructor(private userService: UserService,
              private authService: AuthService) { }

  currentUserId!: number;

  ngOnInit(): void {
    this.getCurrentUserId();
  }

  private getCurrentUserId() {
    const username = this.authService.getUsernameFromLocalCache();
    this.userService.getUserByUsername(username).subscribe(
      (response: User) => {
        this.currentUserId = response?.id;
        this.getFreeQuiz();
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }

  private getFreeQuiz() {
    this.userService.getFreeQuizByUserId(this.currentUserId!).subscribe(
      (response: Quiz[]) => {
        this.profileQuiz = response;
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }

}
