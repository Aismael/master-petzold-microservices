import {Injectable} from "@angular/core";
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";
@Injectable()
export class ErrorService {
    private subject = new Subject<any>();
    constructor(){
    }
    sendMessage(message: string) {
        this.subject.next({ text: message });
    }

    clearMessage() {
        this.subject.next();
    }

    getMessage(): Observable<any> {
        return this.subject.asObservable();
    }

}

@Injectable()
export class LoginService {
    private subject = new Subject<any>();
    private id:number;
    constructor(){}
    sendMessage(id: number) {
        this.id=id;
        this.subject.next({ id: id });
        console.log("account id:"+id);
    }
    clearMessage() {
        this.id=null;
        this.subject.next();
    }
    getLast():number{
        return this.id;
    }
    getMessage(): Observable<any> {
        return this.subject.asObservable();
    }
}

@Injectable()
export class OrderService {
    private subject = new Subject<any>();
    private id:number;
    constructor(){}
    sendMessage(id: number) {
        this.id=id;
        this.subject.next({ id: id });
        console.log("account id:"+id);
    }
    clearMessage() {
        this.id=null;
        this.subject.next();
    }
    getLast():number{
        return this.id;
    }
    getMessage(): Observable<any> {
        return this.subject.asObservable();
    }
}
interface DynamicURI {
    uri: string
}
@Injectable()
export class GetServiceUrlService {
    constructor(private http: Http) {
    }

    getUrl(name: String) {
        return this.http.get('/call/' + name)
            .map(response => response.json())
    }

    makeIPExternal(uri: string) :string{
        var aLink = document.createElement("a");
        aLink.href = uri;
        return self.location.protocol + "//" + self.location.hostname + ":" + aLink.port
    }
    getURIByName(name: string) :DynamicURI{
        var returnURI: DynamicURI = {uri: ''};
        this.getUrl(name).subscribe(uriJson => returnURI.uri = uriJson.uri)
        return returnURI;
    }
}

@Injectable()
export class ShopService {
    private subject = new Subject<any>();
    private item:any;
    constructor(){}
    sendMessage(item: any) {
        this.item=item;
        this.subject.next(item);
    }
    clearMessage() {
        this.item=null;
        this.subject.next();
    }
    getLast():number{
        return this.item;
    }
    getMessage(): Observable<any> {
        return this.subject.asObservable();
    }
    submit(item: any) {
        if (!item.on) {
            item.on = true;
        } else {
            item.on = false;
            this.sendMessage(item);
        }
    }
}