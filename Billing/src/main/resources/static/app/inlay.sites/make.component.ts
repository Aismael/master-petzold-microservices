import {Component} from "@angular/core";
import {GetDatasByPath, GetPathsService, PostDatasByPath} from "../app.rest.paths";
import {ErrorService, LoginService} from "./services";
import {Router} from "@angular/router";
/**
 * Created by Aismael on 22.04.2017.
 */
@Component({
    selector: "app-make",
    template: `
        <div class="ui grid">
            <div class="four wide column">
                <sm-menu title="Available Banks" class="fluid vertical menu inverted blue">
                    <a sm-item *ngFor="let bank of banks" [icon]="bank.name"
                       (click)="click(bank)">{{bank?.name}}
                        <i class="arrow circle right icon" *ngIf="bank?.name===actualBank.name">
                        </i>
                    </a>
                </sm-menu>
            </div>
            <div class="twelve wide stretched column">
                <div class="ui segment">
                    <h1> make an new Acoount at the {{actualBank.name}} Bank
                    </h1>
                </div>
                <div class="ui massive red labeled icon button" (click)="make()">
                    <i class="send icon"></i>
                    request new Account
                </div>
            </div>
        </div>`
})
export class MakeComponent {
    errorHandle: any;
    actualBank: any;
    banks: any;
    config: any;
    id: number;
    MakeNewBankAccountDTO: any;

    constructor(getPathsService: GetPathsService,
                loginService: LoginService,
                private errorService: ErrorService,
                private getDatasByPath: GetDatasByPath,
                private postDatasByPath: PostDatasByPath,
                private router: Router) {
        this.actualBank = {name: "noname", id: 0};
        this.id = loginService.getLast();
        getPathsService.getPathsData().subscribe(config => {
            this.config = config.config.view
            this.load()
        });
    }

    load() {
        this.getDatasByPath.getPathsData(
            this.config.bank.path +
            this.config.bank.all.path
        ).subscribe(
            data => {
                this.banks = data.bankDTOList;
                this.actualBank = data.bankDTOList[0];
                console.log(data)
            },
            (err) => {
                this.errorService.sendMessage("Banks doesnt exist" + err);

            })
    }

    click(bank: any) {
        this.actualBank = bank;
        console.log(this.actualBank)
    }

    make() {
        this.MakeNewBankAccountDTO = {
            accountId: this.id,
            bankId: this.actualBank.id
        }
        this.postDatasByPath.postPathsData(
            this.config.bankAccount.path +
            this.config.bankAccount.one.path +
            this.config.bankAccount.one.account.path,
            this.MakeNewBankAccountDTO)
            .subscribe(data => {
                    console.log(data)
                    this.router.navigate(["/bill/pay"])

                },
                (err) => {
                    this.errorHandle("Paying", err)
                });
    }
}
