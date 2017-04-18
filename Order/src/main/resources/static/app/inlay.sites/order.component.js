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
var router_1 = require("@angular/router");
var OrderComponent = (function () {
    function OrderComponent(getPathsService, loginService) {
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
    return OrderComponent;
}());
OrderComponent = __decorate([
    core_1.Component({
        selector: 'app-order',
        template: "\n        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n            <div class=\"center aligned column\">\n                <app-order-items></app-order-items>\n            </div>\n            <div class=\"center aligned column\">\n                <app-order-order></app-order-order>\n            </div>\n        </div>\n        <div class=\"ui section divider\"></div>\n        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n            <div class=\"center aligned column\">\n                <app-order-favorite></app-order-favorite>\n            </div>\n            <div class=\"divider-column\">\n                <div class=\"ui vertical divider\">\n                    Or\n                </div>\n            </div>\n            <div class=\"center aligned column\">\n                <app-order-pay></app-order-pay>\n            </div>\n        </div>\n    "
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService, services_1.LoginService])
], OrderComponent);
exports.OrderComponent = OrderComponent;
var OrderitemsComponent = (function () {
    function OrderitemsComponent() {
    }
    return OrderitemsComponent;
}());
OrderitemsComponent = __decorate([
    core_1.Component({
        selector: 'app-order-items',
        template: "\n        <div>\n            <sm-accordion class=\"styled vertical menu\" [options]=\"{ exclusive: true, on: 'mousedown' }\">\n                <sm-accordion-item>\n                    <accordion-title>What is a dog?</accordion-title>\n                    <accordion-content>A dog is a type of domesticated animal. Known for its loyalty and faithfulness,\n                        it can be found as a welcome guest in many households across the world.\n                    </accordion-content>\n                </sm-accordion-item>\n                <sm-accordion-item>\n                    <accordion-title>What is a dog?</accordion-title>\n                    <accordion-content>A dog is a type of domesticated animal. Known for its loyalty and faithfulness,\n                        it can be found as a welcome guest in many households across the world.\n                    </accordion-content>\n                </sm-accordion-item>\n                <sm-accordion-item>\n                    <accordion-title>What is a dog?</accordion-title>\n                    <accordion-content>A dog is a type of domesticated animal. Known for its loyalty and faithfulness,\n                        it can be found as a welcome guest in many households across the world.\n                    </accordion-content>\n                </sm-accordion-item>\n            </sm-accordion>\n        </div>\n    "
    }),
    __metadata("design:paramtypes", [])
], OrderitemsComponent);
exports.OrderitemsComponent = OrderitemsComponent;
var OrderOrderComponent = (function () {
    function OrderOrderComponent() {
        this.items = [];
        this.items = [{
                "title": "Games",
                "link": "Home",
                "icon": "gamepad"
            }, {
                "title": "Chanel",
                "link": "Chanel",
                "icon": "video camera"
            }, {
                "title": "Videos",
                "link": "Videos",
                "icon": "video play"
            }];
    }
    return OrderOrderComponent;
}());
OrderOrderComponent = __decorate([
    core_1.Component({
        selector: 'app-order-order',
        template: "\n        <div>\n            <sm-menu title=\"Vertical\" class=\"ui menu vertical\">\n                <a sm-item *ngFor=\"let item of items\" [icon]=\"item.icon\">{{item?.title}}</a>\n            </sm-menu>\n        </div>\n    "
    }),
    __metadata("design:paramtypes", [])
], OrderOrderComponent);
exports.OrderOrderComponent = OrderOrderComponent;
var OrderFavoriteComponent = (function () {
    function OrderFavoriteComponent() {
        this.items = [];
        this.items = [{
                "title": "Games",
                "link": "Home",
                "icon": "gamepad"
            }, {
                "title": "Chanel",
                "link": "Chanel",
                "icon": "video camera"
            }, {
                "title": "Videos",
                "link": "Videos",
                "icon": "video play"
            }];
    }
    return OrderFavoriteComponent;
}());
OrderFavoriteComponent = __decorate([
    core_1.Component({
        selector: 'app-order-favorite',
        template: "\n        <div>\n            <sm-menu title=\"Vertical\" class=\"ui menu vertical\">\n                <a sm-item *ngFor=\"let item of items\" [icon]=\"item.icon\">{{item?.title}}</a>\n            </sm-menu>\n        </div>\n    "
    }),
    __metadata("design:paramtypes", [])
], OrderFavoriteComponent);
exports.OrderFavoriteComponent = OrderFavoriteComponent;
var OrderPayComponent = (function () {
    function OrderPayComponent(getServiceUrlService, router, loginService, orderService) {
        var _this = this;
        this.getServiceUrlService = getServiceUrlService;
        this.router = router;
        this.loginService = loginService;
        this.orderService = orderService;
        this.userId = loginService.getLast();
        loginService.getMessage().subscribe(function (message) {
            if (message) {
                _this.userId = message.id;
            }
        });
        this.orderId = orderService.getLast();
        orderService.getMessage().subscribe(function (message) {
            if (message) {
                _this.orderId = message.id;
            }
        });
    }
    OrderPayComponent.prototype.makeIPExternal = function (uri) {
        return this.getServiceUrlService.makeIPExternal(uri);
    };
    OrderPayComponent.prototype.jump = function () {
        var _this = this;
        var returnURI = { uri: "" };
        this.getServiceUrlService.getUrl("BILLING").subscribe(function (uriJson) {
            returnURI.uri = _this.makeIPExternal(uriJson.uri) + "/?orderId=" + _this.orderId + "&&accountId=" + _this.userId;
            window.location.href = _this.makeIPExternal(returnURI.uri);
        });
    };
    return OrderPayComponent;
}());
OrderPayComponent = __decorate([
    core_1.Component({
        selector: 'app-order-pay',
        template: "\n        <div class=\"ui massive green labeled icon button\" (click)=\"jump()\">\n            <i class=\"credit card alternative icon\"></i>\n            Pay\n        </div>\n    "
    }),
    __metadata("design:paramtypes", [services_1.GetServiceUrlService, router_1.Router, services_1.LoginService, services_1.OrderService])
], OrderPayComponent);
exports.OrderPayComponent = OrderPayComponent;
//# sourceMappingURL=order.component.js.map