import { Injectable } from '@angular/core';
import {Subject, Observable} from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class CancelrequestService {
  cancelPendingRequest: Subject<void>=new Subject<void>();
  constructor() { }

  public cancelPendingRequests() {
    this.cancelPendingRequest.next()
  }

  public onCancelPendingRequests() {
    return this.cancelPendingRequest.asObservable()
  }
}
