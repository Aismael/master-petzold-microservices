import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";
import {GetServiceUrlService, HomeButton, Inlay, SideSteps} from "./app.global";
import {RouterModule} from "@angular/router";
import {routes} from "./app.routes";
import {HomeComponent, HomeErrorComponent, HomeLeftComponent, HomeRightComponent} from "./inlay.sites/home.components";
import {emptyComponent} from "./app.components.inlay.sites";
import {FormsModule} from "@angular/forms";
import {AppComponent, ContentX} from "./app.stuff";
import {GetDatasByPath, GetPathsService, PostDatasByPath} from "./app.rest.paths";
import {ErrorService, LoginService} from "./inlay.sites/services";
import {ChooseComponent} from "./inlay.sites/choose.components";

@NgModule({
    imports: [BrowserModule, HttpModule, RouterModule.forRoot(routes), FormsModule
    ],
    declarations: [
        SideSteps,
        Inlay,
        HomeComponent,
        HomeLeftComponent,
        HomeRightComponent,
        emptyComponent,
        HomeButton,
        HomeErrorComponent,
        AppComponent,
        ContentX,
        ChooseComponent],
    bootstrap: [SideSteps, Inlay, HomeButton],
    providers: [GetServiceUrlService,
        GetPathsService,
        GetDatasByPath,
        ErrorService,
        PostDatasByPath,
        LoginService], //<-- you should inject all providers here

})
export class AppModule {
}

