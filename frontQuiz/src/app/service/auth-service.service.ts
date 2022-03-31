import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Observable} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  host: string = environment.api;
  private token!: string;
  private usernameLogeedIn!: string;
  private jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient) { }

  public login(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.host}/user/login`, user, { observe: 'response'});
  }

  public register(user: User): Observable<User> {
    return this.http.post<User>(`${this.host}/user/register`, user);
  }

  public logOut(): void {
    this.token = null!;
    this.usernameLogeedIn = null!;
    localStorage.removeItem('userName');
    localStorage.removeItem('token');
    localStorage.removeItem('currentId');

  }

  public saveToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public saveUsernameToLocalCahe(userName: string): void {
    localStorage.setItem('userName', userName);
  }

  public saveCurrentUserId(userId: number): void {
   localStorage.setItem('currentId', JSON.stringify(userId));
}
  public saveRoleAccess(role: string): void {
    localStorage.setItem('accessRole', role);
  }
  public getRoleAccess(): string {
   return localStorage.getItem('accessRole')!;
  }

  public getCurrentUserId(): string {
    return localStorage.getItem('currentId')!;
  }

  public getUsernameFromLocalCache(): string {
    return localStorage.getItem('userName')!;
  }

  public loadToken(): void {
    this.token = localStorage.getItem('token')!;
  }

  public getToken(): string {
    return this.token;
  }

  // @ts-ignore
  public isUserLoggedIn(): boolean {
    this.loadToken();
    if (this.token != null && this.token !== '') {
      if (this.jwtHelper.decodeToken(this.token).sub != null || '') {
        if (this.jwtHelper.getTokenExpirationDate(this.token)) {
          this.usernameLogeedIn = this.jwtHelper.decodeToken(this.token).sub;
          return true;
        }
      }
    } else {
      this.logOut();
      return false;
    }
  }
}
