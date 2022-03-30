import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Bodyid} from "../model/bodyid";
import {environment} from "../../environments/environment";
import {RequestTable} from "../model/request-table";
import {ResponseTable} from "../model/response-table";
import {Quiz} from "../model/quiz";

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  private api = environment.api + '/quiz';
  constructor(private http: HttpClient) { }


  public update(update: Quiz) {
  return this.http.put(`${this.api}/update`, update);
}


public save(save: Quiz): Observable<Quiz> {
  return this.http.post<Quiz>(`${this.api}/save`, save);
}

public findById(id: number): Observable<Quiz> {
  return this.http.get<Quiz>(`${this.api}/get/${id}`)
}

public findAll(): Observable<Quiz[]> {
  return this.http.get<Quiz[]>(`${this.api}/all`)
}

public findAllTable(requestTable: RequestTable): Observable<ResponseTable<Quiz>> {
  return this.http.post<ResponseTable<Quiz>>(`${this.api}/get/table`, requestTable);
}

public deleteById(id: number): Observable<boolean> {
  return this.http.delete<boolean>(`${this.api}/delete/${id}`)
}

public creatQuizWithQuestion(quizId: number, ids: Bodyid): Observable<boolean> {
    return this.http.post<boolean>(`${this.api}/save_question_to_quiz/${quizId}`, ids);
}
}
