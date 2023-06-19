import { NgModule,DEFAULT_CURRENCY_CODE, LOCALE_ID } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { NgbAlertModule, NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';


import ptBr from '@angular/common/locales/pt';
import { registerLocaleData } from '@angular/common';
import { StudyComponent } from './study/study.component';
import { InfoComponent } from './info/info.component';

import { LoginComponent } from './login/login.component';
import { routing } from './app.routing';
import { FinancesModule } from './finances/finances.module';
import { StudyModule } from './study/study.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { InterceptorService } from './authentication/interceptor/interceptor.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AlertComponent } from './util/alert/alert.component';






registerLocaleData(ptBr);
@NgModule({
  declarations: [
    AppComponent,
    InfoComponent,
    LoginComponent,
    AlertComponent,



  ],
  imports: [
    BrowserModule,
    NgbModule,
    NgbPaginationModule,
    NgbAlertModule,
    FormsModule,
    StudyModule,
    FinancesModule,
    routing,
    ModalModule.forRoot(),
    HttpClientModule,
    BrowserAnimationsModule,

  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'pt' },
    InterceptorService,
    {
      provide:HTTP_INTERCEPTORS,
      useClass:InterceptorService,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
