import {Component} from "@angular/core";
import {GetDatasByPath, GetPathsService, PostDatasByPath} from "../app.rest.paths";
import {ErrorService, FromOrderService, GetServiceUrlService, LoginService, ShopService} from "./services";
import {NavigationEnd, Router} from "@angular/router";
@Component({
    selector: "app-bill",
    template: `
        <div class="ui grid">
            <div class="four wide column">
                <sm-menu title="Your Accounts" class="ui four  fluid  menu vertical inverted blue ">
                    <a sm-item *ngFor="let bankAccount of accounts" [icon]="bankAccount.name"
                       (click)="click(bankAccount)">{{bankAccount?.bankname}}</a>
                </sm-menu>
            </div>
            <div class="twelve wide stretched column">
                <div class="ui segment">
                    <h4>your Actual ammount for your Account at {{actualAccount.bankname}} is {{actualAccount.ammount}} $
                        and you have to pay {{sum}} $
                        <div class="ui section divider"></div>
                        so your new ammount would be {{actualAccount.ammount - sum}}
                        <div class="ui section divider"></div>
                    </h4>
                    <div class="ui massive green labeled icon button" (click)="pay()">
                        <i class="credit card alternative icon"></i>
                        Pay @ {{actualAccount.bankname}}
                    </div>
                </div>
            </div>
        </div>
        <div class="ui section divider"></div>
        <router-outlet></router-outlet>
    `
})

export class PayComponent {
    actualAccount: any;
    accounts: any;
    sum: any;
    sub2: boolean;
    orderId: number;
    id: number;
    private config: any;
    payDTO: {
        bankAccountId: any,
        orderId: any
    }

    constructor(getPathsService: GetPathsService,
                loginService: LoginService,
                private errorService: ErrorService,
                fromOrderService: FromOrderService,
                private getDatasByPath: GetDatasByPath,
                private router: Router,
                private shop: ShopService,
                private postDatasByPath: PostDatasByPath,
                private getServiceUrlService: GetServiceUrlService,) {
        this.payDTO={
            bankAccountId: 0,
                orderId: 0
        };
        this.id = loginService.getLast();
        this.orderId = fromOrderService.getLast();
        this.payDTO.orderId = fromOrderService.getLast();
        errorService.clearMessage();
        this.router.events.subscribe((event) => {
                if (event instanceof NavigationEnd) {
                    this.sub2 = !(event.urlAfterRedirects.includes("make"));

                }
            }
        );
        getPathsService.getPathsData().subscribe(config => {
            this.config = config.config.view
            this.load()
        });
        this.sum = shop.getLast().sum;
        this.actualAccount = {ammount: 0, bankname: "noname", id: 0}
    }

    pay() {
        this.payDTO.bankAccountId = this.actualAccount.id
        this.postDatasByPath.postPathsData(
            this.config.bankAccount.path +
            this.config.bankAccount.one.path +
            this.config.bankAccount.one.pay.path,
            this.payDTO)
            .subscribe(data => {
                    console.log(data)
                    var returnURI: any = {uri: ""};
                    this.getServiceUrlService.getUrl("ESL").subscribe(uriJson => {
                        returnURI.uri = this.makeIPExternal(uriJson.uri);
                        console.log(returnURI.uri)
                        window.location.href = returnURI.uri
                    })
                },
                (err) => {
                    this.errorHandle("Paying", err)
                });
    }

    errorHandle(msg: string, err: any) {
        this.errorService.sendMessage(msg);
    }

    click(account: any) {
        this.actualAccount = account;
        console.log(this.actualAccount)
    }

    load() {
        this.getDatasByPath.getPathsData(
            this.config.bankAccount.path +
            this.config.bankAccount.all.path +
            this.config.bankAccount.all.account.path +
            "/" + this.id).subscribe(
            data => {
                this.accounts = data.bankAccountList;
                this.actualAccount = data.bankAccountList[0];
                console.log(this.actualAccount)
                console.log(data)
            },
            (err) => {
                this.errorService.sendMessage("Account doesnt exist" + err);

            })
    }

    makeIPExternal(uri: string): string {
        return this.getServiceUrlService.makeIPExternal(uri);
    }
}