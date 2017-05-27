import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";
import {ErrorComponent, HomeButton, Inlay, SideSteps} from "./app.global";
import {RouterModule} from "@angular/router";
import {routes} from "./app.routes";
import {emptyComponent} from "./app.components.inlay.sites";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppComponent, ContentX} from "./app.stuff";
import {GetDatasByPath, GetPathsService, PostDatasByPath} from "./app.rest.paths";
import {
    ErrorService, FavoriteIdService, FavoriteService, FromOrderService, GetServiceUrlService, LoginService, OrderService, ShopBasketService,
    ShopService
} from "./inlay.sites/services";
import {NgSemanticModule} from "ng-semantic";
import {SearchArrayPipe} from "./pipes/array";
import {HomeComponent} from "./inlay.sites/home.component";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        RouterModule.forRoot(routes),
        FormsModule,
        NgSemanticModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        SideSteps,
        Inlay,
        emptyComponent,
        HomeButton,
        AppComponent,
        ContentX,
        SearchArrayPipe,
        ErrorComponent,
        HomeComponent
        ],
    bootstrap: [
        SideSteps,
        Inlay,
        HomeButton],
    providers: [
        GetServiceUrlService,
        GetPathsService,
        GetDatasByPath,
        ErrorService,
        PostDatasByPath,
        LoginService,
        OrderService,
        ShopService,
        FavoriteService,
        ShopBasketService,
        FavoriteIdService,
        FromOrderService], //<-- you should inject all providers here
    schemas: [CUSTOM_ELEMENTS_SCHEMA]


})
export class AppModule {
}

