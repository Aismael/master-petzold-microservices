import {Routes} from "@angular/router";
import {emptyComponent} from "./app.components.inlay.sites";
import {BillComponent} from "./inlay.sites/bill.component";
import {PayComponent} from "./inlay.sites/pay.components";
export const routes: Routes = [
    {path: "", redirectTo: "bill", pathMatch: "full"},
    {
        path: "bill", component: BillComponent,
        children: [
            {
                path: "pay", component: PayComponent,
                children: [
                    {path: "make", component: emptyComponent}
                ]
            }
        ]
    }
];