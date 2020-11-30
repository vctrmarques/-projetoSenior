import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  hospedesUrl = 'http://localhost:8081/hospede/';

  hospedagemUrl = 'http://localhost:8081/hospedagem/';

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<Array<any>>(this.hospedesUrl);
  }

  criar(hospedagem: any) {
    return this.http.post(this.hospedagemUrl, hospedagem);
  }
}
