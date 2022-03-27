import {Question} from "./question";

export class Quiz {

  name!: string;
  dateStart!: Date;
  dateEnd!: Date;
  notice!: string;
  countQuestion!: number;
  questions: Question[] = [];

}
