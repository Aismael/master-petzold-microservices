import {Component} from "@angular/core";
import "rxjs/add/operator/map";
import {GetPathsService} from "./app.rest.paths";



@Component({
    selector: 'my-app',
    template: `<h1>Hello {{config}}</h1>`
})
export class AppComponent {
    config:any=null;
    constructor(getPathsService: GetPathsService) {
       getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
    }
}

interface X {
    id: number;
}

@Component({
    selector: 'contentx',
    template: `
        <p *ngFor="let i of xs" >
            <img class="ui wireframe paragraph image" src="/images/wireframe/paragraph.png">
        </p>
    `
})

export class ContentX {
    public xs: X[];
    public selectedX: X;

    constructor() {
        this.xs = [{
            id: 0
        }, {
            id: 1
        }, {
            id: 2
        }, {
            id: 3
        }, {
            id: 4
        }, {
            id: 5
        }, {
            id: 6
        }, {
            id: 7
        }];
        this.selectX(this.xs[0]);

    }

    selectX(x: X) {
        this.selectedX = x;
    }
}