import {GetPathsService} from "../app.rest.paths";
import {LoginService} from "./services";
import {Component} from "@angular/core";
@Component({
    selector: 'app-favorite',
    template: `
        <div class="ui two column middle aligned very relaxed stackable grid" >
            fav
        </div>
    `})

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
