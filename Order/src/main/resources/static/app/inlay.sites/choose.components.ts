import {Component, OnDestroy} from "@angular/core";
import {GetPathsService} from "../app.rest.paths";
import {LoginService} from "./services";
@Component({
    selector: 'app-home',
    template: `
            <div>{{id}}</div>
            <router-outlet></router-outlet>
        `
})

export class ChooseComponent {
    config: any = null;
    id: any = null;

    constructor(getPathsService: GetPathsService,private loginService: LoginService) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
        this.id=loginService.getLast();
        loginService.getMessage().subscribe(message => {if(message){this.id = message.id}});
    }

}
