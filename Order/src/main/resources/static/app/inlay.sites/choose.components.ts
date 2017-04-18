import {Component} from "@angular/core";
import {GetPathsService} from "../app.rest.paths";
import {LoginService} from "./services";
import {NavigationEnd, Router} from "@angular/router";
@Component({
    selector: 'app-choose',
    template: `
        <div class="ui section divider"></div>
        <div class="ui two column middle aligned very relaxed stackable grid" *ngIf="sub">
            <div class="center aligned column">
                <div class="ui massive green labeled icon button" (click)="toNew()">
                    <i class="shopping basket icon"></i>
                    New Order
                </div>
            </div>
            <div class="divider-column">
                <div class="ui vertical divider">
                    Or
                </div>
            </div>
            <div class="center aligned column">
                <div class="ui massive blue right labeled icon button" (click)="toLoad()">
                    <i class="right thumbs up icon"></i>
                    Reorder
                </div>
            </div>
        </div>
        <router-outlet></router-outlet>
    `
})

export class ChooseComponent {
    config: any = null;
    id: any = null;
    private sub: boolean;

    constructor(getPathsService: GetPathsService, private router: Router) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);

        this.router.events.subscribe((event) => {
                if (event instanceof NavigationEnd) {
                    this.sub = !(event.urlAfterRedirects.includes("order")||event.urlAfterRedirects.includes("favorite"));

                }
            }
        );
    }
    toNew(){
        this.router.navigate(['/choose/order'])
    }
    toLoad(){
        this.router.navigate(['/choose/favorite'])
    }

}
