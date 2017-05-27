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
var FavoriteObject = (function () {
    function FavoriteObject() {
    }
    return FavoriteObject;
}());
var FavoriteIdService = (function () {
    function FavoriteIdService() {
    }
    FavoriteIdService.prototype.getId = function () {
        return this.id;
    };
    return FavoriteIdService;
}());
FavoriteIdService = __decorate([
    core_1.Injectable()
], FavoriteIdService);
exports.FavoriteIdService = FavoriteIdService;
var ShopBasketService = (function () {
    function ShopBasketService() {
        this.basket = { items: new Array() };
    }
    ShopBasketService.prototype.getBasket = function () {
        return this.basket;
    };
    return ShopBasketService;
}());
ShopBasketService = __decorate([
    core_1.Injectable()
], ShopBasketService);
exports.ShopBasketService = ShopBasketService;
var FavoriteService = (function () {
    function FavoriteService(basketService, loginService) {
        this.basketService = basketService;
        this.loginService = loginService;
        this.subject = new Subject_1.Subject();
        this.favorite = null;
    }
    FavoriteService.prototype.sendMessage = function (favorite) {
        this.favorite = favorite;
        this.subject.next(favorite);
    };
    FavoriteService.prototype.clearMessage = function () {
        this.favorite = null;
        this.subject.next();
    };
    FavoriteService.prototype.getLast = function () {
        return this.favorite;
    };
    FavoriteService.prototype.getMessage = function () {
        return this.subject.asObservable();
    };
    FavoriteService.prototype.makeOrderfromBasket = function () {
        this.favorite = new FavoriteObject();
        var favorite = this.favorite;
        favorite.itemSetStubDTOS = [];
        this.basketService.getBasket().items.forEach(function (data) {
            favorite.itemSetStubDTOS.push(new ItemSetStubDTO(data.count, data.id));
        });
        this.favorite.accountId = this.loginService.getLast();
        console.log(JSON.stringify(this.favorite));
        this.sendMessage(favorite);
        return favorite;
    };
    return FavoriteService;
}());
FavoriteService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [ShopBasketService, LoginService])
], FavoriteService);
exports.FavoriteService = FavoriteService;
var OrderObject = (function () {
    function OrderObject() {
        this.date = new Date();
    }
    return OrderObject;
}());
var ItemSetStubDTO = (function () {
    function ItemSetStubDTO(count, itemID) {
        this.itemID = itemID;
        this.count = count;
    }
    return ItemSetStubDTO;
}());
var OrderService = (function () {
    function OrderService(basketService, loginService) {
        this.basketService = basketService;
        this.loginService = loginService;
        this.subject = new Subject_1.Subject();
        this.order = null;
    }
    OrderService.prototype.makeOrderfromBasket = function () {
        this.order = new OrderObject();
        var order = this.order;
        order.itemSetStubDTOS = [];
        this.basketService.getBasket().items.forEach(function (data) {
            order.itemSetStubDTOS.push(new ItemSetStubDTO(data.count, data.id));
        });
        this.order.accountId = this.loginService.getLast();
        console.log(JSON.stringify(this.order));
        this.sendMessage(order);
        return order;
    };
    OrderService.prototype.sendMessage = function (order) {
        this.order = order;
        this.subject.next(order);
    };
    OrderService.prototype.clearMessage = function () {
        this.order = null;
        this.subject.next();
    };
    OrderService.prototype.getLast = function () {
        return this.order;
    };
    OrderService.prototype.getMessage = function () {
        return this.subject.asObservable();
    };
    return OrderService;
}());
OrderService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [ShopBasketService, LoginService])
], OrderService);
exports.OrderService = OrderService;
var GetServiceUrlService = (function () {
    function GetServiceUrlService(http) {
        this.http = http;
    }
    GetServiceUrlService.prototype.getUrl = function (name) {
        return this.http.get("/call/" + name)
            .map(function (response) { return response.json(); });
    };
    GetServiceUrlService.prototype.makeIPExternal = function (uri) {
        var aLink = document.createElement("a");
        aLink.href = uri;
        return self.location.protocol + "//" + self.location.hostname + ":" + aLink.port;
    };
    GetServiceUrlService.prototype.getURIByName = function (name) {
        var returnURI = { uri: "" };
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
var ShopService = (function () {
    function ShopService() {
        this.subject = new Subject_1.Subject();
    }
    ShopService.prototype.sendMessage = function (item) {
        item.on = false;
        this.item = item;
        this.subject.next(item);
    };
    ShopService.prototype.clearMessage = function () {
        this.item = null;
        this.subject.next();
    };
    ShopService.prototype.getLast = function () {
        return this.item;
    };
    ShopService.prototype.getMessage = function () {
        return this.subject.asObservable();
    };
    return ShopService;
}());
ShopService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [])
], ShopService);
exports.ShopService = ShopService;
//# sourceMappingURL=services.js.map