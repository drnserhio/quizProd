import { Component, OnInit } from '@angular/core';
import {QuizService} from "../service/quiz.service";
import {Quiz} from "../model/quiz";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-create-quiz',
  templateUrl: './create-quiz.component.html',
  styleUrls: ['./create-quiz.component.css']
})
export class CreateQuizComponent implements OnInit {

  constructor(private quizService: QuizService) { }

  ngOnInit(): void {
  }


  onSaveQuiz(quiz: Quiz) {
    this.quizService.save(quiz).subscribe(
      (response: Quiz) => {
        alert("Quiz succesful create.")
        this.clearFormField();
      },
      (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    )
  }

  private clearFormField() {
    // @ts-ignore
    document.getElementById('name').value ='';
    // @ts-ignore
    document.getElementById('notice').value ='';
  }
}
