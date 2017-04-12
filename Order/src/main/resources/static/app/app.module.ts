import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import {GetServiceUrlService, Inlay, SideSteps} from "./app.global";
import {InlayModule} from "./app.module.inlay";

@NgModule({
  imports:      [ BrowserModule ,HttpModule,InlayModule],
  declarations: [ SideSteps,Inlay],
  bootstrap:    [ SideSteps,Inlay],
  providers: [GetServiceUrlService], //<-- you should inject all providers here

})
export class AppModule { }

