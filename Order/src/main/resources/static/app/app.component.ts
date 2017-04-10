import { Component } from '@angular/core';
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class ViewRestService {
  constructor(private http: Http) { }

  getConfig() {
    return this.http.get('/config/json')
        .map(response => response.json());
  }
}

@Component({
  selector: 'my-app',
  template: `<h1>Hello {{config}}</h1>`
})
export class AppComponent {
    config: any = null;

  constructor(viewRestService: ViewRestService) {
    viewRestService.getConfig()
        .subscribe(config => this.config = JSON.stringify(config.config.view));
  }
}