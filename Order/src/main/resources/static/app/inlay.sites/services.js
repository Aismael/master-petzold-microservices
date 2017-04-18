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
var Subject_1 = require("rxjs/Subject");
var http_1 = require("@angular/http");
var ErrorService = (function () {
    function ErrorService() {
        this.subject = new Subject_1.Subject();
    }
    ErrorService.prototype.sendMessage = function (message) {
        this.subject.next({ text: message });
    };
    ErrorService.prototype.clearMessage = function () {
        this.subject.next();
    };
    ErrorService.prototype.getMessage = function () {
        return this.subject.asObservable();
    };
    return ErrorService;
}());
ErrorService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [])
], ErrorService);
exports.ErrorService = ErrorService;
var LoginService = (function () {
    function LoginService() {
        this.subject = new Subject_1.Subject();
    }
    LoginService.prototype.sendMessage = function (id) {
        this.id = id;
        this.subject.next({ id: id });
        console.log("account id:" + id);
    };
    LoginService.prototype.clearMessage = function () {
        this.id = null;
        this.subject.next();
    };
    LoginService.prototype.getLast = function () {
        return this.id;
    };
    LoginService.prototype.getMessage = function () {
        return this.subject.asObservable();
    };
    return LoginService;
}());
LoginService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [])
], LoginService);
exports.LoginService = LoginService;
var OrderService = (function () {
    function OrderService() {
        this.subject = new Subject_1.Subject();
    }
    OrderService.prototype.sendMessage = function (id) {
        this.id = id;
        this.subject.next({ id: id });
        console.log("account id:" + id);
    };
    OrderService.prototype.clearMessage = function () {
        this.id = null;
        this.subject.next();
    };
    OrderService.prototype.getLast = function () {
        return this.id;
    };
    OrderService.prototype.getMessage = function () {
        return this.subject.asObservable();
    };
    return OrderService;
}());
OrderService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [])
], OrderService);
exports.OrderService = OrderService;
var GetServiceUrlService = (function () {
    function GetServiceUrlService(http) {
        this.http = http;
    }
    GetServiceUrlService.prototype.getUrl = function (name) {
        return this.http.get('/call/' + name)
            .map(function (response) { return response.json(); });
    };
    GetServiceUrlService.prototype.makeIPExternal = function (uri) {
        var aLink = document.createElement("a");
        aLink.href = uri;
        return self.location.protocol + "//" + self.location.hostname + ":" + aLink.port;
    };
    GetServiceUrlService.prototype.getURIByName = function (name) {
        var returnURI = { uri: '' };
        this.getUrl(name).subscribe(function (uriJson) { return returnURI.uri = uriJson.uri; });
        return returnURI;
    };
    return GetServiceUrlService;
}());
GetServiceUrlService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], GetServiceUrlService);
exports.GetServiceUrlService = GetServiceUrlService;
//# sourceMappingURL=services.js.map