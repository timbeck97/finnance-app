import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { URL } from '../util/environment';
import { take } from 'rxjs';
import { Token } from './model/token';
import { Router } from '@angular/router';
import { User } from './model/User';
import { UserComplete } from './model/UserComplete';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  user: UserComplete;
  constructor(private http: HttpClient, private router: Router) {
    if (localStorage.getItem('user')) {
      this.user = JSON.parse(localStorage.getItem('user') || '{}');
    }
  }
  createUser(user: UserComplete) {
    const url = URL;
    this.http.post(url + '/signin', user)
      .pipe(take(1))
      .subscribe(result => {
        this.login(user.username, user.password);
      });
  }
  login(login: string, password: string) {
    const url = URL;
    this.http.post<Token>(url + '/login', { username: login, password: password })
      .pipe(take(1))
      .subscribe(result => {
        this.setToken(result)
        this.loadUser(true);
      });
  }
  loadUser(redirecionar: boolean = false) {
    const url = URL;
    this.http.get<UserComplete>(url + '/users/me')
      .pipe(take(1))
      .subscribe(result => {
        this.user = result;
        console.log(this.user);
        
        this.setUser(result);
        if (redirecionar) {
          this.router.navigate(['/finances'])
        }
      })
  }
  refreshToken() {
    const url = URL;
    let refreshToken = this.getRefreshToken();
    this.http.post<Token>(url + '/token/refreshtoken', { refreshToken: refreshToken  })
      .pipe(take(1))
      .subscribe(result => {
        this.setTokenRefresh(result)
      }, error => {
        console.log(error)
      });
  }
  alterarSenha(dto:any){
    const url = URL;
    this.http.post(url + '/users/changePassword', dto)
      .pipe(take(1))
      .subscribe(result => {
        alert('Senha alterada com sucesso')
      });
  }
  validadeTokenExpiration() {
    let tokenExpiration = Number(this.getTokenExpiration());
    let now = new Date().getTime();
    return now > tokenExpiration;
  }
  validateRefreshTokenExpiration() {
    let tokenExpiration = Number(this.getRefreshTokenExpiration());
    let now = new Date().getTime();
    console.log();

    return now > tokenExpiration;
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login'])
  }
  getRefreshToken() {
    return localStorage.getItem("refresh_token")
  }
  getToken() {
    return localStorage.getItem("access_token")
  }
  getTokenExpiration() {
    return localStorage.getItem("access_token_expiration")
  }
  getRefreshTokenExpiration() {
    return localStorage.getItem("refresh_token_expiration")
  }
  setUser(user: UserComplete) {
    localStorage.setItem('user', JSON.stringify(user));
  }
  setToken(token: Token) {
    localStorage.setItem('access_token', token.access_token);
    localStorage.setItem('access_token_expiration', String(token.access_token_expiration));
    localStorage.setItem('refresh_token_expiration', String(token.refresh_token_expiration));
    localStorage.setItem('refresh_token', token.refresh_token);
  }
  setTokenRefresh(token: Token) {
    localStorage.clear();
    localStorage.setItem('access_token', token.access_token);
    localStorage.setItem('access_token_expiration', String(token.access_token_expiration));
  }
  isLogged() {
    return this.getToken() !== null && this.getToken() !== undefined;
  }
  getUser() {
    if(!this.user){
      this.loadUser();
    }
    return this.user;
  }

}
