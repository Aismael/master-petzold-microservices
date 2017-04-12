import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import {AppComponent, ContentX, ViewRestService} from "./app.test.stuff";
import {AppModule} from "./app.module";


@NgModule({
  imports:      [ BrowserModule ,HttpModule,AppModule],
  declarations: [ AppComponent,ContentX],
  bootstrap:    [ AppComponent,ContentX],
  providers: [ViewRestService,], //<-- you should inject all providers here
})
export class InlayModule { }

