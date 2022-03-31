import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ProfileComponent} from "./profile/profile.component";
import {ListQuizComponent} from "./list-quiz/list-quiz.component";
import {AuthGuard} from "./guard/auth-guard.guard";
import {PassedTestComponent} from "./passed-test/passed-test.component";
import {AdminDashboardComponent} from "./admin-dashboard/admin-dashboard.component";
import {CreateQuestionComponent} from "./create-question/create-question.component";
import {ListQuestionComponent} from "./list-question/list-question.component";
import {CreateQuizComponent} from "./create-quiz/create-quiz.component";
import {AllQuizInBaseComponent} from "./all-quiz-in-base/all-quiz-in-base.component";


const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'list_quiz', component: ListQuizComponent, canActivate: [AuthGuard]},
  {path: 'passed_tests', component: PassedTestComponent, canActivate: [AuthGuard]},
  {path: 'admin_dashboard', component: AdminDashboardComponent, canActivate:[AuthGuard]},
  {path: 'create_question', component: CreateQuestionComponent, canActivate:[AuthGuard]},
  {path: 'list_question', component: ListQuestionComponent, canActivate:[AuthGuard]},
  {path: 'create_quiz', component: CreateQuizComponent, canActivate:[AuthGuard]},
  {path: 'quiz_all_list', component: AllQuizInBaseComponent, canActivate:[AuthGuard]},






];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
