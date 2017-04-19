import {GetDatasByPath, GetPathsService, PostDatasByPath} from "../app.rest.paths";
import {
    ErrorService, GetServiceUrlService, LoginService, OrderService, ShopBasketService, ShopService, FavoriteService,
    FavoriteIdService
} from "./services";
import {Component, Input} from "@angular/core";
import {FormControl} from "@angular/forms";
@Component({
    selector: "app-favorite",
    template: `
        <div class="ui two column middle aligned very relaxed stackable grid">
            <app-favorite-favorite class="center aligned column"></app-favorite-favorite>
            <div class="ui vertical divider"></div>
            <app-order-order class="center aligned column"></app-order-order>
        </div>
        <div class="ui section divider"></div>
        <div class="ui one column middle aligned very relaxed stackable grid">
            <app-favorite-pay class="center aligned column"></app-favorite-pay>
        </div>
        <app-home-error></app-home-error>`
})

export class FavoriteComponent {
    config: any = null;
    id: any = null;
    private sub: boolean;

    constructor(getPathsService: GetPathsService, private loginService: LoginService) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
        this.id = loginService.getLast();
        loginService.getMessage().subscribe(message => {
            if (message) {
                this.id = message.id
            }
        });
    }
}
@Component({
    selector: "app-favorite-favorite",
    template: `
        <sm-segment>
            <h5>Items</h5>
            <sm-input icon="search" [(model)]="searchString" class="fluid" placeholder="Search..."></sm-input>
            <div class="ui divider"></div>
            <sm-list class="divided">
                <div *ngFor="let favorite of data.favorites  | smArraySearch:searchString">
                    <sm-item *ngIf="!favorite.on">
                        <div class="ui two column middle aligned very relaxed stackable grid">
                            <div class="center aligned column">
                                <sm-accordion [options]="{ on: 'click' }" class="fluid">
                                    <sm-accordion-item>
                                        <accordion-title>
                                            <div>
                                                {{favorite.name}}
                                            </div>
                                        </accordion-title>
                                        <accordion-content>
                                            <div *ngFor="let item of favorite.itemSetStubDTOS">
                                                {{item.count}}x{{item.name}}
                                            </div>
                                        </accordion-content>
                                    </sm-accordion-item>
                                </sm-accordion>
                            </div>
                            <div class="center aligned column">
                                <div class="ui three column grid">
                                    <div (click)="submit(favorite)" class="column">
                                        <sm-checkbox label=" " [control]="toggleControl"
                                                     type="toggle"></sm-checkbox>
                                    </div>
                                    <div class="right aligned column">
                                        <h3 class="right aligned">{{favorite.count}}</h3>
                                    </div>
                                    <div class="column">
                                        <i class="circular tags icon"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </sm-item>
                </div>
            </sm-list>
        </sm-segment>    `
})
export class FavoriteFavoriteComponent {
    error: any;
    data = {favorites: ""};
    config: any;
    id: any = null;

    html: string;
    choosenOne = {on: true};
    @Input() toggleControl: FormControl = new FormControl(false);


    constructor(getPathsService: GetPathsService,
                public getDatasByPath: GetDatasByPath,
                private errorService: ErrorService,
                private shopService: ShopService,
                private loginService: LoginService,
                private shopBasketService: ShopBasketService,
                private favoriteIdService: FavoriteIdService
    ) {
        getPathsService.getPathsData().subscribe(config => {
            this.config = config.config.view;
            this.loadItems()
        });
        this.id = loginService.getLast();
        loginService.getMessage().subscribe(message => {
            if (message) {
                this.id = message.id
            }
        });
    }


    submit(favorite: any) {
        this.favoriteIdService.id=favorite.id;
        this.shopBasketService.getBasket().items = favorite.itemSetStubDTOS;
        this.choosenOne.on = false;
        this.choosenOne = favorite;
        if (!favorite.on) {
            favorite.on = true;
        } else {
            favorite.on = false;
        }
    }

    loadItems() {
        this.getDatasByPath.getPathsData(
            this.config.favorite.path +
            this.config.favorite.all.path +
            this.config.favorite.all.account.path +
            "/" + this.id
        ).subscribe(
            data => {

                this.data.favorites = data.list;
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
    selector: "app-favorite-pay",
    template: `
        <div class="ui massive green labeled icon button" (click)="jump()">
            <i class="credit card alternative icon"></i>
            Pay
        </div>
    `
})
export class FavoritePayComponent {
    config: any = null;
    orderId: any;
    userId: number;

    constructor(private getServiceUrlService: GetServiceUrlService,
                private loginService: LoginService,
                private favoriteIdService: FavoriteIdService,
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
        var favoriteId=this.favoriteIdService.id;
        this.postDatasByPath.postPathsData(
            this.config.favorite.path +
            this.config.favorite.one.path+
            this.config.favorite.one.order.path+
            "/"+favoriteId,
            favoriteId)
            .subscribe(data => {
                    this.orderId=data;
                    console.log(data)
                    var returnURI = {uri: ""};
                    this.getServiceUrlService.getUrl("BILLING").subscribe(uriJson => {
                        returnURI.uri = this.makeIPExternal(uriJson.uri) + "/?orderId=" + this.orderId + "&&accountId=" + this.userId;
                        console.log(returnURI.uri)
                        window.location.href = returnURI.uri
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

