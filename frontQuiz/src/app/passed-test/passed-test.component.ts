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
import {Router} from "@angular/router";

@Component({
  selector: 'app-passed-test',
  templateUrl: './passed-test.component.html',
  styleUrls: ['./passed-test.component.css']
})
export class PassedTestComponent implements OnInit {

  selectCurrentUserQuizes?: Quiz[];
  answerByQuizTest?: Answers[];
  flagRoleAccess = true;


  constructor(private userService: UserService,
              private authService: AuthService,
              private notify: NotifyService,
              private answerService: AnswersService,
              private router: Router,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getCurrentUser();
  }

  private getCurrentUser() {
    let username = this.authService.getUsernameFromLocalCache();
    this.userService.getUserByUsername(username).subscribe(
      (response: User) => {
        this.selectCurrentUserQuizes = response.quizzes;
        if (response.quizzes.length <= 0) {
          this.router.navigateByUrl('/profile')
          alert('You don\'t have passed test.')
        }
        this.checkRole();
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

  private checkRole() {
    if (this.authService.getRoleAccess().endsWith('ADMIN')) {
      this.flagRoleAccess = false;
    }
  }

  public searchQuiz(searchQuiz: string): void {
    const res: Quiz[] = [];
    for (const quiz of this?.selectCurrentUserQuizes!) {
      if (quiz.name.toLowerCase().indexOf(searchQuiz.toLowerCase()) !== -1 ||
        quiz.notice.toLowerCase().indexOf(searchQuiz.toLowerCase()) !== -1 ) {
        res.push(quiz);
      }
    }
    this.selectCurrentUserQuizes = res;
    if (res.length === 0 ||
      !searchQuiz) {
      this.getCurrentUser();
    }
  }
}
