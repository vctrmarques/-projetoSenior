import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  hospedagens: Array<any>;
  hospedagem: any;
  
  categorias: any;

  constructor(private service: AppService) {}

  ngOnInit() {
    this.hospedagem = {};
    this.categorias = {};

    this.service.listar()
      .subscribe(resposta => this.hospedagens = resposta);

  }

  criar(frm: FormGroup) {
    debugger
    this.service.criar(this.hospedagem).subscribe(resposta => {
      this.hospedagens.push(resposta);

      frm.reset();
    });
  }
}
