import { Component, OnInit } from '@angular/core';
import {Question} from "../model/question";
import {QuestionService} from "../service/question.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.css']
})
export class CreateQuestionComponent implements OnInit {

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
  }

  onSaveQuestion(question: Question) {
    console.log(question);
    this.questionService.save(question).subscribe(
      (response: Question) => {
        alert("Question successful create");
        this.clearFormField();
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }

  private clearFormField() {
    // @ts-ignore
    document.getElementById('question').value ='';
    // @ts-ignore
    document.getElementById('answerSuccessful').value ='';
    // @ts-ignore
    document.getElementById('answerBadlyFirst').value ='';
    // @ts-ignore
    document.getElementById('answerBadlySecond').value ='';
  }
}
