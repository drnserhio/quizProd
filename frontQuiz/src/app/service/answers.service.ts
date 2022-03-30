import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Answers} from "../model/answers";

@Injectable({
  providedIn: 'root'
})
export class AnswersService {

  private api = environment.api + '/answers';

  constructor(private http: HttpClient) {
  }


  public getAllPassedTestByQuizId(formData: FormData): Observable<Answers[]> {
    return this.http.post<Answers[]>(`${this.api}/get_passed_test`, formData)
  }

  public createFormDataForAnswerTheQuestion(quizId: string,username: string) {
    const formData = new FormData();
    formData.append("quizId", quizId)
    formData.append("username", username)
    return formData;
  }
}
