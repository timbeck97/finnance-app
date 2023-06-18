import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable, catchError } from 'rxjs';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from 'src/app/util/alert/alert.component';

@Injectable({
  providedIn: 'root'
})
export class InterceptorService implements HttpInterceptor {

  constructor(private auth: AuthenticationService, private router: Router, private modalService: NgbModal) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let isRefreshTokenRequest = req.url.includes('token/refreshtoken') || req.url.includes('login');
    if (isRefreshTokenRequest) {

      return next.handle(req);
    }
    let token = this.auth.getToken();
    if (!token) {
      this.router.navigate(['/login'])
    }
    let tokenExpired = this.auth.validadeTokenExpiration();
    let refreshToken=this.auth.getRefreshToken();
    if(tokenExpired && refreshToken){
      this.auth.refreshToken();
      return EMPTY;
    }

    const authReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + token)
    });
    return next.handle(authReq)
      .pipe(
        catchError((error: any) => {
          if (error.error.customCode == 666) {


            this.modalService.dismissAll();
            const modalRef=this.modalService.open(AlertComponent);
            modalRef.componentInstance.titleText='Sessão expirada';
            modalRef.componentInstance.bodyText='Sua sessão expirou, por favor faça login novamente';
            modalRef.dismissed.subscribe(()=>{
              this.auth.logout();
            })

          } else if (error.error.code == 403) {
            alert('SEM AUTORIZAÇÃO PARA ESSE RECURSO: ' + error.url)
          }

          return EMPTY;
        })
      )
  }
}
