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
var platform_browser_1 = require("@angular/platform-browser");
require("rxjs/add/operator/map");
var router_1 = require("@angular/router");
var services_1 = require("./inlay.sites/services");
var HomeButton = (function () {
    function HomeButton() {
    }
    return HomeButton;
}());
HomeButton = __decorate([
    core_1.Component({
        selector: 'home-button',
        template: " <a class=\"ui item\" [routerLink]=\"['/home']\">Home</a>"
    })
], HomeButton);
exports.HomeButton = HomeButton;
var Inlay = (function () {
    function Inlay(sanitizerArg, router) {
        var _this = this;
        this.sanitizerArg = sanitizerArg;
        this.router = router;
        this.inlayUrl = "inlay.html";
        this.choose = false;
        this.order = false;
        this.favorite = false;
        this.sanitizer = sanitizerArg;
        this.router.events.subscribe(function (event) {
            if (event instanceof router_1.NavigationEnd) {
                _this.choose = event.urlAfterRedirects.includes("choose");
                _this.order = event.urlAfterRedirects.includes("order");
                _this.favorite = event.urlAfterRedirects.includes("favorite");
            }
        });
    }
    return Inlay;
}());
Inlay = __decorate([
    core_1.Component({
        selector: 'inlay',
        template: "\n        <div class=\"ui breadcrumb\">\n            <a [routerLink]=\"['/']\" >Home</a>\n            <i class=\"right angle icon divider\" *ngIf=\"choose\"></i>\n            <a [routerLink]=\"['/choose']\" *ngIf=\"choose\">Choose</a>\n            <i class=\"right angle icon divider\" *ngIf=\"order\"></i>\n            <a [routerLink]=\"['/choose/order']\" *ngIf=\"order\">Order</a>\n            <i class=\"right angle icon divider\" *ngIf=\"favorite\"></i>\n            <a [routerLink]=\"['/choose/favorite']\" *ngIf=\"favorite\">Favorite</a>\n        </div>\n        <router-outlet></router-outlet>\n    "
    }),
    __metadata("design:paramtypes", [platform_browser_1.DomSanitizer, router_1.Router])
], Inlay);
exports.Inlay = Inlay;
var InlayState;
(function (InlayState) {
    InlayState[InlayState["ACTIVE"] = "active"] = "ACTIVE";
    InlayState[InlayState["COMPLETED"] = "completed"] = "COMPLETED";
    InlayState[InlayState["New"] = ""] = "New";
})(InlayState = exports.InlayState || (exports.InlayState = {}));
var SideSteps = (function () {
    function SideSteps(sanitizerArg, getServiceUrlService) {
        this.sanitizerArg = sanitizerArg;
        this.getServiceUrlService = getServiceUrlService;
        this.sanitizer = sanitizerArg;
        this.serviceInlays = [
            {
                id: 1,
                name: "Order",
                description: "Choose your shipping options",
                URI: this.getURIByName("ORDER"),
                icon: "shop",
                state: InlayState.ACTIVE
            }, {
                id: 2,
                name: "Billing",
                description: "Enter billing information",
                URI: this.getURIByName("BILLING"),
                icon: "payment",
                state: InlayState.New
            }, {
                id: 3,
                name: "Chat",
                description: "Verify order details",
                URI: this.getURIByName("ESL"),
                icon: "mail",
                state: InlayState.New
            }
        ];
        this.selectServiceInlay(this.serviceInlays[0]);
        this.activeInlay = { active: this.serviceInlays[0] };
    }
    SideSteps.prototype.getURIByName = function (name) {
        return this.getServiceUrlService.getURIByName(name);
    };
    SideSteps.prototype.selectServiceInlay = function (serviceInlay) {
        this.selectedServiceInlay = serviceInlay;
    };
    SideSteps.prototype.makeIPExternal = function (uri) {
        return this.getServiceUrlService.makeIPExternal(uri);
    };
    return SideSteps;
}());
SideSteps = __decorate([
    core_1.Component({
        selector: 'side-step',
        template: "\n        <div class=\"ui fluid vertical steps\">\n            <div *ngFor=\"let si of serviceInlays\" class=\"{{si.state}} step\" id=\"{{si.name}}\">\n                <i class=\"{{si.icon}} icon\"></i>\n                <div class=\"content\">\n                    <div class=\"title\">{{si.name}}</div>\n                    <div class=\"description\">{{si.description}}\n                    </div>\n                    <a [href]=\"sanitizer.bypassSecurityTrustUrl(makeIPExternal(si.URI.uri))\">\n                        direct-Link-Test\n                    </a>\n                </div>\n            </div>\n        </div>"
    }),
    __metadata("design:paramtypes", [platform_browser_1.DomSanitizer, services_1.GetServiceUrlService])
], SideSteps);
exports.SideSteps = SideSteps;
//# sourceMappingURL=app.global.js.map