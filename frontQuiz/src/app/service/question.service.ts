import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {RequestTable} from "../model/request-table";
import {ResponseTable} from "../model/response-table";
import {Question} from "../model/question";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private api = environment.api + '/question';
  constructor(private http: HttpClient) { }

  public update(update: Question): Observable<Question> {
  return this.http.put<Question>(`${this.api}/update`, update);
}


public save(save: Question): Observable<Question> {
  return this.http.post<Question>(`${this.api}/save`, save);
}

public findById(id: number): Observable<Question> {
  return this.http.get<Question>(`${this.api}/get/${id}`)
}


public findAll(): Observable<Question[]> {
  return this.http.get<Question[]>(`${this.api}/all`)
}

public findAllTableQuestion(requestTable: RequestTable): Observable<ResponseTable<Question>> {
  return this.http.post<ResponseTable<Question>>(`${this.api}/get/table`, requestTable);
}


public deleteById(id: number): Observable<boolean> {
  return this.http.delete<boolean>(`${this.api}/delete/${id}`);
}

  public getAllQuestionWithoutInQuiz(quizId: string): Observable<Question[]> {
  return this.http.get<Question[]>(`${this.api}/get_questin_in_quiz/${quizId}`)
}
}

