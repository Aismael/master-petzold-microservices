import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import {GetServiceUrlService, Inlay, SideSteps} from "./app.global";

@NgModule({
  imports:      [ BrowserModule ,HttpModule],
  declarations: [ SideSteps,Inlay],
  bootstrap:    [ SideSteps,Inlay],
  providers: [GetServiceUrlService], //<-- you should inject all providers here

})
export class AppModule { }

