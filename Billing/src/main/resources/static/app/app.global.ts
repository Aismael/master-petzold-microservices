import {Component, OnDestroy} from "@angular/core";
import {DomSanitizer} from "@angular/platform-browser";
import "rxjs/add/operator/map";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {ErrorService, FromOrderService, GetServiceUrlService, LoginService} from "./inlay.sites/services";
declare var $: any;

@Component({
    selector: "home-button",
    template: ` <a class="ui item" [routerLink]="['/home']">Home</a>`
})
export class HomeButton {
}
@Component({
    selector: "inlay",
    template: `
        <div class="ui breadcrumb">
            <a [routerLink]="['/bill']" *ngIf="bill">Bill</a>
            <i class="right angle icon divider" *ngIf="pay"></i>
            <a [routerLink]="['/bill/pay']" *ngIf="pay">Pay</a>
            <i class="right angle icon divider" *ngIf="make"></i>
            <a [routerLink]="['/bill/make']" *ngIf="make">Make</a>
        </div>
        <router-outlet></router-outlet>
        <app-error></app-error>
    `
})
export class Inlay {
    inlayUrl: string = "inlay.html";
    public sanitizer: DomSanitizer;
    public bill = false;
    public pay = false;
    public make = false;

    constructor(private sanitizerArg: DomSanitizer,
                private router: Router,
                private route: ActivatedRoute,
                loginService: LoginService,
                fromOrderService: FromOrderService,
                private errorService: ErrorService) {
        this.sanitizer = sanitizerArg;
        this.router.events.subscribe((event) => {
                if (event instanceof NavigationEnd) {
                    this.bill = event.urlAfterRedirects.includes("bill");
                    this.pay = event.urlAfterRedirects.includes("pay");
                    this.make = event.urlAfterRedirects.includes("make");
                }
            }
        );
        this.route.queryParams.subscribe(params => {
            if (!loginService.getLast()) {
                if(!params["accountId"]){
                    this.errorService.sendMessage("accountId");
                }else {
                    loginService.sendMessage(params["accountId"])
                }
            }
            if (!fromOrderService.getLast()) {
                if(!params["orderId"]) {
                    this.errorService.sendMessage("orderId");
                }else {
                    fromOrderService.sendMessage(params["orderId"])
                }
            }
        })
    }

}


export enum InlayState{
    ACTIVE = <any> "active",
    COMPLETED = <any>"completed",
    New = <any> ""
}
interface DynamicURI {
    uri: string
}
interface ServiceInlay {
    id: number,
    name: string,
    description: string,
    URI: DynamicURI,
    icon: string,
    state: InlayState,
}
@Component({
    selector: "side-step",
    template: `
        <div class="ui fluid vertical steps">
            <div *ngFor="let si of serviceInlays" class="{{si.state}} step" id="{{si.name}}">
                <i class="{{si.icon}} icon"></i>
                <div class="content">
                    <div class="title">{{si.name}}</div>
                    <div class="description">{{si.description}}
                    </div>
                    <a [href]="sanitizer.bypassSecurityTrustUrl(makeIPExternal(si.URI.uri))">
                        direct-Link-Test
                    </a>
                </div>
            </div>
        </div>`
})

export class SideSteps {
    activeInlay: { active: ServiceInlay; };
    public serviceInlays: ServiceInlay[];
    public selectedServiceInlay: ServiceInlay;
    public sanitizer: DomSanitizer;

    constructor(private sanitizerArg: DomSanitizer, private getServiceUrlService: GetServiceUrlService) {
        this.sanitizer = sanitizerArg;
        this.serviceInlays = [
            {
                id: 1,
                name: "Order",
                description: "Choose your shipping options",
                URI: this.getURIByName("ORDER"),
                icon: "shop",
                state: InlayState.COMPLETED
            }, {
                id: 2,
                name: "Billing",
                description: "Enter billing information",
                URI: this.getURIByName("BILLING"),
                icon: "payment",
                state: InlayState.ACTIVE
            }, {
                id: 3,
                name: "Chat",
                description: "Verify order details",
                URI: this.getURIByName("ESL"),
                icon: "mail",
                state: InlayState.New
            }
        ];
        this.selectServiceInlay(this.serviceInlays[0]);
        this.activeInlay = {active: this.serviceInlays[0]};

    }

    getURIByName(name: string): DynamicURI {
        return this.getServiceUrlService.getURIByName(name)
    }

    selectServiceInlay(serviceInlay: ServiceInlay) {
        this.selectedServiceInlay = serviceInlay;
    }

    makeIPExternal(uri: string): string {
        return this.getServiceUrlService.makeIPExternal(uri);
    }


}
@Component({
    selector: "app-error",
    template: `
        <div class="ui basic modal">
            <div class="ui icon header">
                <i class="error icon"></i>
                {{text}} is wrong
            </div>
            <div class="content">
                <p>your {{text}} is incorrect</p>
            </div>
            <div class="actions">
                <div class="ui green ok inverted button">
                    <i class="checkmark icon" (click)="clearMessage()"></i>
                    OK
                </div>
            </div>
        </div>`
})
export class ErrorComponent implements OnDestroy {
    ngOnDestroy(): void {
        $(".ui.basic.modal").remove();
    }

    text: string = "";

    constructor(private e: ErrorService) {
        e.getMessage().subscribe(message => {
            if (message) {
                $(".ui.basic.modal").modal("show");
                this.text = message.text
            }
        })
    }

    clearMessage() {
        this.e.clearMessage()
    }
}