import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppComponent, ViewRestService}  from './app.component';
import { HttpModule } from '@angular/http';
import {GetServiceUrlService, Inlay, SideSteps} from "./app.global";
import {ContentX} from "./app.test.stuff";


@NgModule({
  imports:      [ BrowserModule ,HttpModule],
  declarations: [ AppComponent ,SideSteps, ContentX,Inlay],
  bootstrap:    [ AppComponent ,SideSteps, ContentX,Inlay],
  providers: [ViewRestService,GetServiceUrlService,SideSteps], //<-- you should inject all providers here

})
export class AppModule { }

