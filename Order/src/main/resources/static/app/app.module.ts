import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppComponent, ViewRestService}  from './app.component';
import { HttpModule } from '@angular/http';


@NgModule({
  imports:      [ BrowserModule ,HttpModule],
  declarations: [ AppComponent ],
  bootstrap:    [ AppComponent ],
  providers: [ViewRestService] //<-- you should inject all providers here

})
export class AppModule { }

