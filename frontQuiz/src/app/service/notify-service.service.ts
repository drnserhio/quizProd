import {Injectable} from '@angular/core';
import {NotifierService} from 'angular-notifier';
import {NotifyType} from "../model/notify-type.enum";


@Injectable({
  providedIn: 'root'
})
export class NotifyService {

  constructor(public notify: NotifierService) { }

  public sendNotify(typeNotify: NotifyType, message: string) {
    this.notify.notify(typeNotify, message);
  }
}
