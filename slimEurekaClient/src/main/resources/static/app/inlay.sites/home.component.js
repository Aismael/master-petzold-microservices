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
var HomeComponent = (function () {
    function HomeComponent(sanitizerArg) {
        this.sanitizerArg = sanitizerArg;
        this.inlayUrl = self.location.protocol + "//" + self.location.hostname + ":3000";
        this.sanitizer = sanitizerArg;
    }
    HomeComponent.prototype.doit = function () {
        $(document).ready(function () {
            $("#testFrame").load(function () {
                var doc = this.contentDocument || this.contentWindow.document;
                var target = doc.getElementById("target");
                target.innerHTML = "Found It!";
            });
        });
    };
    return HomeComponent;
}());
HomeComponent = __decorate([
    core_1.Component({
        selector: "app-home",
        template: "\n        <iframe id=\"testFrame\" style=\"height: 1000px;width:100%;border:none;overflow:hidden;\"\n                [src]=\"sanitizer.bypassSecurityTrustResourceUrl(inlayUrl)\"\n                onloadeddata=\"doit\"\n        >\n        </iframe>\n       "
    }),
    __metadata("design:paramtypes", [platform_browser_1.DomSanitizer])
], HomeComponent);
exports.HomeComponent = HomeComponent;
//# sourceMappingURL=home.component.js.map