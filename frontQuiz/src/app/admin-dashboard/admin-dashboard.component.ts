import {Component, OnInit, TemplateRef} from '@angular/core';
import {UserService} from "../service/user.service";
import {AnswersService} from "../service/answers.service";
import {AdminDashboardService} from "../service/admin-dashboard.service";
import {User} from "../model/user";
import {HttpErrorResponse} from "@angular/common/http";
import {Quiz} from "../model/quiz";
import {NgbActiveModal, NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {Answers} from "../model/answers";

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  users?: User[];
  private selectUsername?: string;
  quizeses?: Quiz[];
  private selectUserId?: number;
  answersByUser?: Answers[];


  constructor(private userService: UserService,
              private answerService: AnswersService,
              private adminDashBoardService: AdminDashboardService,
              private modalService: NgbModal,
              ) { }

  ngOnInit(): void {
    this.allUsers();
  }

  private allUsers() {
    this.adminDashBoardService.getAllUsers().subscribe(
      (response: User[]) => {
        this.users = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    );
  }

  onSelectAllAnswersByQuizIdAndUserId(quizId: number, qui: any) {
    let quId = JSON.stringify(quizId);
    let formData = this.answerService.createFormDataForAnswerTheQuestion(quId, this.selectUsername!);
    this.adminDashBoardService.getAllPassedByUserIdAndQuizId(formData).subscribe(
      (response: Answers[]) => {
        this.answersByUser = response;
        this.openModal(qui);
      },
      (error: HttpErrorResponse) => {
        console.log(error.error.message);
      }
    )
  }

  onSelectUser(userId: number, username: string, openModal: any ) {
    this.selectUsername = username;
    this.userService.getUserByUsername(username!).subscribe(
      (response: User) => {
        this.quizeses = response.quizzes;
        console.log(response)
        this.openModal(openModal);
      },
      (error: HttpErrorResponse) => {
        console.log(error.error.msessage);
      }
    )
  }

  openModal(content: any) {
    this.modalService.open(content, {size: 'lg'}).result
      .then((result) => {
      }, (reason) => {

      });
  }

  onCloseContent() {
  this.modalService.dismissAll();
  }


}
