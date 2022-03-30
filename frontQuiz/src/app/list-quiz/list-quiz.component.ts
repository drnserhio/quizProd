import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/user.service";
import {Quiz} from "../model/quiz";
import {HttpErrorResponse} from "@angular/common/http";
import {AuthService} from "../service/auth-service.service";
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {QuizService} from "../service/quiz.service";
import {Question} from "../model/question";
import {NotifyService} from "../service/notify-service.service";
import {NotifyType} from "../model/notify-type.enum";
import {AnswersService} from "../service/answers.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-list-quiz',
  templateUrl: './list-quiz.component.html',
  styleUrls: ['./list-quiz.component.css']
})
export class ListQuizComponent implements OnInit {

  profileQuiz?: Quiz[];
  selectQuiz?: Quiz;
  closeResult = '';
  selectQuizQuestion?: Question[];
  currentQuestion?: Question;

  countOfQuestion = 0;
  flagEndTest = true;
  private answerSave?: string;

  constructor(private userService: UserService,
              private authService: AuthService,
              private modalService: NgbModal,
              private quizService: QuizService,
              private answerService: AnswersService,
              private router: Router,
              private notify: NotifyService) { }

  currentUserId!: number;

  ngOnInit(): void {
    this.getFreeQuiz();
  }


  private getFreeQuiz() {
    let userId = parseInt(this.authService.getCurrentUserId());
    this.userService.getFreeQuizByUserId(userId).subscribe(
      (response: Quiz[]) => {
        if (response.length <= 0 ) {
          this.notify.sendNotify(NotifyType.ERROR, "");
          this.router.navigateByUrl("/profile")
        }
        this.profileQuiz = response;
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }

  onSelectModalQuiz(quizId: number) {
    console.log(quizId);
   this.quizService.findById(quizId).subscribe(
      (response: Quiz) => {
        this.selectQuiz = response;
        this.selectQuizQuestion = response.questions;
        this.currentQuestion = this.selectQuizQuestion![this.countOfQuestion];
      },
      (error: HttpErrorResponse) => {
        console.log(error.error.message);
      }
    )
  }

  saveChangeTest() {
    //TODO: add to user quiz
    const userId = parseInt(this.authService.getCurrentUserId());
    this.userService.insertQuizToUserAfterTest(this.selectQuiz?.id!, userId).subscribe(
      (response: boolean) => {
        this.resetTestField();
      },
      (error: HttpErrorResponse) => {
        console.log(error.error.message);
      }
    );
  }

  onDeleteIfClose() {
    let username = this.authService.getUsernameFromLocalCache();
    let qizId = JSON.stringify(this.selectQuiz?.id!);
    let formData = this.answerService.createFormDataForCloseTest(qizId, username);
    console.log(qizId);
    console.log(username);
    this.answerService.deleteAllAnswersIfCloseTest(formData).subscribe(
      (resposne: boolean) => {
       this.countOfQuestion = 0;
          this.modalService.dismissAll();
      },
      (error: HttpErrorResponse) => {
        this.notify.sendNotify(NotifyType.ERROR, error.error.message);
      }
    )

  }

  onNextQuestion() {
    if (this.selectQuiz?.questions.length!-1 > this.countOfQuestion) {
      console.log(this.selectQuiz?.questions.length)
      console.log(this.countOfQuestion)
      this.onCreateAnswerTheQuestion(this.answerSave!, this.currentQuestion!);
      this.currentQuestion = this.selectQuizQuestion![++this.countOfQuestion];
      return;
    }
    if (this.selectQuiz?.questions.length!-1 == this.countOfQuestion) {
      console.log(this.selectQuiz?.questions.length)
      console.log(this.countOfQuestion)
      this.onCreateAnswerTheQuestion(this.answerSave!, this.currentQuestion!);
      this.saveChangeTest();
    }
  }

  onCreateAnswerTheQuestion(answer: string, currentQuestion: Question) {
    //TODO: create answer and save to database
    let username = this.authService.getUsernameFromLocalCache();
    let quizId = JSON.stringify(this.selectQuiz?.id!);
    this.userService.answerTheQuestion(quizId, username, answer, currentQuestion).subscribe(
      (response: boolean) => {
        this.notify.sendNotify(NotifyType.SUCCESS, 'Test passed');
      },
      (error: HttpErrorResponse) => {
        this.notify.sendNotify(NotifyType.WARNING, 'Test failed save');
      }
    )
  }


  open(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
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

  private resetTestField() {
    this.countOfQuestion = 0;
    this.modalService.dismissAll();
    this.selectQuiz = null!;
    this.selectQuizQuestion = null!;
    this.getFreeQuiz();
  }

  onSelectAnswerSave(answerSave: string) {
    this.answerSave = answerSave;
    console.log(answerSave);
  }

}
