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
var PayComponent = (function () {
    function PayComponent(getPathsService, loginService, errorService, fromOrderService, getDatasByPath, router, shop, postDatasByPath, getServiceUrlService) {
        var _this = this;
        this.errorService = errorService;
        this.getDatasByPath = getDatasByPath;
        this.router = router;
        this.shop = shop;
        this.postDatasByPath = postDatasByPath;
        this.getServiceUrlService = getServiceUrlService;
        this.payDTO = {
            bankAccountId: 0,
            orderId: 0
        };
        this.id = loginService.getLast();
        this.orderId = fromOrderService.getLast();
        this.payDTO.orderId = fromOrderService.getLast();
        errorService.clearMessage();
        this.router.events.subscribe(function (event) {
            if (event instanceof router_1.NavigationEnd) {
                _this.sub2 = !(event.urlAfterRedirects.includes("make"));
                _this.load();
            }
        });
        getPathsService.getPathsData().subscribe(function (config) {
            _this.config = config.config.view;
            _this.load();
        });
        this.sum = shop.getLast().sum;
        this.actualAccount = { ammount: 0, bankname: "noname", id: 0 };
    }
    PayComponent.prototype.request = function () {
        this.router.navigate(["/bill/pay/make"]);
    };
    PayComponent.prototype.pay = function () {
        var _this = this;
        this.payDTO.bankAccountId = this.actualAccount.id;
        this.postDatasByPath.postPathsData(this.config.bankAccount.path +
            this.config.bankAccount.one.path +
            this.config.bankAccount.one.pay.path, this.payDTO)
            .subscribe(function (data) {
            console.log(data);
            var returnURI = { uri: "" };
            _this.getServiceUrlService.getUrl("ESL").subscribe(function (uriJson) {
                returnURI.uri = _this.makeIPExternal(uriJson.uri);
                console.log(returnURI.uri);
                window.location.href = returnURI.uri;
            });
        }, function (err) {
            _this.errorHandle("Paying", err);
        });
    };
    PayComponent.prototype.errorHandle = function (msg, err) {
        this.errorService.sendMessage(msg);
    };
    PayComponent.prototype.click = function (account) {
        this.actualAccount = account;
        console.log(this.actualAccount);
    };
    PayComponent.prototype.load = function () {
        var _this = this;
        if (this.config) {
            this.getDatasByPath.getPathsData(this.config.bankAccount.path +
                this.config.bankAccount.all.path +
                this.config.bankAccount.all.account.path +
                "/" + this.id).subscribe(function (data) {
                _this.accounts = data.bankAccountList;
                _this.actualAccount = data.bankAccountList[0];
                console.log(_this.actualAccount);
                console.log(data);
            }, function (err) {
                _this.errorService.sendMessage("Account doesnt exist" + err);
            });
        }
    };
    PayComponent.prototype.makeIPExternal = function (uri) {
        return this.getServiceUrlService.makeIPExternal(uri);
    };
    return PayComponent;
}());
PayComponent = __decorate([
    core_1.Component({
        selector: "app-bill",
        template: "\n        <div *ngIf=\"sub2\">\n            <div class=\"ui grid\">\n                <div class=\"four wide column\">\n                    <sm-menu title=\"Your Accounts\" class=\"fluid vertical menu inverted blue\">\n                        <a sm-item *ngFor=\"let bankAccount of accounts\" [icon]=\"bankAccount.name\"\n                           (click)=\"click(bankAccount)\">{{bankAccount?.bankname}}\n                            <i class=\"arrow circle right icon\" *ngIf=\"bankAccount?.bankname===actualAccount.bankname\">\n                            </i>\n                        </a>\n                    </sm-menu>\n                </div>\n                <div class=\"twelve wide stretched column\">\n                    <div class=\"ui segment\">\n                        <h4>your Actual ammount for your Account at {{actualAccount.bankname}} is {{actualAccount.ammount}} $\n                            and you have to pay {{sum}} $\n                            <div class=\"ui section divider\"></div>\n                            so your new ammount would be {{actualAccount.ammount - sum}}\n                            <div class=\"ui section divider\"></div>\n                        </h4>\n                        <div class=\"ui massive green labeled icon button\" (click)=\"pay()\">\n                            <i class=\"credit card alternative icon\"></i>\n                            Pay @ {{actualAccount.bankname}}\n                        </div>\n                    </div>\n                </div>\n            </div>\n            <div class=\"ui section divider\"></div>\n            <div class=\"one wide center aligned centered column\">\n                <div class=\"ui massive red labeled icon button\" (click)=\"request()\">\n                    <i class=\"university icon\"></i>\n                    request new Account\n                </div>\n            </div>\n        </div>\n        <router-outlet></router-outlet>\n    "
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService,
        services_1.LoginService,
        services_1.ErrorService,
        services_1.FromOrderService,
        app_rest_paths_1.GetDatasByPath,
        router_1.Router,
        services_1.ShopService,
        app_rest_paths_1.PostDatasByPath,
        services_1.GetServiceUrlService])
], PayComponent);
exports.PayComponent = PayComponent;
//# sourceMappingURL=pay.components.js.map