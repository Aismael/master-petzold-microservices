import {GetDatasByPath, GetPathsService, PostDatasByPath} from "../app.rest.paths";
import {ErrorService, FavoriteService, GetServiceUrlService, LoginService, OrderService, ShopBasketService, ShopService} from "./services";
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
         <app-home-error></app-home-error>`
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
                                    <div class="right aligned column">
                                        <h3 class="right aligned">{{item.currency}}</h3>
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
            this.shopService.sendMessage(item);
            item.on = true;
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
            <a sm-item *ngFor="let item of basket.getBasket().items" [icon]="item.name">
                <div class="ui three column grid">
                    <div  class=" column">
                        <i *ngIf="item.on" (click)="submit(item)" class="large red remove circle icon"></i>
                    </div>
                    <div class="right aligned column">
                        {{item?.name}}
                    </div>
                    <div class=" column">
                        <input type="number" placeholder="count" [(ngModel)]="item.count" #ctrl="ngModel" style="max-width: 2vw">
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

    fromItemList(item: any){
        if(item.on==null){
            return false
        }else return true
    }
    constructor(private shopService: ShopService,private basket: ShopBasketService) {
        this.indexE = {i: null};
        shopService.getMessage().subscribe(message => {
            if (message) {
                console.log()
                if (!this.isInArray(this.basket.getBasket().items, message, this.indexE)) {
                    message.count = 1;
                    this.basket.getBasket().items.push(message)
                } else {
                    if (this.indexE.i)
                        this.basket.getBasket().items.splice((Number)(this.indexE.i) - 1, 1);
                }
            }
        });
        this.basket.getBasket().items = [];
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
            <a sm-item [icon]="items[0].icon">
                <div class="ui two column grid">
                    <div class="left aligned column">
                        {{items[0].title}}
                    </div>
                    <div class="right aligned column">
                        <input type="number" placeholder="favorite" [(ngModel)]="name" #ctrl="ngModel" style="max-width: 6vw">
                    </div>
                </div>
            </a>
            <a sm-item [icon]="items[1].icon">
                <div class="ui two column grid">
                    <div class="left aligned column">
                        {{items[1].title}}
                    </div>
                    <div class="right aligned column">

                        <div class="ui big blue button" (click)="jump()">
                            Pay
                        </div>
                    </div>
                </div>
            </a>
        </sm-menu>
    `
})
export class OrderFavoriteComponent {
    config: any = null;
    html: string;
    items: Array<any> = [];
    name: string;
    orderId: any;
    userId: number;

    constructor(private getServiceUrlService: GetServiceUrlService,
                private loginService: LoginService,
                private favoriteService: FavoriteService,
                getPathsService: GetPathsService,
                private postDatasByPath: PostDatasByPath,
                private errorService: ErrorService
    ) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
        this.items = [{
            "title": "1. Name it",
            "link": "Home",
            "icon": "tag big"
        }, {
            "title": "2. and",
            "link": "Chanel",
            "icon": "credit card alternative big"
        }];
        this.userId = loginService.getLast();
        loginService.getMessage().subscribe(message => {
            if (message) {
                this.userId = message.id
            }
        });
    }

    makeIPExternal(uri: string): string {
        return this.getServiceUrlService.makeIPExternal(uri);
    }

    jump() {
        var favorite=this.favoriteService.makeOrderfromBasket();
        this.postDatasByPath.postPathsData(this.config.favorite.path +
            this.config.favorite.one.path,
            favorite)
            .subscribe(data => {
                    console.log(data)

                },
                (err) => {
                    this.errorHandle("order Saving", err)
                });
    }
    errorHandle(msg: string, err: any) {
        this.errorService.sendMessage(msg);
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
    config: any = null;
    orderId: any;
    userId: number;

    constructor(private getServiceUrlService: GetServiceUrlService,
                private loginService: LoginService,
                private orderService: OrderService,
                private postDatasByPath: PostDatasByPath,
                private getPathsService: GetPathsService,
                private errorService: ErrorService,
                private favoriteService: FavoriteService
    ) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
        this.userId = loginService.getLast();
        loginService.getMessage().subscribe(message => {
            if (message) {
                this.userId = message.id
            }
        });
        favoriteService.getMessage().subscribe(message=>this.jump())
    }

    makeIPExternal(uri: string): string {
        return this.getServiceUrlService.makeIPExternal(uri);
    }

    jump() {
        var order=this.orderService.makeOrderfromBasket();
        this.postDatasByPath.postPathsData(this.config.order.path +
            this.config.order.one.path,
            order)
            .subscribe(data => {
                this.orderId=data;
                    console.log(data)
                    var returnURI: DynamicURI = {uri: ""};
                    this.getServiceUrlService.getUrl("BILLING").subscribe(uriJson => {
                        returnURI.uri = this.makeIPExternal(uriJson.uri) + "/?orderId=" + this.orderId + "&&accountId=" + this.userId;
                        window.location.href = this.makeIPExternal(returnURI.uri)

                    })
                },
                (err) => {
                    this.errorHandle("order Saving", err)
                });
    }
    errorHandle(msg: string, err: any) {
        this.errorService.sendMessage(msg);
    }
}

