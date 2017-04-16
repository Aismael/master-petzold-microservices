import {Component, Injectable} from "@angular/core";
import {DomSanitizer} from "@angular/platform-browser";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {NavigationEnd, Router} from "@angular/router";

@Component({
    selector: 'home-button',
    template: ` <a class="ui item" [routerLink]="['/home']">Home</a>`
})
export class HomeButton {
}
@Component({
    selector: 'inlay',
    template: `
        <div class="ui breadcrumb">
            <a [routerLink]="['/']" >Home</a>
            <i class="right angle icon divider" *ngIf="choose"></i>
            <a [routerLink]="['/choose']" *ngIf="choose">Choose</a>
            <i class="right angle icon divider" *ngIf="order"></i>
            <a [routerLink]="['/choose/order']" *ngIf="order">Order</a>
            <i class="right angle icon divider" *ngIf="favorite"></i>
            <a [routerLink]="['/choose/favorite']" *ngIf="favorite">Favorite</a>
        </div>
        <router-outlet></router-outlet>
    `
})
export class Inlay {
    inlayUrl: string = "inlay.html";
    public sanitizer: DomSanitizer;
    public choose = false;
    public order = false;
    public favorite = false;

    constructor(private sanitizerArg: DomSanitizer, private router: Router) {
        this.sanitizer = sanitizerArg;
        this.router.events.subscribe((event) => {
                if (event instanceof NavigationEnd) {
                    this.choose = event.urlAfterRedirects.includes("choose");
                    this.order = event.urlAfterRedirects.includes("order");
                    this.favorite = event.urlAfterRedirects.includes("favorite");
                }
            }
        );


    }


}

@Injectable()
export class GetServiceUrlService {
    constructor(private http: Http) {
    }

    getUrl(name: String) {
        return this.http.get('/call/' + name)
            .map(response => response.json())
    }
}
export enum InlayState{
    ACTIVE = <any> "active",
    COMPLETED = <any>"completed",
    New = <any> ""
}
interface DynamicURI {
    uri: ''
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
    selector: 'side-step',
    template: `
        <div class="ui fluid vertical steps">
            <div *ngFor="let si of serviceInlays" class="{{si.state}} step" id="{{si.name}}">
                <i class="{{si.icon}} icon"></i>
                <div class="content">
                    <div class="title">{{si.name}}</div>
                    <div class="description">{{si.description}}
                        <a [href]="sanitizer.bypassSecurityTrustUrl(makeIPExternal(si.URI.uri))">
                            got to
                        </a>
                    </div>
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
                state: InlayState.ACTIVE
            }, {
                id: 2,
                name: "Billing",
                description: "Enter billing information",
                URI: this.getURIByName("BILLING"),
                icon: "payment",
                state: InlayState.New
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


    getURIByName(name: string) {
        var returnURI: DynamicURI = {uri: ''};
        this.getServiceUrlService.getUrl(name).subscribe(uriJson => returnURI.uri = uriJson.uri)
        return returnURI;
    }

    selectServiceInlayByName(name: string) {
        return this.serviceInlays.find(serviceinlay => serviceinlay.name == name);
    }

    selectServiceInlayById(id: number) {
        return this.serviceInlays.find(serviceinlay => serviceinlay.id == id);
    }

    selectServiceInlay(serviceInlay: ServiceInlay) {
        this.selectedServiceInlay = serviceInlay;
    }

    makeIPExternal(uri: string) {
        var aLink = document.createElement("a");
        aLink.href = uri;
        return self.location.protocol + "//" + self.location.hostname + ":" + aLink.port
    }


}