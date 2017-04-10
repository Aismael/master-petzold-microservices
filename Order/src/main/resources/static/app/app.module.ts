import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppComponent, ContentX, SideSteps, ViewRestService}  from './app.component';
import { HttpModule } from '@angular/http';


@NgModule({
  imports:      [ BrowserModule ,HttpModule],
  declarations: [ AppComponent ,SideSteps, ContentX],
  bootstrap:    [ AppComponent ,SideSteps, ContentX],
  providers: [ViewRestService] //<-- you should inject all providers here

})
export class AppModule { }

