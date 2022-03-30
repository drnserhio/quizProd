import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {AppRoutingModule} from "./app-routing.module";
import { RegisterComponent } from './register/register.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { ProfileComponent } from './profile/profile.component';
import { ListQuizComponent } from './list-quiz/list-quiz.component';
import {AuthService} from "./service/auth-service.service";
import {UserService} from "./service/user.service";
import {AuthInterceptor} from "./interceptor/auth-interceptor";
import {NotifierModule, NotifierService} from "angular-notifier";
import {AuthGuard} from "./guard/auth-guard.guard";
import {NotificationModule} from "./notification.module";
import {NgbActiveModal, NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { PassedTestComponent } from './passed-test/passed-test.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    ListQuizComponent,
    PassedTestComponent,
    AdminDashboardComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NotifierModule,
    NgbModule,
  ],
  providers: [AuthService, UserService, NotifierService, AuthGuard, NgbActiveModal,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
