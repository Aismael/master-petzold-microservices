/**
 * Created by Aismael on 13.04.2017.
 */
import {Component, OnDestroy} from "@angular/core";
import {GetDatasByPath, GetPathsService, PostDatasByPath} from "../app.rest.paths";
import {Router} from "@angular/router";
import {ErrorService, LoginService} from "./services";
declare var $: any;


@Component({
    selector: 'app-home',
    template: `
        <div class="ui section divider"></div>
        <div class="ui two column middle aligned very relaxed stackable grid">
            <app-home-left class="column"></app-home-left>
            <div class="divider-column">
                <div class="ui vertical divider">
                    Or
                </div>
            </div>
            <app-home-right class="column"></app-home-right>
        </div>
        <app-home-error></app-home-error>`
})

export class HomeComponent {
    config: any = null;

    constructor(getPathsService: GetPathsService, loginService: LoginService,errorService :ErrorService) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
        loginService.clearMessage();
        errorService.clearMessage();

    }
}
@Component({
    selector: 'app-home-left',
    template: `
        <div class="ui form">
            <div class="field">
                <label>Username</label>
                <div class="ui left icon input">
                    <input type="text" placeholder="Username" [(ngModel)]="userName" #ctrl="ngModel">
                    <i class="user icon"></i>
                </div>
            </div>
            <div class="field">
                <label>E-m@il</label>
                <div class="ui left icon input">
                    <input type="email" placeholder="Emailadress" [(ngModel)]="mail" #ctrl="ngModel">
                    <i class="mail icon"></i>
                </div>
            </div>
            <div class="ui blue submit button" (click)="checkAccount()">Login</div>
        </div>
    `
})
export class HomeLeftComponent {
    error: any;
    config: any = null;
    mail: string = '';
    userName: string = '';
    id: number;
    constructor(getPathsService: GetPathsService, public getDatasByPath: GetDatasByPath, private router: Router, private errorService: ErrorService, private loginService: LoginService) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
    }

    checkAccount() {
        this.checkMail(this.mail)
    }

    checkMail(mail: string) {
        this.getDatasByPath.getPathsData(
            this.config.account.path +
            this.config.account.one.path +
            this.config.account.one.mail.path +
            "/" + mail + "/").subscribe(
            data => {
                this.checkName(this.userName)
            },
            (err) => {
                this.errorHandle("Mail", err)

            })
    }

    checkName(name: string) {
        this.getDatasByPath.getPathsData(
            this.config.account.path +
            this.config.account.one.path +
            this.config.account.one.name.path +
            "/" + name + "/").subscribe(
            data => {
                this.loginService.sendMessage(data.id);
                this.router.navigate(['/choose'])
            },
            (err) => {
                this.errorHandle("Name", err)

            })
    }

    errorHandle(msg: string, err: any) {
        this.errorService.sendMessage(msg);
        this.error = err
    }

}

@Component({
    selector: 'app-home-right',
    template: `
        <div class="ui form">
            <div class="field">
                <label>Username</label>
                <div class="ui left icon input">
                    <input type="text" placeholder="Username" [(ngModel)]="userName" #ctrl="ngModel">
                    <i class="user icon"></i>
                </div>
            </div>
            <div class="field">
                <label>E-m@il</label>
                <div class="ui left icon input">
                    <input type="email" placeholder="Emailadress" [(ngModel)]="mail" #ctrl="ngModel">
                    <i class="mail icon"></i>
                </div>
            </div>
            <div class="ui green labeled icon button" (click)="click()">
                <i class="signup icon"></i>
                Sign Up
            </div>
        </div>
    `
})
export class HomeRightComponent {
    error: any;
    config: any = null;
    mail: string = '';
    userName: string = '';

    constructor(getPathsService: GetPathsService, private postDatasByPath: PostDatasByPath, private errorService: ErrorService, private router: Router, private loginService: LoginService) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
    }

    click() {
        this.postDatasByPath.postPathsData(this.config.account.path +
            this.config.account.one.path,
            {
                id: null,
                name: this.userName,
                mail: this.mail
            })
            .subscribe(data => {
                    this.loginService.sendMessage(data.id);
                    this.router.navigate(['/choose'])
                },
                (err) => {
                    this.errorHandle("new account", err)
                });
    }

    errorHandle(msg: string, err: any) {
        this.errorService.sendMessage(msg);
         this.error = err
    }


}

@Component({
    selector: 'app-home-error',
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
export class HomeErrorComponent implements  OnDestroy {
    ngOnDestroy(): void {
        $('.ui.basic.modal').remove();
    }

    text: string = '';
   constructor(private e: ErrorService) {
        e.getMessage().subscribe(message => {
            if (message) {
                $('.ui.basic.modal').modal('show');
                this.text = message.text
            }
        })
    }

    clearMessage() {
        this.e.clearMessage()
    }
}


