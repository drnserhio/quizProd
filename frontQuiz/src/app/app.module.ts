import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {AppRoutingModule} from "./app-routing.module";
import {RegisterComponent} from './register/register.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ProfileComponent} from './profile/profile.component';
import {ListQuizComponent} from './list-quiz/list-quiz.component';
import {AuthService} from "./service/auth-service.service";
import {UserService} from "./service/user.service";
import {AuthInterceptor} from "./interceptor/auth-interceptor";
import {NotifierModule, NotifierService} from "angular-notifier";
import {AuthGuard} from "./guard/auth-guard.guard";
import {NgbActiveModal, NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {PassedTestComponent} from './passed-test/passed-test.component';
import {AdminDashboardComponent} from './admin-dashboard/admin-dashboard.component';
import {CreateQuestionComponent} from './create-question/create-question.component';
import {ListQuestionComponent} from './list-question/list-question.component';
import {CreateQuizComponent} from './create-quiz/create-quiz.component';
import {AllQuizInBaseComponent} from './all-quiz-in-base/all-quiz-in-base.component';
import {NotificationModule} from "./notification.module";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    ListQuizComponent,
    PassedTestComponent,
    AdminDashboardComponent,
    CreateQuestionComponent,
    ListQuestionComponent,
    CreateQuizComponent,
    AllQuizInBaseComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NotificationModule,
    NgbModule,
  ],
  providers: [AuthService, UserService, NotifierService, AuthGuard, NgbActiveModal,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
