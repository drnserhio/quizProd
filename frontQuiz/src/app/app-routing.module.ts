import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ProfileComponent} from "./profile/profile.component";
import {ListQuizComponent} from "./list-quiz/list-quiz.component";
import {AuthGuard} from "./guard/auth-guard.guard";
import {PassedTestComponent} from "./passed-test/passed-test.component";
import {AdminDashboardComponent} from "./admin-dashboard/admin-dashboard.component";


const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'list_quiz', component: ListQuizComponent, canActivate: [AuthGuard]},
  {path: 'passed_tests', component: PassedTestComponent, canActivate: [AuthGuard]},
  {path: 'admin_dashboard', component: AdminDashboardComponent, canActivate:[AuthGuard]},



];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
