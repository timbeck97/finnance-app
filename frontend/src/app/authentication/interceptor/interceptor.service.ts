import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable, ViewChild } from '@angular/core';
import { EMPTY, Observable, catchError } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from 'src/app/util/alert/alert.component';
import { CancelrequestService } from '../cancelrequest/cancelrequest.service';
import { ModalService } from 'src/app/util/modal.service';

@Injectable({
  providedIn: 'root'
})
export class InterceptorService implements HttpInterceptor {

  constructor(private auth: AuthenticationService, private router: Router, private modalService: ModalService, private cancelReq:CancelrequestService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let isRefreshTokenRequest = req.url.includes('token/refreshtoken') || req.url.includes('login');
    if (isRefreshTokenRequest) {
      return next.handle(req)
      .pipe(
        catchError((error: any) => {
          if(error.error.customCode==999){
            this.modalService.openModal('Erro no login', 'Login ou senha incorretos')
          }else if(error.status==0){
            this.modalService.openModal('Erro na comunicação com o servidor', 'Ocorreu um erro na comunicação com o servidor, por favor tente novamente mais tarde',)
          }
         
          return EMPTY;
        }
      ));
    }
    let token = this.auth.getToken();
    if (!token) {
      this.router.navigate(['/login'])
    }
    let tokenExpired = this.auth.validadeTokenExpiration();
    let refreshToken=this.auth.getRefreshToken();
    if(tokenExpired && refreshToken){
      if(!this.auth.validateRefreshTokenExpiration()){
        this.auth.refreshToken();
        return EMPTY;
      }
    }

    const authReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + token)
    });
    return next.handle(authReq)
      .pipe(
        takeUntil(this.cancelReq.onCancelPendingRequests()),
        catchError((error: any) => {
          this.cancelReq.cancelPendingRequests()
          if (error.error.customCode == 666) {
            this.modalService.openModal('Sessão expirada', 'Sua sessão expirou, por favor faça login novamente',()=>{
              this.auth.logout();
            })

          } else if (error.error.code == 403) {
            this.modalService.openModal('Sem autorização', 'Você não tem permissão para acessar este recurso')
          }else if(error.error.code==404){
            this.modalService.openModal('Recurso não encontrado', 'O recurso solicitado não foi encontrado: '+error.url)
          }else if(error.error.code==500){
            this.modalService.openModal('Erro interno no servidor', 'Ocorreu um erro interno no servidor, por favor tente novamente mais tarde',)
          }else if(error.error.code==401){
            this.modalService.openModal('Sem autorização', 'Você não tem permissão para acessar este recurso')
          }else{
           this.modalService.openModal('Erro na comunicação com o servidor', 'Ocorreu um erro na comunicação com o servidor, por favor tente novamente mais tarde',)
          
          }
       
          return EMPTY;
        })
      )
  }
  
}
