import {Quiz} from "./quiz";

export class User {
  username!: string;
  password!: string;
  role!: string;
  quizzes: Quiz[] = [];
}
