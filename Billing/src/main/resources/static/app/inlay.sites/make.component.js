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
/**
 * Created by Aismael on 22.04.2017.
 */
var MakeComponent = (function () {
    function MakeComponent(getPathsService, loginService, errorService, getDatasByPath, postDatasByPath, router) {
        var _this = this;
        this.errorService = errorService;
        this.getDatasByPath = getDatasByPath;
        this.postDatasByPath = postDatasByPath;
        this.router = router;
        this.actualBank = { name: "noname", id: 0 };
        this.id = loginService.getLast();
        getPathsService.getPathsData().subscribe(function (config) {
            _this.config = config.config.view;
            _this.load();
        });
    }
    MakeComponent.prototype.load = function () {
        var _this = this;
        this.getDatasByPath.getPathsData(this.config.bank.path +
            this.config.bank.all.path).subscribe(function (data) {
            _this.banks = data.bankDTOList;
            _this.actualBank = data.bankDTOList[0];
            console.log(data);
        }, function (err) {
            _this.errorService.sendMessage("Banks doesnt exist" + err);
        });
    };
    MakeComponent.prototype.click = function (bank) {
        this.actualBank = bank;
        console.log(this.actualBank);
    };
    MakeComponent.prototype.make = function () {
        var _this = this;
        this.MakeNewBankAccountDTO = {
            accountId: this.id,
            bankId: this.actualBank.id
        };
        this.postDatasByPath.postPathsData(this.config.bankAccount.path +
            this.config.bankAccount.one.path +
            this.config.bankAccount.one.account.path, this.MakeNewBankAccountDTO)
            .subscribe(function (data) {
            console.log(data);
            _this.router.navigate(["/bill/pay"]);
        }, function (err) {
            _this.errorHandle("Paying", err);
        });
    };
    return MakeComponent;
}());
MakeComponent = __decorate([
    core_1.Component({
        selector: "app-make",
        template: "\n        <div class=\"ui grid\">\n            <div class=\"four wide column\">\n                <sm-menu title=\"Available Banks\" class=\"fluid vertical menu inverted blue\">\n                    <a sm-item *ngFor=\"let bank of banks\" [icon]=\"bank.name\"\n                       (click)=\"click(bank)\">{{bank?.name}}\n                        <i class=\"arrow circle right icon\" *ngIf=\"bank?.name===actualBank.name\">\n                        </i>\n                    </a>\n                </sm-menu>\n            </div>\n            <div class=\"twelve wide stretched column\">\n                <div class=\"ui segment\">\n                    <h1> make an new Acoount at the {{actualBank.name}} Bank\n                    </h1>\n                </div>\n                <div class=\"ui massive red labeled icon button\" (click)=\"make()\">\n                    <i class=\"send icon\"></i>\n                    request new Account\n                </div>\n            </div>\n        </div>"
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService,
        services_1.LoginService,
        services_1.ErrorService,
        app_rest_paths_1.GetDatasByPath,
        app_rest_paths_1.PostDatasByPath,
        router_1.Router])
], MakeComponent);
exports.MakeComponent = MakeComponent;
//# sourceMappingURL=make.component.js.map