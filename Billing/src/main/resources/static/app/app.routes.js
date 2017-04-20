"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var home_components_1 = require("./inlay.sites/home.components");
var choose_components_1 = require("./inlay.sites/choose.components");
var order_component_1 = require("./inlay.sites/order.component");
var favorite_component_1 = require("./inlay.sites/favorite.component");
exports.routes = [
    { path: '', redirectTo: "/home", pathMatch: "full" },
    { path: 'home', component: home_components_1.HomeComponent },
    { path: 'choose', component: choose_components_1.ChooseComponent,
        children: [
            { path: 'order', component: order_component_1.OrderComponent },
            { path: 'favorite', component: favorite_component_1.FavoriteComponent }
        ]
    }
];
//# sourceMappingURL=app.routes.js.map