import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { StylebindingComponent } from './components/stylebinding/stylebinding.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EventbindingComponent } from './components/eventbinding/eventbinding.component';
import { FormsModule } from '@angular/forms';
import { ContadorComponent } from './components/contador/contador.component';
import { NgswitchcaseComponent } from './components/diretives/ngswitchcase/ngswitchcase.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    StylebindingComponent,
    EventbindingComponent,
    ContadorComponent,
    NgswitchcaseComponent

  ],
  imports: [
    BrowserModule,
    NgbModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
