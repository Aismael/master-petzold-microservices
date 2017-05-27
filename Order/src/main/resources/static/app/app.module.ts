import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";
import {HomeButton, Inlay, SideSteps} from "./app.global";
import {RouterModule} from "@angular/router";
import {routes} from "./app.routes";
import {HomeComponent, HomeErrorComponent, HomeLeftComponent, HomeRightComponent} from "./inlay.sites/home.components";
import {emptyComponent} from "./app.components.inlay.sites";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppComponent, ContentX} from "./app.stuff";
import {GetDatasByPath, GetPathsService, PostDatasByPath} from "./app.rest.paths";
import {
    ErrorService, FavoriteIdService, FavoriteService, GetServiceUrlService, LoginService, OrderService, ShopBasketService,
    ShopService
} from "./inlay.sites/services";
import {ChooseComponent} from "./inlay.sites/choose.components";
import {
    OrderComponent,
    OrderFavoriteComponent,
    OrderitemsComponent,
    OrderOrderComponent,
    OrderPayComponent
} from "./inlay.sites/order.component";
import {FavoriteComponent, FavoriteFavoriteComponent, FavoritePayComponent} from "./inlay.sites/favorite.component";
import {NgSemanticModule} from "ng-semantic";
import {SearchArrayPipe} from "./pipes/array";

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
        HomeComponent,
        HomeLeftComponent,
        HomeRightComponent,
        emptyComponent,
        HomeButton,
        HomeErrorComponent,
        AppComponent,
        ContentX,
        ChooseComponent,
        OrderComponent,
        FavoriteComponent,
        OrderitemsComponent,
        OrderOrderComponent,
        OrderFavoriteComponent,
        OrderPayComponent,
        SearchArrayPipe,
        FavoriteFavoriteComponent,
        FavoritePayComponent,
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
        FavoriteIdService], //<-- you should inject all providers here
    schemas: [CUSTOM_ELEMENTS_SCHEMA]


})
export class AppModule {
}

