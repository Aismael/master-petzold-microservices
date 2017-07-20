"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var http_1 = require("@angular/http");
var app_global_1 = require("./app.global");
var router_1 = require("@angular/router");
var app_routes_1 = require("./app.routes");
var home_components_1 = require("./inlay.sites/home.components");
var app_components_inlay_sites_1 = require("./app.components.inlay.sites");
var forms_1 = require("@angular/forms");
var app_stuff_1 = require("./app.stuff");
var app_rest_paths_1 = require("./app.rest.paths");
var services_1 = require("./inlay.sites/services");
var choose_components_1 = require("./inlay.sites/choose.components");
var order_component_1 = require("./inlay.sites/order.component");
var favorite_component_1 = require("./inlay.sites/favorite.component");
var ng_semantic_1 = require("ng-semantic");
var array_1 = require("./pipes/array");
var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    core_1.NgModule({
        imports: [
            platform_browser_1.BrowserModule,
            http_1.HttpModule,
            router_1.RouterModule.forRoot(app_routes_1.routes),
            forms_1.FormsModule,
            ng_semantic_1.NgSemanticModule,
            forms_1.FormsModule,
            forms_1.ReactiveFormsModule
        ],
        declarations: [
            app_global_1.SideSteps,
            app_global_1.Inlay,
            home_components_1.HomeComponent,
            home_components_1.HomeLeftComponent,
            home_components_1.HomeRightComponent,
            app_components_inlay_sites_1.emptyComponent,
            app_global_1.HomeButton,
            home_components_1.HomeErrorComponent,
            app_stuff_1.AppComponent,
            app_stuff_1.ContentX,
            choose_components_1.ChooseComponent,
            order_component_1.OrderComponent,
            favorite_component_1.FavoriteComponent,
            order_component_1.OrderitemsComponent,
            order_component_1.OrderOrderComponent,
            order_component_1.OrderFavoriteComponent,
            order_component_1.OrderPayComponent,
            array_1.SearchArrayPipe,
            favorite_component_1.FavoriteFavoriteComponent,
            favorite_component_1.FavoritePayComponent,
        ],
        bootstrap: [
            app_global_1.SideSteps,
            app_global_1.Inlay,
            app_global_1.HomeButton
        ],
        providers: [
            services_1.GetServiceUrlService,
            app_rest_paths_1.GetPathsService,
            app_rest_paths_1.GetDatasByPath,
            services_1.ErrorService,
            app_rest_paths_1.PostDatasByPath,
            services_1.LoginService,
            services_1.OrderService,
            services_1.ShopService,
            services_1.FavoriteService,
            services_1.ShopBasketService,
            services_1.FavoriteIdService
        ],
        schemas: [core_1.CUSTOM_ELEMENTS_SCHEMA]
    })
], AppModule);
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map