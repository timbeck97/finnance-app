import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { URL } from '../util/environment';
import { take } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http:HttpClient) { }

  login(login:string, password:string){
    const url=URL;
    return this.http.post(url+'/login',{login:login, password:password})
    .pipe(take(1));
  }
}
