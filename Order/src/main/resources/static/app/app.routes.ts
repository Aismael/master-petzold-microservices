import { Routes } from '@angular/router';
import {HomeComponent} from "./inlay.sites/home.components";
import {ChooseComponent} from "./inlay.sites/choose.components";
import {OrderComponent} from "./inlay.sites/order.component";
import {FavoriteComponent} from "./inlay.sites/favorite.component";
export const routes: Routes = [
    { path: '', redirectTo: "/home", pathMatch: "full" },
    { path: 'home', component:  HomeComponent},
    { path: 'choose', component: ChooseComponent,
        children: [
            { path: 'order', component: OrderComponent },
            { path: 'favorite', component: FavoriteComponent }
        ]
    }
];