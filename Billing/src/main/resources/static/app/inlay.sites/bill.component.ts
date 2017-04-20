import {Component} from "@angular/core";
import {GetDatasByPath, GetPathsService} from "../app.rest.paths";
import {ErrorService, FromOrderService, LoginService, ShopBasketService, ShopService} from "./services";
import {NavigationEnd, Router} from "@angular/router";
@Component({
    selector: "app-bill",
    template: `
        <div class="ui section divider"></div>
        <div class="ui two column middle aligned very relaxed stackable grid" *ngIf="sub">
            <app-order-order class="column"></app-order-order>
            <div class="ui vertical divider"></div>
            <app-bill-pay class="center aligned column"></app-bill-pay>
        </div>
        <router-outlet></router-outlet>
        <app-home-error></app-home-error>`
})

export class BillComponent {
    sub: boolean;
    data: any;
    private orderId: number;
    config: any = null;
    private id: number;

    constructor(getPathsService: GetPathsService,
                loginService: LoginService,
                private errorService: ErrorService,
                fromOrderService: FromOrderService,
                private getDatasByPath: GetDatasByPath,
                private basket: ShopBasketService,
                private shop: ShopService,
                private router: Router
    ) {
        this.router.events.subscribe((event) => {
                if (event instanceof NavigationEnd) {
                    this.sub = !(event.urlAfterRedirects.includes("pay"));

                }
            }
        );
        getPathsService.getPathsData().subscribe(config => {
            this.config = config.config.view
            this.load()
        });
        this.id = loginService.getLast();
        this.orderId = fromOrderService.getLast();
        errorService.clearMessage();
    }

    load() {
        this.getDatasByPath.getPathsData(
            this.config.order.path +
            this.config.order.one.path +
            this.config.order.one.idAndAccount.path +
            "/" + this.orderId + "/" + this.id).subscribe(
            data => {
                this.shop.sendMessage(data);
                this.basket.basket.items = data.itemSetStubDTOS;
            },
            (err) => {
                this.errorService.sendMessage("Order or Account doesnt exist" + err);

            })
    }
}

@Component({
    selector: "app-order-order",
    template: `
        <sm-menu title="Shopping Card" class="fluid vertical menu">
            <a sm-item>
                Order from {{date}}
            </a>
            <a sm-item *ngFor="let item of basket.getBasket().items" [icon]="item.name">
                <div class="ui grid">
                    <div class="eight wide column">
                        {{item?.name}}
                    </div>
                    <div class="three wide column">
                        <h3 class="right aligned">{{item.count}} x </h3>
                    </div>
                    <div class="five wide column">
                        <div class="ui two column middle aligned very relaxed stackable grid">
                            <div class="column">
                                <h3 class="right aligned">{{item.currency}}</h3>
                            </div>
                            <div class="column">
                                <i class="circular dollar icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </a>
            <div class="ui section divider"></div>
            <a sm-item class="right aligned">
                <div class="ui grid">
                    <div class="eight wide column">
                        <h3 class="right aligned">Sum</h3>
                    </div>
                    <div class="three wide column">
                    </div>
                    <div class="five wide column">

                        <div class="ui two column middle aligned very relaxed stackable grid">
                            <div class="column">
                                <h3 class="right aligned">{{sum}}</h3>
                            </div>
                            <div class="column">
                                <i class="circular dollar icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </a>
        </sm-menu>
    `
})
export class OrderOrderComponent {
    sum: 1;
    date: Date;
    number: number;
    html: string;

    constructor(private basket: ShopBasketService, private shop: ShopService) {

        shop.getMessage().subscribe(data => {
            this.date = new Date(data.date);
            this.sum = data.sum
        })
    }

}
@Component({
    selector: "app-bill-pay",
    template: `
        <div class="ui massive green labeled icon button" (click)="click()">
            <i class="credit card alternative icon"></i>
            Pay
        </div>
    `
})
export class BillPayComponent {
    constructor(private router: Router) {

    }

    click() {
        this.router.navigate(["/bill/pay"])
    }
}