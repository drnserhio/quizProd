import {Component, OnInit} from '@angular/core';
import {QuestionService} from "../service/question.service";
import {Question} from "../model/question";
import {HttpErrorResponse} from "@angular/common/http";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Quiz} from "../model/quiz";

@Component({
  selector: 'app-list-question',
  templateUrl: './list-question.component.html',
  styleUrls: ['./list-question.component.css']
})
export class ListQuestionComponent implements OnInit {
  questions?: Question[];
  selectQuestion?: Question;

  constructor(private questionService: QuestionService,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.allQuestion();
  }

  private allQuestion() {
    this.questionService.findAll().subscribe(
      (response: Question[]) => {
        this.questions = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }

  onChangeQuestion(quest: Question, content: any) {
    this.selectQuestion = quest;
    this.openModal(content);
  }

  openModal(content: any) {
    this.modalService.open(content, {size: 'lg'}).result
      .then((result) => {
      }, (reason) => {

      });
  }

  onSaveChangeQuestion(question: Question) {
    console.log(question);
    this.questionService.update(question).subscribe(
      (response: Question) => {
        alert("Success update");
        this.modalService.dismissAll();
        this.allQuestion();
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )

  }

  public searchQuestion(searchQuiz: string): void {
    const res: Question[] = [];
    for (const quest of this?.questions!) {
      if (quest.question.toLowerCase().indexOf(searchQuiz.toLowerCase()) !== -1 ||
          quest.id.toString().toLowerCase().indexOf(searchQuiz.toLowerCase()) !== -1 ||
          quest.answer.toLowerCase().indexOf(searchQuiz.toLowerCase()) !== -1) {
        res.push(quest);
      }
    }
    this.questions = res;
    if (res.length === 0 ||
      !searchQuiz) {
      this.allQuestion();
    }
  }

  onDeleteQuestion(questionId: number) {
    this.questionService.deleteById(questionId).subscribe(
      (response: boolean) => {
        alert("Question success delete.")
        this.allQuestion();
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }
}
