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
var core_1 = require("@angular/core");
var app_rest_paths_1 = require("../app.rest.paths");
var services_1 = require("./services");
var router_1 = require("@angular/router");
var BillComponent = (function () {
    function BillComponent(getPathsService, loginService, errorService, fromOrderService, getDatasByPath, basket, shop, router) {
        var _this = this;
        this.errorService = errorService;
        this.getDatasByPath = getDatasByPath;
        this.basket = basket;
        this.shop = shop;
        this.router = router;
        this.config = null;
        this.router.events.subscribe(function (event) {
            if (event instanceof router_1.NavigationEnd) {
                _this.sub = !(event.urlAfterRedirects.includes("pay"));
            }
        });
        getPathsService.getPathsData().subscribe(function (config) {
            _this.config = config.config.view;
            _this.load();
        });
        this.id = loginService.getLast();
        this.orderId = fromOrderService.getLast();
        errorService.clearMessage();
    }
    BillComponent.prototype.load = function () {
        var _this = this;
        this.getDatasByPath.getPathsData(this.config.order.path +
            this.config.order.one.path +
            this.config.order.one.idAndAccount.path +
            "/" + this.orderId + "/" + this.id).subscribe(function (data) {
            _this.shop.sendMessage(data);
            _this.basket.basket.items = data.itemSetStubDTOS;
        }, function (err) {
            _this.errorService.sendMessage("Order or Account doesnt exist" + err);
        });
    };
    return BillComponent;
}());
BillComponent = __decorate([
    core_1.Component({
        selector: "app-bill",
        template: "\n        <div class=\"ui section divider\"></div>\n        <div class=\"ui two column middle aligned very relaxed stackable grid\" *ngIf=\"sub\">\n            <app-order-order class=\"column\"></app-order-order>\n            <div class=\"ui vertical divider\"></div>\n            <app-bill-pay class=\"center aligned column\"></app-bill-pay>\n        </div>\n        <router-outlet></router-outlet>\n        <app-home-error></app-home-error>"
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService,
        services_1.LoginService,
        services_1.ErrorService,
        services_1.FromOrderService,
        app_rest_paths_1.GetDatasByPath,
        services_1.ShopBasketService,
        services_1.ShopService,
        router_1.Router])
], BillComponent);
exports.BillComponent = BillComponent;
var OrderOrderComponent = (function () {
    function OrderOrderComponent(basket, shop) {
        var _this = this;
        this.basket = basket;
        this.shop = shop;
        shop.getMessage().subscribe(function (data) {
            _this.date = new Date(data.date);
            _this.sum = data.sum;
        });
    }
    return OrderOrderComponent;
}());
OrderOrderComponent = __decorate([
    core_1.Component({
        selector: "app-order-order",
        template: "\n        <sm-menu title=\"Shopping Card\" class=\"fluid vertical menu\">\n            <a sm-item>\n                Order from {{date}}\n            </a>\n            <a sm-item *ngFor=\"let item of basket.getBasket().items\" [icon]=\"item.name\">\n                <div class=\"ui grid\">\n                    <div class=\"eight wide column\">\n                        {{item?.name}}\n                    </div>\n                    <div class=\"three wide column\">\n                        <h3 class=\"right aligned\">{{item.count}} x </h3>\n                    </div>\n                    <div class=\"five wide column\">\n                        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n                            <div class=\"column\">\n                                <h3 class=\"right aligned\">{{item.currency}}</h3>\n                            </div>\n                            <div class=\"column\">\n                                <i class=\"circular dollar icon\"></i>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </a>\n            <div class=\"ui section divider\"></div>\n            <a sm-item class=\"right aligned\">\n                <div class=\"ui grid\">\n                    <div class=\"eight wide column\">\n                        <h3 class=\"right aligned\">Sum</h3>\n                    </div>\n                    <div class=\"three wide column\">\n                    </div>\n                    <div class=\"five wide column\">\n\n                        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n                            <div class=\"column\">\n                                <h3 class=\"right aligned\">{{sum}}</h3>\n                            </div>\n                            <div class=\"column\">\n                                <i class=\"circular dollar icon\"></i>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </a>\n        </sm-menu>\n    "
    }),
    __metadata("design:paramtypes", [services_1.ShopBasketService, services_1.ShopService])
], OrderOrderComponent);
exports.OrderOrderComponent = OrderOrderComponent;
var BillPayComponent = (function () {
    function BillPayComponent(router) {
        this.router = router;
    }
    BillPayComponent.prototype.click = function () {
        this.router.navigate(["/bill/pay"]);
    };
    return BillPayComponent;
}());
BillPayComponent = __decorate([
    core_1.Component({
        selector: "app-bill-pay",
        template: "\n        <div class=\"ui massive green labeled icon button\" (click)=\"click()\">\n            <i class=\"credit card alternative icon\"></i>\n            Choose Pay Method\n        </div>\n    "
    }),
    __metadata("design:paramtypes", [router_1.Router])
], BillPayComponent);
exports.BillPayComponent = BillPayComponent;
//# sourceMappingURL=bill.component.js.map