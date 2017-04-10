import {Component, Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";

@Injectable()
export class ViewRestService {
    constructor(private http: Http) {
    }

    getConfig() {
        return this.http.get('/config/json')
            .map(response => response.json());
    }
}

@Component({
    selector: 'my-app',
    template: `<h1>Hello {{config}}</h1>`
})
export class AppComponent {
    config: any = null;

    constructor(viewRestService: ViewRestService) {
        viewRestService.getConfig()
            .subscribe(config => this.config = JSON.stringify(config.config.view));
    }
}

@Component({
    selector: 'side-step',
    template: `
        <div class="ui fluid vertical steps">
            <div class="active step">
                <i class="shop icon"></i>
                <div class="content">
                    <div class="title">Order</div>
                    <div class="description">Choose your shipping options</div>
                </div>
            </div>
            <div class="step">
                <i class="payment icon"></i>
                <div class="content">
                    <div class="title">Billing</div>
                    <div class="description">Enter billing information</div>
                </div>
            </div>
            <div class="step">
                <i class="mail icon"></i>
                <div class="content">
                    <div class="title">Message</div>
                    <div class="description">Verify order details</div>
                </div>
            </div>
        </div>`
})

export class SideSteps {

}
interface X {
    id: number;
}

@Component({
    selector: 'contentx',
    template: `
        <li *ngFor="let i of xs" >
            <img class="ui wireframe paragraph image" src="/images/wireframe/paragraph.png">
        </li>
    `
})

export class ContentX {
    public xs: X[];
    public selectedX: X;
    constructor() {
        this.xs = [{
            id: 0
        },{
            id: 1
        },{
            id: 2
        },{
            id: 3
        },{
            id: 4
        },{
            id: 5
        },{
            id: 6
        },{
            id: 7
        }];
        this.selectX(this.xs[0]);

    }
    selectX(x: X) {
        this.selectedX = x;
    }
}