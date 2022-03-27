import {Quiz} from "./quiz";

export class User {
  id!: number;
  username!: string;
  password!: string;
  role!: string;
  quizzes: Quiz[] = [];
}
