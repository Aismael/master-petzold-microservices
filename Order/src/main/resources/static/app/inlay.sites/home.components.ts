/**
 * Created by Aismael on 13.04.2017.
 */
import {Component, OnDestroy} from "@angular/core";
import {GetDatasByPath, GetPathsService, PostDatasByPath} from "../app.rest.paths";
import {Router} from "@angular/router";
import {ErrorService, LoginService} from "./services";
import {EmailValidator, FormBuilder, FormGroup, Validators} from "@angular/forms";
declare var $: any;


@Component({
    selector: "app-home",
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

    constructor(getPathsService: GetPathsService, loginService: LoginService, errorService: ErrorService) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
        loginService.clearMessage();
        errorService.clearMessage();

    }
}
@Component({
    selector: "app-home-left",
    template: `
        <form class="ui form" [formGroup]="form">
            <sm-loader [complete]="!formSubmited" class="inverted" text="Loading..."></sm-loader>
            <div class="field">
                    <sm-input label="UserName" icon="user" class="left" [control]="form.controls.nameControl" placeholder="Enter name..."></sm-input>
            </div>
            <div class="field">
                    <sm-input label="E-m@il" icon="mail" class="left" [control]="form.controls.emailControl" placeholder="Enter e-mail..."></sm-input>
            </div>
            <sm-button [disabled]="!form.valid" class=" blue submit button" (click)="checkAccount()">Login</sm-button>
        </form>
    `
})
export class HomeLeftComponent {
    error: any;
    config: any = null;
    id: number;
    form: FormGroup;
    formSubmited: boolean = false;


    constructor(fb: FormBuilder, getPathsService: GetPathsService, public getDatasByPath: GetDatasByPath, private router: Router, private errorService: ErrorService, private loginService: LoginService) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
        this.form = fb.group({
            emailControl: ["", Validators.compose([Validators.required, Validators.email])],
            nameControl: ["", Validators.compose([Validators.required, Validators.minLength(4)])],
        });

    }

    checkAccount() {
        if(this.form.valid) {
            this.formSubmited = true;
            this.checkMail(this.form.get("emailControl").value)
        }
    }

    checkMail(mail: string) {
        this.getDatasByPath.getPathsData(
            this.config.account.path +
            this.config.account.one.path +
            this.config.account.one.mail.path +
            "/" + mail + "/").subscribe(
            data => {
                this.checkName(this.form.get("nameControl").value)
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
                this.router.navigate(["/choose"])
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
    selector: "app-home-right",
    template: `
        <form class="ui form" [formGroup]="form">
            <sm-loader [complete]="!formSubmited" class="inverted" text="Loading..."></sm-loader>
            <div class="field">
                <sm-input label="UserName" icon="user" class="left" [control]="form.controls.nameControl" placeholder="Enter name..."></sm-input>
            </div>
            <div class="field">
                <sm-input label="E-m@il" icon="mail" class="left" [control]="form.controls.emailControl" placeholder="Enter e-mail..."></sm-input>
            </div>
            <sm-button [disabled]="!form.valid" class=" green labeled icon button" icon="signup" (click)="click()">SignUp</sm-button>
        </form>
        
    `
})
export class HomeRightComponent {
    error: any;
    config: any = null;
    form: FormGroup;
    formSubmited: boolean = false;

    constructor(fb: FormBuilder,getPathsService: GetPathsService, private postDatasByPath: PostDatasByPath, private errorService: ErrorService, private router: Router, private loginService: LoginService) {
        getPathsService.getPathsData().subscribe(config => this.config = config.config.view);
        this.form = fb.group({
            emailControl: ["", Validators.compose([Validators.required, Validators.email])],
            nameControl: ["", Validators.compose([Validators.required, Validators.minLength(4)])],
        });
    }

    click() {
        if(this.form.valid) {
            this.formSubmited = true;
            this.postDatasByPath.postPathsData(this.config.account.path +
                this.config.account.one.path,
                {
                    id: null,
                    name: this.form.get("nameControl").value,
                    mail: this.form.get("emailControl").value
                })
                .subscribe(data => {
                        this.loginService.sendMessage(data.id);
                        this.router.navigate(["/choose"])
                    },
                    (err) => {
                        this.errorHandle("new account", err)
                    });
        }
    }

    errorHandle(msg: string, err: any) {
        this.errorService.sendMessage(msg);
        this.error = err
    }


}

@Component({
    selector: "app-home-error",
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
export class HomeErrorComponent implements OnDestroy {
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


