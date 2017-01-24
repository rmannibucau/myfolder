import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';

@Injectable()
export class TreeService {
  private headers = new Headers();
  private options = new RequestOptions();

  constructor(private http: Http) {
    this.headers.append('Content-Type', 'application/json');
    this.options = new RequestOptions({headers: this.headers});
  }

  loadData(request) {
    return this.http.post('api/file/list', JSON.stringify(request), this.options)
              .map(r => r.json());
  }

  findDetail(node) {
    return this.http.post('api/file', JSON.stringify({id: node.id}), this.options)
              .map(r => r.json());
  }
}
