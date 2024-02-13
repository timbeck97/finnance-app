import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable, catchError } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from 'src/app/util/alert/alert.component';
import { CancelrequestService } from '../cancelrequest/cancelrequest.service';

@Injectable({
  providedIn: 'root'
})
export class InterceptorService implements HttpInterceptor {
  
  constructor(private auth: AuthenticationService, private router: Router, private modalService: NgbModal, private cancelReq:CancelrequestService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let isRefreshTokenRequest = req.url.includes('token/refreshtoken') || req.url.includes('login');
    if (isRefreshTokenRequest) {
      return next.handle(req)
      .pipe(
        catchError((error: any) => {
          if(error.error.customCode==999){
            alert('Usuário ou senha inválidos');
          }else if(error.status==0){
            alert('Erro na comunicação com o servidor, possivelmente o servidor está fora do ar');
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


            this.modalService.dismissAll();
            const modalRef=this.modalService.open(AlertComponent);
            modalRef.componentInstance.titleText='Sessão expirada';
            modalRef.componentInstance.bodyText='Sua sessão expirou, por favor faça login novamente';
            modalRef.dismissed.subscribe(()=>{
              this.auth.logout();
            })

          } else if (error.error.code == 403) {
            alert('SEM AUTORIZAÇÃO PARA ESSE RECURSO: ' + error.url)
          }else if(error.error.code==404){
            alert('RECURSO NÃO ENCONTRADO: '+error.url)
          }else if(error.error.code==500){
            alert(error.error.erros[0])
          }else if(error.error.code==401){
            alert('ERRO DE REQUISIÇÃO: '+error.url)
          }else{
            alert('Erro na comunicação com o servidor')
          }
       
          return EMPTY;
        })
      )
  }
}
