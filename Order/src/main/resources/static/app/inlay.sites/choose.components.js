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
var router_1 = require("@angular/router");
var ChooseComponent = (function () {
    function ChooseComponent(getPathsService, router) {
        var _this = this;
        this.router = router;
        this.config = null;
        this.id = null;
        getPathsService.getPathsData().subscribe(function (config) { return _this.config = config.config.view; });
        this.router.events.subscribe(function (event) {
            if (event instanceof router_1.NavigationEnd) {
                _this.sub = !(event.urlAfterRedirects.includes("order") || event.urlAfterRedirects.includes("favorite"));
            }
        });
    }
    ChooseComponent.prototype.toNew = function () {
        this.router.navigate(['/choose/order']);
    };
    ChooseComponent.prototype.toLoad = function () {
        this.router.navigate(['/choose/favorite']);
    };
    return ChooseComponent;
}());
ChooseComponent = __decorate([
    core_1.Component({
        selector: 'app-choose',
        template: "\n        <div class=\"ui section divider\"></div>\n        <div class=\"ui two column middle aligned very relaxed stackable grid\" *ngIf=\"sub\">\n            <div class=\"center aligned column\">\n                <div class=\"ui massive green labeled icon button\" (click)=\"toNew()\">\n                    <i class=\"shopping basket icon\"></i>\n                    New Order\n                </div>\n            </div>\n            <div class=\"divider-column\">\n                <div class=\"ui vertical divider\">\n                    Or\n                </div>\n            </div>\n            <div class=\"center aligned column\">\n                <div class=\"ui massive blue right labeled icon button\" (click)=\"toLoad()\">\n                    <i class=\"right thumbs up icon\"></i>\n                    Reorder\n                </div>\n            </div>\n        </div>\n        <router-outlet></router-outlet>\n    "
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService, router_1.Router])
], ChooseComponent);
exports.ChooseComponent = ChooseComponent;
//# sourceMappingURL=choose.components.js.map