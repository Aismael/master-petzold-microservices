"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var bill_component_1 = require("./inlay.sites/bill.component");
var pay_components_1 = require("./inlay.sites/pay.components");
var make_component_1 = require("./inlay.sites/make.component");
exports.routes = [
    { path: "", redirectTo: "bill", pathMatch: "full" },
    {
        path: "bill", component: bill_component_1.BillComponent,
        children: [
            {
                path: "pay", component: pay_components_1.PayComponent,
                children: [
                    { path: "make", component: make_component_1.MakeComponent }
                ]
            }
        ]
    }
];
//# sourceMappingURL=app.routes.js.map