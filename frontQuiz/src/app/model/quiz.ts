import {Question} from "./question";

export class Quiz {

  id!: number;
  name!: string;
  notice!: string;
  questions: Question[] = [];

}
