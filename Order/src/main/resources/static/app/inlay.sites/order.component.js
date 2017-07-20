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
var OrderComponent = (function () {
    function OrderComponent(getPathsService, loginService) {
        var _this = this;
        this.loginService = loginService;
        this.config = null;
        this.userId = null;
        getPathsService.getPathsData().subscribe(function (config) { return _this.config = config.config.view; });
        this.userId = loginService.getLast();
        loginService.getMessage().subscribe(function (message) {
            if (message) {
                _this.userId = message.id;
            }
        });
    }
    return OrderComponent;
}());
OrderComponent = __decorate([
    core_1.Component({
        selector: "app-order",
        template: "\n        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n            <app-order-items class=\"center aligned column\"></app-order-items>\n            <div class=\"ui vertical divider\"></div>\n            <app-order-order class=\"center aligned column\"></app-order-order>\n        </div>\n        <div class=\"ui section divider\"></div>\n        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n            <app-order-favorite class=\"center aligned column\"></app-order-favorite>\n            <div class=\"divider-column\">\n                <div class=\"ui vertical divider\">\n                    Or\n                </div>\n            </div>\n            <app-order-pay class=\"center aligned column\"></app-order-pay>\n        </div>\n         <app-home-error></app-home-error>"
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService, services_1.LoginService])
], OrderComponent);
exports.OrderComponent = OrderComponent;
var OrderitemsComponent = (function () {
    function OrderitemsComponent(getPathsService, getDatasByPath, errorService, shopService) {
        var _this = this;
        this.getDatasByPath = getDatasByPath;
        this.errorService = errorService;
        this.shopService = shopService;
        this.data = { items: "" };
        this.toggleControl = new forms_1.FormControl(false);
        getPathsService.getPathsData().subscribe(function (config) {
            _this.config = config.config.view;
            _this.loadItems();
        });
    }
    OrderitemsComponent.prototype.submit = function (item) {
        if (!item.on) {
            this.shopService.sendMessage(item);
            item.on = true;
        }
        else {
            item.on = false;
        }
    };
    OrderitemsComponent.prototype.loadItems = function () {
        var _this = this;
        this.getDatasByPath.getPathsData(this.config.item.path +
            this.config.item.all.path).subscribe(function (data) {
            _this.data.items = data;
        }, function (err) {
            _this.errorHandle("Mail", err);
        });
    };
    OrderitemsComponent.prototype.errorHandle = function (msg, err) {
        this.errorService.sendMessage(msg);
        this.error = err;
    };
    return OrderitemsComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", forms_1.FormControl)
], OrderitemsComponent.prototype, "toggleControl", void 0);
OrderitemsComponent = __decorate([
    core_1.Component({
        selector: "app-order-items",
        template: "\n        <sm-segment>\n            <h5>Items</h5>\n            <sm-input icon=\"search\" [(model)]=\"searchString\" class=\"fluid\" placeholder=\"Search...\"></sm-input>\n            <div class=\"ui divider\"></div>\n            <sm-list class=\"divided\">\n                <div *ngFor=\"let item of data.items  | smArraySearch:searchString\">\n                    <sm-item *ngIf=\"!item.on\">\n                        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n                            <div class=\"center aligned column\">\n                                <sm-accordion [options]=\"{ on: 'click' }\" class=\"fluid\">\n                                    <sm-accordion-item>\n                                        <accordion-title>\n                                            <div>\n                                                {{item.name}}\n                                            </div>\n                                        </accordion-title>\n                                        <accordion-content>\n                                            <div>\n                                                {{item.details}}\n                                                |\n                                                <i *ngIf=\"item.allergens\" class=\"big warning circle icon\"></i>\n                                            </div>\n                                        </accordion-content>\n                                    </sm-accordion-item>\n                                </sm-accordion>\n                            </div>\n                            <div class=\"center aligned column\">\n                                <div class=\"ui three column grid\">\n                                    <div (click)=\"submit(item)\" class=\"column\">\n                                        <sm-checkbox label=\" \" [control]=\"toggleControl\"\n                                                     type=\"toggle\"></sm-checkbox>\n                                    </div>\n                                    <div class=\"right aligned column\">\n                                        <h3 class=\"right aligned\">{{item.currency}}</h3>\n                                    </div>\n                                    <div class=\"column\">\n                                        <i class=\"circular dollar icon\"></i>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                    </sm-item>\n                </div>\n            </sm-list>\n        </sm-segment>    "
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService, app_rest_paths_1.GetDatasByPath, services_1.ErrorService, services_1.ShopService])
], OrderitemsComponent);
exports.OrderitemsComponent = OrderitemsComponent;
var OrderOrderComponent = (function () {
    function OrderOrderComponent(shopService, basket) {
        var _this = this;
        this.shopService = shopService;
        this.basket = basket;
        basket.getBasket().items = [];
        this.indexE = { i: null };
        shopService.getMessage().subscribe(function (message) {
            if (message) {
                console.log(message);
                console.log(_this.basket.getBasket());
                if (!_this.isInArray(_this.basket.getBasket().items, message, _this.indexE)) {
                    message.count = 1;
                    _this.basket.getBasket().items.push(message);
                }
                else {
                    if (_this.indexE.i)
                        _this.basket.getBasket().items.splice((Number)(_this.indexE.i) - 1, 1);
                }
            }
        });
        this.basket.getBasket().items = [];
    }
    OrderOrderComponent.prototype.isInArray = function (array, itemV, indexV) {
        var i = false;
        var index = 0;
        array.forEach(function (item) {
            index++;
            if (item.id === itemV.id) {
                indexV.i = index;
                i = true;
            }
        });
        return i;
    };
    OrderOrderComponent.prototype.fromItemList = function (item) {
        if (item.on == null) {
            return false;
        }
        else
            return true;
    };
    OrderOrderComponent.prototype.submit = function (item) {
        if (!item.on) {
            item.on = true;
        }
        else {
            item.on = false;
            this.shopService.sendMessage(item);
        }
    };
    return OrderOrderComponent;
}());
OrderOrderComponent = __decorate([
    core_1.Component({
        selector: "app-order-order",
        template: "\n        <sm-menu title=\"Shopping Card\" class=\"fluid vertical menu\">\n            <a sm-item *ngFor=\"let item of basket.getBasket().items\" [icon]=\"item.name\">\n                <div class=\"ui three column grid\">\n                    <div  class=\" column\">\n                        <i *ngIf=\"item.on\" (click)=\"submit(item)\" class=\"large red remove circle icon\"></i>\n                    </div>\n                    <div class=\"right aligned column\">\n                        {{item?.name}}\n                    </div>\n                    <div class=\" column\">\n                        <input type=\"number\" placeholder=\"count\" [(ngModel)]=\"item.count\" #ctrl=\"ngModel\" style=\"max-width: 2vw\">\n                    </div>\n                </div>\n            </a>\n        </sm-menu>\n    "
    }),
    __metadata("design:paramtypes", [services_1.ShopService, services_1.ShopBasketService])
], OrderOrderComponent);
exports.OrderOrderComponent = OrderOrderComponent;
var OrderFavoriteComponent = (function () {
    function OrderFavoriteComponent(getServiceUrlService, loginService, favoriteService, getPathsService, postDatasByPath, errorService) {
        var _this = this;
        this.getServiceUrlService = getServiceUrlService;
        this.loginService = loginService;
        this.favoriteService = favoriteService;
        this.postDatasByPath = postDatasByPath;
        this.errorService = errorService;
        this.config = null;
        this.items = [];
        getPathsService.getPathsData().subscribe(function (config) { return _this.config = config.config.view; });
        this.items = [{
                "title": "1. Name it",
                "link": "Home",
                "icon": "tag big"
            }, {
                "title": "2. and",
                "link": "Chanel",
                "icon": "credit card alternative big"
            }];
        this.userId = loginService.getLast();
        loginService.getMessage().subscribe(function (message) {
            if (message) {
                _this.userId = message.id;
            }
        });
    }
    OrderFavoriteComponent.prototype.jump = function () {
        var _this = this;
        var favorite = this.favoriteService.makeOrderfromBasket();
        this.postDatasByPath.postPathsData(this.config.favorite.path +
            this.config.favorite.one.path, favorite)
            .subscribe(function (data) {
            console.log(data);
        }, function (err) {
            _this.errorHandle("order Saving", err);
        });
    };
    OrderFavoriteComponent.prototype.errorHandle = function (msg, err) {
        this.errorService.sendMessage(msg);
    };
    return OrderFavoriteComponent;
}());
OrderFavoriteComponent = __decorate([
    core_1.Component({
        selector: "app-order-favorite",
        template: "\n        <sm-menu title=\"Save & Pay\" class=\"fluid vertical menu\">\n            <a sm-item [icon]=\"items[0].icon\">\n                <div class=\"ui two column grid\">\n                    <div class=\"left aligned column\">\n                        {{items[0].title}}\n                    </div>\n                    <div class=\"right aligned column\">\n                        <input type=\"text\" placeholder=\"favorite\" [(ngModel)]=\"name\" #ctrl=\"ngModel\" style=\"max-width: 6vw\">\n                    </div>\n                </div>\n            </a>\n            <a sm-item [icon]=\"items[1].icon\">\n                <div class=\"ui two column grid\">\n                    <div class=\"left aligned column\">\n                        {{items[1].title}}\n                    </div>\n                    <div class=\"right aligned column\">\n\n                        <div class=\"ui big blue button\" (click)=\"jump()\">\n                            Pay\n                        </div>\n                    </div>\n                </div>\n            </a>\n        </sm-menu>\n    "
    }),
    __metadata("design:paramtypes", [services_1.GetServiceUrlService,
        services_1.LoginService,
        services_1.FavoriteService,
        app_rest_paths_1.GetPathsService,
        app_rest_paths_1.PostDatasByPath,
        services_1.ErrorService])
], OrderFavoriteComponent);
exports.OrderFavoriteComponent = OrderFavoriteComponent;
var OrderPayComponent = (function () {
    function OrderPayComponent(getServiceUrlService, loginService, orderService, postDatasByPath, getPathsService, errorService, favoriteService) {
        var _this = this;
        this.getServiceUrlService = getServiceUrlService;
        this.loginService = loginService;
        this.orderService = orderService;
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
    OrderPayComponent.prototype.makeIPExternal = function (uri) {
        return this.getServiceUrlService.makeIPExternal(uri);
    };
    OrderPayComponent.prototype.jump = function () {
        var _this = this;
        var order = this.orderService.makeOrderfromBasket();
        console.log(JSON.stringify(order));
        this.postDatasByPath.postPathsData(this.config.order.path +
            this.config.order.one.path, order)
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
    OrderPayComponent.prototype.errorHandle = function (msg, err) {
        this.errorService.sendMessage(msg);
    };
    return OrderPayComponent;
}());
OrderPayComponent = __decorate([
    core_1.Component({
        selector: "app-order-pay",
        template: "\n        <div class=\"ui massive green labeled icon button\" (click)=\"jump()\">\n            <i class=\"credit card alternative icon\"></i>\n            Pay\n        </div>\n    "
    }),
    __metadata("design:paramtypes", [services_1.GetServiceUrlService,
        services_1.LoginService,
        services_1.OrderService,
        app_rest_paths_1.PostDatasByPath,
        app_rest_paths_1.GetPathsService,
        services_1.ErrorService,
        services_1.FavoriteService])
], OrderPayComponent);
exports.OrderPayComponent = OrderPayComponent;
//# sourceMappingURL=order.component.js.map