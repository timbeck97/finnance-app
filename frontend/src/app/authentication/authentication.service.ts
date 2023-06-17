import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { URL } from '../util/environment';
import { take } from 'rxjs';
import { Token } from './model/token';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http:HttpClient, private router:Router) { }

  login(login:string, password:string){
    const url=URL;
     this.http.post<Token>(url+'/login',{username:login, password:password})
    .pipe(take(1))
    .subscribe(result=>{
      this.setToken(result)
      this.router.navigate(['/finances'])
      
    });
  }
  logout(){
    localStorage.clear();
    this.router.navigate(['/login'])
  }
  getToken(){
    return localStorage.getItem("access_token")
  }
  setToken(token:Token){
    localStorage.setItem('access_token', token.access_token);
    localStorage.setItem('access_token_expiration', String(token.access_token_expiration));
    localStorage.setItem('refresh_token_expiration', String(token.refresh_token_expiration));
    localStorage.setItem('refresh_token', token.refresh_token);
  }
  isLogged(){
    return this.getToken()!==null && this.getToken()!==undefined;
  }
  
}
