import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { URL } from '../util/environment';
import { take } from 'rxjs';
import { Token } from './model/token';
import { Router } from '@angular/router';
import { User } from './model/User';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  user:User;
  constructor(private http:HttpClient, private router:Router) {
    if(localStorage.getItem('user')){
      this.user=JSON.parse(localStorage.getItem('user')||'{}');
    }
   }

  login(login:string, password:string){
    const url=URL;
     this.http.post<Token>(url+'/login',{username:login, password:password})
    .pipe(take(1))
    .subscribe(result=>{
      this.setToken(result)
      //this.router.navigate(['/finances'])
      this.loadUser(true);
    });
  }
  loadUser(redirecionar:boolean=false){
    const url=URL;
     this.http.get<User>(url+'/users/me')
      .pipe(take(1))
      .subscribe(result=>{
        this.user=result;
        this.setUser(result);
        if(redirecionar){
          this.router.navigate(['/finances'])
        }
      })
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
  validateRefreshTokenExpiration(){
    let tokenExpiration=Number(this.getRefreshTokenExpiration());
    let now=new Date().getTime();
    console.log();
    
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
  getRefreshTokenExpiration(){
    return localStorage.getItem("refresh_token_expiration")
  }
  setUser(user:User){
    localStorage.setItem('user', JSON.stringify(user));
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
  getUser(){
    return this.user;
  }
  
}
