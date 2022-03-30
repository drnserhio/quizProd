import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {User} from "../model/user";
import { Quiz } from '../model/quiz';
import {Answers} from "../model/answers";

@Injectable({
  providedIn: 'root'
})
export class AdminDashboardService {

  private api = environment.api + '/admin';
  constructor(private http: HttpClient) { }

  public getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.api}/all_users`);
  }

  public getAllUsersTable(requestTable: any) {
  return this.http.post(`${this.api}/all_user_table`, requestTable);
}

public getFreeQuizByUserId(userId: number): Observable<Quiz[]> {
  return this.http.get<Quiz[]>(`${this.api}/get/all_quiz/by/${userId}`)
}

public onSelectQuiz(quizId: number): Observable<Quiz> {
    return this.http.get<Quiz>(`${this.api}/select/quiz/${quizId}`);
}

public getAllPassedByUserIdAndQuizId(formData: FormData): Observable<Answers[]> {
  return this.http.post<Answers[]>(`${this.api}/all_test_passed/by/quiz_and_user`, formData);
}

}
