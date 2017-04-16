import {Injectable} from "@angular/core";
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";
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