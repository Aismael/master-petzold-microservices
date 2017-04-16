import { Routes } from '@angular/router';
import {emptyComponent} from "./app.components.inlay.sites";
import {HomeComponent} from "./inlay.sites/home.components";
import {ChooseComponent} from "./inlay.sites/choose.components";
export const routes: Routes = [
    { path: '', redirectTo: "/home", pathMatch: "full" },
    { path: 'home', component:  HomeComponent},
    { path: 'choose', component: ChooseComponent,
        children: [
            { path: 'order', component: emptyComponent },
            { path: 'favorite', component: emptyComponent }
        ]
    }
];