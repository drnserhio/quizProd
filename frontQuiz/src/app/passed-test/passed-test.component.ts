import {Component, OnInit, TemplateRef} from '@angular/core';
import {UserService} from "../service/user.service";
import {AuthService} from "../service/auth-service.service";
import {User} from "../model/user";
import {HttpErrorResponse} from "@angular/common/http";
import {NotifyService} from "../service/notify-service.service";
import {NotifyType} from "../model/notify-type.enum";
import {Quiz} from "../model/quiz";
import {AnswersService} from "../service/answers.service";
import {Answers} from "../model/answers";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-passed-test',
  templateUrl: './passed-test.component.html',
  styleUrls: ['./passed-test.component.css']
})
export class PassedTestComponent implements OnInit {
  selectCurrentUserQuizes?: Quiz[];
  answerByQuizTest?: Answers[];

  closeResult = '';

  constructor(private userService: UserService,
              private authService: AuthService,
              private notify: NotifyService,
              private answerService: AnswersService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getCurrentUser();
  }

  private getCurrentUser() {
    let username = this.authService.getUsernameFromLocalCache();
    this.userService.getUserByUsername(username).subscribe(
      (response: User) => {
        this.selectCurrentUserQuizes = response.quizzes;
      },
      (error: HttpErrorResponse) => {
        this.notify.sendNotify(NotifyType.ERROR, error.error.message);
      }
    )
  }

  onShowTest(quizId: number, passed: any) {
    let quizID = JSON.stringify(quizId);
    let username = this.authService.getUsernameFromLocalCache();
    let formData = this.answerService.createFormDataForAnswerTheQuestion(quizID, username);
    this.answerService.getAllPassedTestByQuizId(formData).subscribe(
      (response: Answers[]) => {
        console.log(response);
        this.answerByQuizTest = response;
        this.openModal(passed);
      },
      (error: HttpErrorResponse) => {
        this.notify.sendNotify(NotifyType.ERROR, error.error.message);
      }
    )
  }


  openModal(content: any) {
    this.modalService.open(content, {size: 'lg'}).result
      .then((result) => {

      }, (reason) => {

      });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
}
