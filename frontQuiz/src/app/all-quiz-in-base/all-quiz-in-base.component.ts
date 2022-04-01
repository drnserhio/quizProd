import { Component, OnInit } from '@angular/core';
import {QuizService} from "../service/quiz.service";
import {Quiz} from "../model/quiz";
import {HttpErrorResponse} from "@angular/common/http";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {QuestionService} from "../service/question.service";
import {Question} from "../model/question";
import {Bodyid} from "../model/bodyid";

@Component({
  selector: 'app-all-quiz-in-base',
  templateUrl: './all-quiz-in-base.component.html',
  styleUrls: ['./all-quiz-in-base.component.css']
})
export class AllQuizInBaseComponent implements OnInit {
  quizes?: Quiz[];
  selectQuiz?: Quiz;
  freeQuestion?: Question[];
  arrayQuestionId: number[] = [];

  constructor(private quizService: QuizService,
              private questionService: QuestionService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.allQuiz();
  }

  private allQuiz() {
    this.quizService.findAll().subscribe(
      (response: Quiz[]) => {
        this.quizes = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    );
  }

  onSelectModalQuiz(quizId: number, content: any) {
    this.quizService.findById(quizId).subscribe(
      (response: Quiz) => {
        this.selectQuiz = response;
        this.openModal(content);
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message)
      }
    )
  }

  openModal(content: any) {
    this.modalService.open(content, {size: 'lg'}).result
      .then((result) => {
      }, (reason) => {
      });
  }

  public searchQuiz(searchQuiz: string): void {
    const res: Quiz[] = [];
    for (const quiz of this?.quizes!) {
      if (quiz.name.toLowerCase().indexOf(searchQuiz.toLowerCase()) !== -1 ||
        quiz.notice.toLowerCase().indexOf(searchQuiz.toLowerCase()) !== -1 ) {
        res.push(quiz);
      }
    }
    this.quizes = res;
    if (res.length === 0 ||
      !searchQuiz) {
      this.allQuiz();
    }
  }

  openQuizAndOpenModal(content: any) {
    let quizId = JSON.stringify(this.selectQuiz?.id);
    this.questionService.getAllQuestionWithoutInQuiz(quizId).subscribe(
      (response: Question[]) => {
        this.freeQuestion = response;
        console.log(response);
        this.openModal(content);
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }

  // addToQuiz(questionId: number) {
  //   this.quizService.creatQuizWithQuestion()
  // }
  addQuestionToArray(questionId: number) {
    this.arrayQuestionId.push(questionId);
    console.log(questionId);
  }


  saveChanges() {
     const body = new Bodyid();
     body.ids = this.arrayQuestionId;
    this.quizService.creatQuizWithQuestion(this.selectQuiz?.id!, body).subscribe(
      (response: boolean) => {
        alert("Save question to quiz successful.")
        this.modalService.dismissAll();
        this.allQuiz();
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }

  onDeleteQuestionInQuiz(questionId: number) {
    this.quizService.deleteFromQuiz(this.selectQuiz?.id!, questionId).subscribe(
      (response: boolean) => {
        alert("Question delete in quiz");
        this.resetSelectQuiz();
      }
    )
  }

  private resetSelectQuiz() {
    this.quizService.findById(this.selectQuiz?.id!).subscribe(
      (response: Quiz) => {
        this.selectQuiz = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message)
      }
    )
  }
}
