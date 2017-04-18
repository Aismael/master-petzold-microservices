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
        selector: 'app-favorite',
        template: "\n        <div class=\"ui two column middle aligned very relaxed stackable grid\" >\n            fav\n        </div>\n    "
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService, services_1.LoginService])
], FavoriteComponent);
exports.FavoriteComponent = FavoriteComponent;
//# sourceMappingURL=favorite.component.js.map