import {Component} from "@angular/core";
import {DomSanitizer} from "@angular/platform-browser";
declare var $: any;

@Component({
    selector: "app-home",
    template: `
        <iframe id="testFrame" style="height: 1000px;width:100%;border:none;overflow:hidden;"
                [src]="sanitizer.bypassSecurityTrustResourceUrl(inlayUrl)"
                onloadeddata="doit"
        >
        </iframe>
       `
})

export class HomeComponent {
    inlayUrl: string;
    public sanitizer: DomSanitizer;
    constructor(private sanitizerArg: DomSanitizer){
        this.inlayUrl=self.location.protocol + "//" + self.location.hostname + ":3000"
        this.sanitizer = sanitizerArg;
    }
    doit() {
        $(document).ready(function() {
            $("#testFrame").load(function() {
                var doc = this.contentDocument || this.contentWindow.document;
                var target = doc.getElementById("target");
                target.innerHTML = "Found It!";
            });
        });

}
}

