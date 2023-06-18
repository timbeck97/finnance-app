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
  refreshToken(){
    const url=URL;
    let refreshToken=this.getRefreshToken();
    this.http.get<Token>(url+'/token/refreshtoken',{headers:{'Authorization':'Bearer '+refreshToken}})
    .pipe(take(1))
    .subscribe(result=>{
      this.setTokenRefresh(result)
      //this.router.navigate(['/finances'])
      
    },error=>{
      console.log(error)
    });
  }
  validadeTokenExpiration(){
    let tokenExpiration=Number(this.getTokenExpiration());
    let now=new Date().getTime();
    return now>tokenExpiration;
  }
 
  logout(){
    localStorage.clear();
    this.router.navigate(['/login'])
  }
  getRefreshToken(){
    return localStorage.getItem("refresh_token")
  }
  getToken(){
    return localStorage.getItem("access_token")
  }
  getTokenExpiration(){
    return localStorage.getItem("access_token_expiration")
  }
  setToken(token:Token){
    localStorage.setItem('access_token', token.access_token);
    localStorage.setItem('access_token_expiration', String(token.access_token_expiration));
    localStorage.setItem('refresh_token_expiration', String(token.refresh_token_expiration));
    localStorage.setItem('refresh_token', token.refresh_token);
  }
  setTokenRefresh(token:Token){
    localStorage.clear();
    localStorage.setItem('access_token', token.access_token);
    localStorage.setItem('access_token_expiration', String(token.access_token_expiration));
  }
  isLogged(){
    return this.getToken()!==null && this.getToken()!==undefined;
  }
  
}
