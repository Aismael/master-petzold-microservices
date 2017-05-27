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
/**
 * Created by Aismael on 13.04.2017.
 */
var core_1 = require("@angular/core");
var app_rest_paths_1 = require("../app.rest.paths");
var router_1 = require("@angular/router");
var services_1 = require("./services");
var forms_1 = require("@angular/forms");
var HomeComponent = (function () {
    function HomeComponent(getPathsService, loginService, errorService) {
        var _this = this;
        this.config = null;
        getPathsService.getPathsData().subscribe(function (config) { return _this.config = config.config.view; });
        loginService.clearMessage();
        errorService.clearMessage();
    }
    return HomeComponent;
}());
HomeComponent = __decorate([
    core_1.Component({
        selector: "app-home",
        template: "\n        <div class=\"ui section divider\"></div>\n        <div class=\"ui two column middle aligned very relaxed stackable grid\">\n            <app-home-left class=\"column\"></app-home-left>\n            <div class=\"divider-column\">\n                <div class=\"ui vertical divider\">\n                    Or\n                </div>\n            </div>\n            <app-home-right class=\"column\"></app-home-right>\n        </div>\n        <app-home-error></app-home-error>"
    }),
    __metadata("design:paramtypes", [app_rest_paths_1.GetPathsService, services_1.LoginService, services_1.ErrorService])
], HomeComponent);
exports.HomeComponent = HomeComponent;
var HomeLeftComponent = (function () {
    function HomeLeftComponent(fb, getPathsService, getDatasByPath, router, errorService, loginService) {
        var _this = this;
        this.getDatasByPath = getDatasByPath;
        this.router = router;
        this.errorService = errorService;
        this.loginService = loginService;
        this.config = null;
        this.formSubmited = false;
        getPathsService.getPathsData().subscribe(function (config) { return _this.config = config.config.view; });
        this.form = fb.group({
            emailControl: ["", forms_1.Validators.compose([forms_1.Validators.required, forms_1.Validators.email])],
            nameControl: ["", forms_1.Validators.compose([forms_1.Validators.required, forms_1.Validators.minLength(4)])],
        });
    }
    HomeLeftComponent.prototype.checkAccount = function () {
        if (this.form.valid) {
            this.formSubmited = true;
            this.checkMail(this.form.get("emailControl").value);
        }
    };
    HomeLeftComponent.prototype.checkMail = function (mail) {
        var _this = this;
        this.getDatasByPath.getPathsData(this.config.account.path +
            this.config.account.one.path +
            this.config.account.one.mail.path +
            "/" + mail + "/").subscribe(function (data) {
            _this.checkName(_this.form.get("nameControl").value);
        }, function (err) {
            _this.errorHandle("Mail", err);
        });
    };
    HomeLeftComponent.prototype.checkName = function (name) {
        var _this = this;
        this.getDatasByPath.getPathsData(this.config.account.path +
            this.config.account.one.path +
            this.config.account.one.name.path +
            "/" + name + "/").subscribe(function (data) {
            _this.loginService.sendMessage(data.id);
            _this.router.navigate(["/choose"]);
        }, function (err) {
            _this.errorHandle("Name", err);
        });
    };
    HomeLeftComponent.prototype.errorHandle = function (msg, err) {
        this.errorService.sendMessage(msg);
        this.error = err;
    };
    return HomeLeftComponent;
}());
HomeLeftComponent = __decorate([
    core_1.Component({
        selector: "app-home-left",
        template: "\n        <form class=\"ui form\" [formGroup]=\"form\">\n            <sm-loader [complete]=\"!formSubmited\" class=\"inverted\" text=\"Loading...\"></sm-loader>\n            <div class=\"field\">\n                    <sm-input label=\"UserName\" icon=\"user\" class=\"left\" [control]=\"form.controls.nameControl\" placeholder=\"Enter name...\"></sm-input>\n            </div>\n            <div class=\"field\">\n                    <sm-input label=\"E-m@il\" icon=\"mail\" class=\"left\" [control]=\"form.controls.emailControl\" placeholder=\"Enter e-mail...\"></sm-input>\n            </div>\n            <sm-button [disabled]=\"!form.valid\" class=\" blue submit button\" (click)=\"checkAccount()\">Login</sm-button>\n        </form>\n    "
    }),
    __metadata("design:paramtypes", [forms_1.FormBuilder, app_rest_paths_1.GetPathsService, app_rest_paths_1.GetDatasByPath, router_1.Router, services_1.ErrorService, services_1.LoginService])
], HomeLeftComponent);
exports.HomeLeftComponent = HomeLeftComponent;
var HomeRightComponent = (function () {
    function HomeRightComponent(fb, getPathsService, postDatasByPath, errorService, router, loginService) {
        var _this = this;
        this.postDatasByPath = postDatasByPath;
        this.errorService = errorService;
        this.router = router;
        this.loginService = loginService;
        this.config = null;
        this.formSubmited = false;
        getPathsService.getPathsData().subscribe(function (config) { return _this.config = config.config.view; });
        this.form = fb.group({
            emailControl: ["", forms_1.Validators.compose([forms_1.Validators.required, forms_1.Validators.email])],
            nameControl: ["", forms_1.Validators.compose([forms_1.Validators.required, forms_1.Validators.minLength(4)])],
        });
    }
    HomeRightComponent.prototype.click = function () {
        var _this = this;
        if (this.form.valid) {
            this.formSubmited = true;
            this.postDatasByPath.postPathsData(this.config.account.path +
                this.config.account.one.path, {
                id: null,
                name: this.form.get("nameControl").value,
                mail: this.form.get("emailControl").value
            })
                .subscribe(function (data) {
                _this.loginService.sendMessage(data.id);
                _this.router.navigate(["/choose"]);
            }, function (err) {
                _this.errorHandle("new account", err);
            });
        }
    };
    HomeRightComponent.prototype.errorHandle = function (msg, err) {
        this.errorService.sendMessage(msg);
        this.error = err;
    };
    return HomeRightComponent;
}());
HomeRightComponent = __decorate([
    core_1.Component({
        selector: "app-home-right",
        template: "\n        <form class=\"ui form\" [formGroup]=\"form\">\n            <sm-loader [complete]=\"!formSubmited\" class=\"inverted\" text=\"Loading...\"></sm-loader>\n            <div class=\"field\">\n                <sm-input label=\"UserName\" icon=\"user\" class=\"left\" [control]=\"form.controls.nameControl\" placeholder=\"Enter name...\"></sm-input>\n            </div>\n            <div class=\"field\">\n                <sm-input label=\"E-m@il\" icon=\"mail\" class=\"left\" [control]=\"form.controls.emailControl\" placeholder=\"Enter e-mail...\"></sm-input>\n            </div>\n            <sm-button [disabled]=\"!form.valid\" class=\" green labeled icon button\" icon=\"signup\" (click)=\"click()\">SignUp</sm-button>\n        </form>\n        \n    "
    }),
    __metadata("design:paramtypes", [forms_1.FormBuilder, app_rest_paths_1.GetPathsService, app_rest_paths_1.PostDatasByPath, services_1.ErrorService, router_1.Router, services_1.LoginService])
], HomeRightComponent);
exports.HomeRightComponent = HomeRightComponent;
var HomeErrorComponent = (function () {
    function HomeErrorComponent(e) {
        var _this = this;
        this.e = e;
        this.text = "";
        e.getMessage().subscribe(function (message) {
            if (message) {
                $(".ui.basic.modal").modal("show");
                _this.text = message.text;
            }
        });
    }
    HomeErrorComponent.prototype.ngOnDestroy = function () {
        $(".ui.basic.modal").remove();
    };
    HomeErrorComponent.prototype.clearMessage = function () {
        this.e.clearMessage();
    };
    return HomeErrorComponent;
}());
HomeErrorComponent = __decorate([
    core_1.Component({
        selector: "app-home-error",
        template: "\n        <div class=\"ui basic modal\">\n            <div class=\"ui icon header\">\n                <i class=\"error icon\"></i>\n                {{text}} is wrong\n            </div>\n            <div class=\"content\">\n                <p>your {{text}} is incorrect</p>\n            </div>\n            <div class=\"actions\">\n                <div class=\"ui green ok inverted button\">\n                    <i class=\"checkmark icon\" (click)=\"clearMessage()\"></i>\n                    OK\n                </div>\n            </div>\n        </div>"
    }),
    __metadata("design:paramtypes", [services_1.ErrorService])
], HomeErrorComponent);
exports.HomeErrorComponent = HomeErrorComponent;
//# sourceMappingURL=home.components.js.map