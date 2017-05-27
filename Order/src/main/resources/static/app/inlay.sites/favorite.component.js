"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var app_rest_paths_1 = require("../app.rest.paths");
var services_1 = require("./services");
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var FavoriteComponent = (function () {
    function FavoriteComponent(getPathsService, loginService) {
        var _this = this;
        this.loginService = loginService;
        this.config = null;
        this.id = null;
        getPathsService.getPathsData().subscribe(function (config) { return _this.config = config.config.view; });
        this.id = loginService.getLast();
        loginService.getMessage().subscribe(function (message) {
            if (message) {
                _this.id = message.id;
            }
        });
    }
    return FavoriteComponent;
}());
FavoriteComponent = __decorate([
    core_1.Component({
        selector: "app-favorite",
        template: "\n        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n            <app-favorite-favorite class=\"center aligned column\"></app-favorite-favorite>\n            <div class=\"ui vertical divider\"></div>\n            <app-order-order class=\"center aligned column\"></app-order-order>\n        </div>\n        <div class=\"ui section divider\"></div>\n        <div class=\"ui one column middle aligned very relaxed stackable grid\">\n            <app-favorite-pay class=\"center aligned column\"></app-favorite-pay>\n        </div>\n        <app-home-error></app-home-error>"
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService, services_1.LoginService])
], FavoriteComponent);
exports.FavoriteComponent = FavoriteComponent;
var FavoriteFavoriteComponent = (function () {
    function FavoriteFavoriteComponent(getPathsService, getDatasByPath, errorService, loginService, shopBasketService, favoriteIdService) {
        var _this = this;
        this.getDatasByPath = getDatasByPath;
        this.errorService = errorService;
        this.loginService = loginService;
        this.shopBasketService = shopBasketService;
        this.favoriteIdService = favoriteIdService;
        this.data = { favorites: "" };
        this.id = null;
        this.choosenOne = { on: true };
        this.toggleControl = new forms_1.FormControl(false);
        getPathsService.getPathsData().subscribe(function (config) {
            _this.config = config.config.view;
            _this.loadItems();
        });
        this.id = loginService.getLast();
        loginService.getMessage().subscribe(function (message) {
            if (message) {
                _this.id = message.id;
            }
        });
    }
    FavoriteFavoriteComponent.prototype.submit = function (favorite) {
        this.favoriteIdService.id = favorite.id;
        this.shopBasketService.getBasket().items = favorite.itemSetStubDTOS;
        this.choosenOne.on = false;
        this.choosenOne = favorite;
        if (!favorite.on) {
            favorite.on = true;
        }
        else {
            favorite.on = false;
        }
    };
    FavoriteFavoriteComponent.prototype.loadItems = function () {
        var _this = this;
        this.getDatasByPath.getPathsData(this.config.favorite.path +
            this.config.favorite.all.path +
            this.config.favorite.all.account.path +
            "/" + this.id).subscribe(function (data) {
            _this.data.favorites = data.list;
        }, function (err) {
            _this.errorHandle("Mail", err);
        });
    };
    FavoriteFavoriteComponent.prototype.errorHandle = function (msg, err) {
        this.errorService.sendMessage(msg);
        this.error = err;
    };
    return FavoriteFavoriteComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", forms_1.FormControl)
], FavoriteFavoriteComponent.prototype, "toggleControl", void 0);
FavoriteFavoriteComponent = __decorate([
    core_1.Component({
        selector: "app-favorite-favorite",
        template: "\n        <sm-segment>\n            <h5>Items</h5>\n            <sm-input icon=\"search\" [(model)]=\"searchString\" class=\"fluid\" placeholder=\"Search...\"></sm-input>\n            <div class=\"ui divider\"></div>\n            <sm-list class=\"divided\">\n                <div *ngFor=\"let favorite of data.favorites  | smArraySearch:searchString\">\n                    <sm-item *ngIf=\"!favorite.on\">\n                        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n                            <div class=\"center aligned column\">\n                                <sm-accordion [options]=\"{ on: 'click' }\" class=\"fluid\">\n                                    <sm-accordion-item>\n                                        <accordion-title>\n                                            <div>\n                                                {{favorite.name}}\n                                            </div>\n                                        </accordion-title>\n                                        <accordion-content>\n                                            <div *ngFor=\"let item of favorite.itemSetStubDTOS\">\n                                                {{item.count}}x{{item.name}}\n                                            </div>\n                                        </accordion-content>\n                                    </sm-accordion-item>\n                                </sm-accordion>\n                            </div>\n                            <div class=\"center aligned column\">\n                                <div class=\"ui three column grid\">\n                                    <div (click)=\"submit(favorite)\" class=\"column\">\n                                        <sm-checkbox label=\" \" [control]=\"toggleControl\"\n                                                     type=\"toggle\"></sm-checkbox>\n                                    </div>\n                                    <div class=\"right aligned column\">\n                                        <h3 class=\"right aligned\">{{favorite.count}}</h3>\n                                    </div>\n                                    <div class=\"column\">\n                                        <i class=\"circular tags icon\"></i>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                    </sm-item>\n                </div>\n            </sm-list>\n        </sm-segment>    "
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService,
        app_rest_paths_1.GetDatasByPath,
        services_1.ErrorService,
        services_1.LoginService,
        services_1.ShopBasketService,
        services_1.FavoriteIdService])
], FavoriteFavoriteComponent);
exports.FavoriteFavoriteComponent = FavoriteFavoriteComponent;
var FavoritePayComponent = (function () {
    function FavoritePayComponent(getServiceUrlService, loginService, favoriteIdService, postDatasByPath, getPathsService, errorService, favoriteService) {
        var _this = this;
        this.getServiceUrlService = getServiceUrlService;
        this.loginService = loginService;
        this.favoriteIdService = favoriteIdService;
        this.postDatasByPath = postDatasByPath;
        this.getPathsService = getPathsService;
        this.errorService = errorService;
        this.favoriteService = favoriteService;
        this.config = null;
        getPathsService.getPathsData().subscribe(function (config) { return _this.config = config.config.view; });
        this.userId = loginService.getLast();
        loginService.getMessage().subscribe(function (message) {
            if (message) {
                _this.userId = message.id;
            }
        });
        favoriteService.getMessage().subscribe(function (message) { return _this.jump(); });
    }
    FavoritePayComponent.prototype.makeIPExternal = function (uri) {
        return this.getServiceUrlService.makeIPExternal(uri);
    };
    FavoritePayComponent.prototype.jump = function () {
        var _this = this;
        var favoriteId = this.favoriteIdService.id;
        this.postDatasByPath.postPathsData(this.config.favorite.path +
            this.config.favorite.one.path +
            this.config.favorite.one.order.path +
            "/" + favoriteId, favoriteId)
            .subscribe(function (data) {
            _this.orderId = data;
            console.log(data);
            var returnURI = { uri: "" };
            _this.getServiceUrlService.getUrl("BILLING").subscribe(function (uriJson) {
                returnURI.uri = _this.makeIPExternal(uriJson.uri) + "/?orderId=" + _this.orderId + "&&accountId=" + _this.userId;
                console.log(returnURI.uri);
                window.location.href = returnURI.uri;
            });
        }, function (err) {
            _this.errorHandle("order Saving", err);
        });
    };
    FavoritePayComponent.prototype.errorHandle = function (msg, err) {
        this.errorService.sendMessage(msg);
    };
    return FavoritePayComponent;
}());
FavoritePayComponent = __decorate([
    core_1.Component({
        selector: "app-favorite-pay",
        template: "\n        <div class=\"ui massive green labeled icon button\" (click)=\"jump()\">\n            <i class=\"credit card alternative icon\"></i>\n            Pay\n        </div>\n    "
    }),
    __metadata("design:paramtypes", [services_1.GetServiceUrlService,
        services_1.LoginService,
        services_1.FavoriteIdService,
        app_rest_paths_1.PostDatasByPath,
        app_rest_paths_1.GetPathsService,
        services_1.ErrorService,
        services_1.FavoriteService])
], FavoritePayComponent);
exports.FavoritePayComponent = FavoritePayComponent;
//# sourceMappingURL=favorite.component.js.map