import {Routes} from "@angular/router";
import {emptyComponent} from "./app.components.inlay.sites";
import {BillComponent} from "./inlay.sites/bill.component";
import {PayComponent} from "./inlay.sites/pay.components";
import {MakeComponent} from "./inlay.sites/make.component";
export const routes: Routes = [
    {path: "", redirectTo: "bill", pathMatch: "full"},
    {
        path: "bill", component: BillComponent,
        children: [
            {
                path: "pay", component: PayComponent,
                children: [
                    {path: "make", component: MakeComponent}
                ]
            }
        ]
    }
];