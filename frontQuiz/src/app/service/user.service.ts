import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Question} from "../model/question";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {environment} from "../../environments/environment";
import {RequestTable} from "../model/request-table";
import {ResponseTable} from "../model/response-table";
import {Quiz} from "../model/quiz";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private api = environment.api + '/user';

  constructor(private http: HttpClient) {
  }


  public update(update: User): Observable<User> {
    return this.http.put<User>(`${this.api}/update`, update);
  }

  public save(save: User): Observable<User> {
    return this.http.post<User>(`${this.api}/save`, save);
  }

  public UserfindById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.api}/get/${userId}`);
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(`${this.api}/all`);
  }

  public findAllTable(requestTable: RequestTable): Observable<ResponseTable<User>> {
    return this.http.post<ResponseTable<User>>(`${this.api}/get/table`, requestTable)
  }

  public deleteById(userId: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.api}/delete/${userId}`);
  }


  public getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.api}/get/by/${username}`)
  }

  public getFreeQuizByUserId(userId: number): Observable<Quiz[]> {
    return this.http.get<Quiz[]>(`${this.api}/get/free_quiz/by/${userId}`)
  }

  public onSelectQuiz(quizId: number): Observable<Quiz> {
    return this.http.get<Quiz>(`${this.api}/select/quiz/by/${quizId}`);
  }

  public answerTheQuestion(formData: FormData): Observable<boolean> {
    return this.http.post<boolean>(`${this.api}/answer_question`, formData);
  }

  public createFormDraForAnswerQuestion(quizId: number, username: string, answer: string, question: Question) {
    const formData = new FormData();
    formData.append("quizId", JSON.stringify(quizId));
    formData.append("username", username);
    formData.append("answer", answer);
    formData.append("question", JSON.stringify(question));
    return formData;
  }

}
