/**
 * Created by Aismael on 13.04.2017.
 */
import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";

@Injectable()
export class GetPathsService {
    constructor(private http: Http) {
    }

    getPathsData() {
        return this.http.get('/config/json')
            .map(response => response.json())
    }

}

@Injectable()
export class GetDatasByPath {
    constructor(private http: Http) {
    }

    getPathsData(path: string) {
        return this.http.get(path)
            .map(response => {
                if (response.status != 200) {
                    throw new Error('No comments to retrieve! code status ' + response.status);
                } else {
                    return response.json();
                }
            })
    }

}

@Injectable()
export class PostDatasByPath {
    constructor(private http: Http) {
    }

    postPathsData(path: string,body: any) {
        return this.http.post(path,body)
            .map(response => {
                if (response.status != 200) {
                    throw new Error('No comments to retrieve! code status ' + response.status);
                } else {
                    return response.json();
                }
            })
    }

}