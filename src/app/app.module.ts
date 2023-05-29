import { NgModule,DEFAULT_CURRENCY_CODE, LOCALE_ID } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
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
import { FormatterDirective } from './util/formatter.directive';


registerLocaleData(ptBr);
@NgModule({
  declarations: [
    AppComponent,
    InfoComponent,
    LoginComponent,

  ],
  imports: [
    BrowserModule,
    NgbModule,
    FormsModule,
    StudyModule,
    FinancesModule,
    routing,
    ModalModule.forRoot()
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'pt' },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
