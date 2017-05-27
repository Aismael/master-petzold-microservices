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
require("rxjs/add/operator/map");
var app_rest_paths_1 = require("./app.rest.paths");
var AppComponent = (function () {
    function AppComponent(getPathsService) {
        var _this = this;
        this.config = null;
        getPathsService.getPathsData().subscribe(function (config) { return _this.config = config.config.view; });
    }
    return AppComponent;
}());
AppComponent = __decorate([
    core_1.Component({
        selector: 'my-app',
        template: "<h1>Hello {{config}}</h1>"
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService])
], AppComponent);
exports.AppComponent = AppComponent;
var ContentX = (function () {
    function ContentX() {
        this.xs = [{
                id: 0
            }, {
                id: 1
            }, {
                id: 2
            }, {
                id: 3
            }, {
                id: 4
            }, {
                id: 5
            }, {
                id: 6
            }, {
                id: 7
            }];
        this.selectX(this.xs[0]);
    }
    ContentX.prototype.selectX = function (x) {
        this.selectedX = x;
    };
    return ContentX;
}());
ContentX = __decorate([
    core_1.Component({
        selector: 'contentx',
        template: "\n        <p *ngFor=\"let i of xs\" >\n            <img class=\"ui wireframe paragraph image\" src=\"/images/wireframe/paragraph.png\">\n        </p>\n    "
    }),
    __metadata("design:paramtypes", [])
], ContentX);
exports.ContentX = ContentX;
//# sourceMappingURL=app.stuff.js.map