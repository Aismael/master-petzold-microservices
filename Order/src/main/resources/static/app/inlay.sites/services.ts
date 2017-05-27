import {Injectable} from "@angular/core";
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";
@Injectable()
export class ErrorService {
    private subject = new Subject<any>();

    constructor() {
    }

    sendMessage(message: string) {
        this.subject.next({text: message});
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
    private id: number;

    constructor() {
    }

    sendMessage(id: number) {
        this.id = id;
        this.subject.next({id: id});
        console.log("account id:" + id);
    }

    clearMessage() {
        this.id = null;
        this.subject.next();
    }

    getLast(): number {
        return this.id;
    }

    getMessage(): Observable<any> {
        return this.subject.asObservable();
    }
}

class FavoriteObject {
    name: "";
    count: 0;
    id: null;
    accountId: number;
    itemSetStubDTOS: ItemSetStubDTO[];
}
@Injectable()
export class FavoriteIdService {
    id:number;
    getId(): any {
        return this.id;
    }
}
@Injectable()
export class ShopBasketService {
    basket = {items: new Array()};

    getBasket(): any {
        return this.basket
    }
}
@Injectable()
export class FavoriteService {
    private subject = new Subject<any>();
    private favorite: FavoriteObject = null;

    constructor(private basketService: ShopBasketService, private loginService: LoginService) {
    }

    sendMessage(favorite: FavoriteObject) {
        this.favorite = favorite;
        this.subject.next(favorite);
    }

    clearMessage() {
        this.favorite = null;
        this.subject.next();
    }

    getLast(): FavoriteObject {
        return this.favorite;
    }

    getMessage(): Observable<any> {
        return this.subject.asObservable();
    }

    makeOrderfromBasket() {
        this.favorite = new FavoriteObject();
        var favorite = this.favorite;
        favorite.itemSetStubDTOS = [];
        this.basketService.getBasket().items.forEach(function (data: any) {
            favorite.itemSetStubDTOS.push(new ItemSetStubDTO( data.count,data.id))
        })
        this.favorite.accountId = this.loginService.getLast();
        console.log(JSON.stringify(this.favorite))
        this.sendMessage(favorite);
        return favorite;
    }
}

class OrderObject {
    id: null;
    posted: false;
    date: Date;
    accountId: number;
    itemSetStubDTOS: ItemSetStubDTO[];

    constructor() {
        this.date = new Date()
    }

}

class ItemSetStubDTO {
    count: number;
    itemID: number;

    constructor(count: number, itemID: number) {
        this.itemID = itemID;
        this.count = count;
    }

}
@Injectable()
export class OrderService {
    private subject = new Subject<any>();
    private order: OrderObject = null;

    constructor(private basketService: ShopBasketService, private loginService: LoginService) {
    }

    makeOrderfromBasket(): OrderObject {
        this.order = new OrderObject();
        var order = this.order;
        order.itemSetStubDTOS = [];
        this.basketService.getBasket().items.forEach(function (data: any) {
            order.itemSetStubDTOS.push(new ItemSetStubDTO(data.count,data.id))
        })
        this.order.accountId = this.loginService.getLast();
        console.log(JSON.stringify(this.order))
        this.sendMessage(order);
        return order;
    }

    sendMessage(order: OrderObject) {
        this.order = order;
        this.subject.next(order);
    }

    clearMessage() {
        this.order = null;
        this.subject.next();
    }

    getLast(): OrderObject {
        return this.order;
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
        return this.http.get("/call/" + name)
            .map(response => response.json())
    }

    makeIPExternal(uri: string): string {
        var aLink = document.createElement("a");
        aLink.href = uri;
        return self.location.protocol + "//" + self.location.hostname + ":" + aLink.port
    }

    getURIByName(name: string): DynamicURI {
        var returnURI: DynamicURI = {uri: ""};
        this.getUrl(name).subscribe(uriJson => returnURI.uri = uriJson.uri)
        return returnURI;
    }
}

@Injectable()
export class ShopService {
    private subject = new Subject<any>();
    private item: any;

    constructor() {
    }

    sendMessage(item: any) {
        item.on = false;
        this.item = item;
        this.subject.next(item);
    }

    clearMessage() {
        this.item = null;
        this.subject.next();
    }

    getLast(): number {
        return this.item;
    }

    getMessage(): Observable<any> {
        return this.subject.asObservable();
    }


}