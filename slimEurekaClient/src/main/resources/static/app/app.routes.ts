import {Routes} from "@angular/router";
import {HomeComponent} from "./inlay.sites/home.component";

export const routes: Routes = [
    {path: "", redirectTo: "home", pathMatch: "full"},
    {
        path: "home", component: HomeComponent,
    }
];