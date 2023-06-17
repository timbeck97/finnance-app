import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable, catchError } from 'rxjs';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class InterceptorService implements HttpInterceptor{

  constructor(private auth:AuthenticationService, private router:Router) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    let token=this.auth.getToken();
    if(!token){
      this.router.navigate(['/login'])
    }
    const authReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer '+token)
    });
    return next.handle(authReq)
    .pipe(
      catchError((error:any)=>{
          if(error.error.customCode==666){
            this.auth.logout();
            console.log('TODO: Handle refresh token logic')
          }else if(error.error.code==403){
            alert('SEM AUTORIZAÇÃO PARA ESSE RECURSO: '+error.url)
          }

          return EMPTY;
      })
    )
  }
}
