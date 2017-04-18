import {GetDatasByPath, GetPathsService} from "../app.rest.paths";
import {ErrorService, GetServiceUrlService, LoginService, OrderService, ShopService} from "./services";
import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {FormControl} from "@angular/forms";
declare var $: any;


@Component({
    selector: "app-order",
    template: `
        <div class="ui two column middle aligned very relaxed stackable grid">
            <app-order-items class="center aligned column"></app-order-items>
            <div class="ui vertical divider"></div>
            <app-order-order class="center aligned column"></app-order-order>
        </div>
        <div class="ui section divider"></div>
        <div class="ui two column middle aligned very relaxed stackable grid">
            <app-order-favorite class="center aligned column"></app-order-favorite>
            <div class="divider-column">
                <div class="ui vertical divider">
                    Or
                </div>
            </div>
            <app-order-pay class="center aligned column"></app-order-pay>
        </div>
    `
})

export class OrderComponent {
    config: any = null;
    userId: any = null;

    constructor(getPathsService: GetPathsService, private loginService: LoginService) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
        this.userId = loginService.getLast();
        loginService.getMessage().subscribe(message => {
            if (message) {
                this.userId = message.id
            }
        });
    }
}

@Component({
    selector: "app-order-items",
    template: `
        <sm-segment>
            <h5>Items</h5>
            <sm-input icon="search" [(model)]="searchString" class="fluid" placeholder="Search..."></sm-input>
            <div class="ui divider"></div>
            <sm-list class="divided">
                <div *ngFor="let item of data.items  | smArraySearch:searchString">
                    <sm-item *ngIf="!item.on">
                        <div class="ui two column middle aligned very relaxed stackable grid">
                            <div class="center aligned column">
                                <sm-accordion [options]="{ on: 'click' }" class="fluid">
                                    <sm-accordion-item>
                                        <accordion-title>
                                            <div>
                                                {{item.name}}
                                            </div>
                                        </accordion-title>
                                        <accordion-content>
                                            <div>
                                                {{item.details}}
                                                |
                                                <i *ngIf="item.allergens" class="big warning circle icon"></i>
                                            </div>
                                        </accordion-content>
                                    </sm-accordion-item>
                                </sm-accordion>
                            </div>
                            <div class="center aligned column">
                                <div class="ui three column grid">
                                    <div (click)="submit(item)" class="column">
                                        <sm-checkbox label=" " [control]="toggleControl"
                                                     type="toggle"></sm-checkbox>
                                    </div>
                                    <div class=" column">
                                        <h3>{{item.currency}}</h3>
                                    </div>
                                    <div class="column">
                                        <i class="circular dollar icon"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </sm-item>
                </div>
            </sm-list>
        </sm-segment>    `
})
export class OrderitemsComponent {
    error: any;
    data = {items: ""};
    config: any;
    html: string;
    @Input() toggleControl: FormControl = new FormControl(false);


    constructor(getPathsService: GetPathsService, public getDatasByPath: GetDatasByPath, private errorService: ErrorService, private shopService: ShopService) {
        getPathsService.getPathsData().subscribe(config => {
            this.config = config.config.view;
            this.loadItems()
        });
    }


    submit(item: any) {
        if (!item.on) {
            item.on = true;
            this.shopService.sendMessage(item);
        } else {
            item.on = false;
        }
    }

    loadItems() {
        this.getDatasByPath.getPathsData(
            this.config.item.path +
            this.config.item.all.path).subscribe(
            data => {
                this.data.items = data;
            },
            (err) => {
                this.errorHandle("Mail", err)

            })
    }

    errorHandle(msg: string, err: any) {
        this.errorService.sendMessage(msg);
        this.error = err
    }
}

@Component({
    selector: "app-order-order",
    template: `
        <sm-menu title="Shopping Card" class="fluid vertical menu">
            <a sm-item  *ngFor="let item of holder.items" [icon]="item.name">
                <div class="ui three column grid">
                    <div class=" column">
                    {{item?.name}}
                    </div>
                    <div class=" column">
                    <input type="number" placeholder="count" [(ngModel)]="item.count" #ctrl="ngModel" style="max-width: 2vw">
                    </div>
                    <div class=" column">
                    <i (click)="submit(item)" class="circular red delete calendar icon"></i>
                    </div>
                </div>
            </a>
        </sm-menu>
    `
})
export class OrderOrderComponent {
    number: number;
    indexE: { i: null };
    html: string;
    holder = {items: new Array()};

    isInArray(array: any, itemV: any, indexV: any): boolean {
        var i = false;
        var index = 0;
        array.forEach(function (item: any) {
            index++;
            if (item.id === itemV.id) {
                indexV.i = index;
                i = true;
            }
        })
        return i;

    }

    constructor(private shopService: ShopService) {
        this.indexE = {i: null};
        shopService.getMessage().subscribe(message => {
            if (message) {
                if (!this.isInArray(this.holder.items, message, this.indexE)) {
                    message.count=1;
                    this.holder.items.push(message)
                } else {
                    if (this.indexE.i)
                        this.holder.items.splice((Number)(this.indexE.i) - 1, 1);
                }
            }
        });
        this.holder.items = [];
    }

    submit(item: any) {
        if (!item.on) {
            item.on = true;
        } else {
            item.on = false;
            this.shopService.sendMessage(item);
        }
    }
}

@Component({
    selector: "app-order-favorite",
    template: `
        <sm-menu title="Save & Pay" class="fluid vertical menu">
            <a sm-item *ngFor="let item of items" [icon]="item.icon">{{item?.title}}</a>
        </sm-menu>
    `
})
export class OrderFavoriteComponent {
    html: string;
    items: Array<any> = [];

    constructor() {
        this.items = [{
            "title": "Name it",
            "link": "Home",
            "icon": "gamepad"
        }, {
            "title": "Pay",
            "link": "Chanel",
            "icon": "video camera"
        }];
    }
}
interface DynamicURI {
    uri: string
}
@Component({
    selector: "app-order-pay",
    template: `
        <div class="ui massive green labeled icon button" (click)="jump()">
            <i class="credit card alternative icon"></i>
            Pay
        </div>
    `
})
export class OrderPayComponent {
    orderId: any;
    userId: number;

    constructor(private getServiceUrlService: GetServiceUrlService, private router: Router, private loginService: LoginService, private orderService: OrderService) {
        this.userId = loginService.getLast();
        loginService.getMessage().subscribe(message => {
            if (message) {
                this.userId = message.id
            }
        });
        this.orderId = orderService.getLast();
        orderService.getMessage().subscribe(message => {
            if (message) {
                this.orderId = message.id
            }
        });
    }

    makeIPExternal(uri: string): string {
        return this.getServiceUrlService.makeIPExternal(uri);
    }

    jump() {
        var returnURI: DynamicURI = {uri: ""};
        this.getServiceUrlService.getUrl("BILLING").subscribe(uriJson => {
            returnURI.uri = this.makeIPExternal(uriJson.uri) + "/?orderId=" + this.orderId + "&&accountId=" + this.userId;
            window.location.href = this.makeIPExternal(returnURI.uri)

        })
    }
}

