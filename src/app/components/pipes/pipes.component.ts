import { Component } from '@angular/core';

@Component({
  selector: 'app-pipes',
  templateUrl: './pipes.component.html',
  styleUrls: ['./pipes.component.css']
})
export class PipesComponent {
  produto: any = {
    nome:'Headset HyperX Cloud Alpha S',
    preco:369.99,
    marca:'HyperX',
    dataPromocao:new Date()
  }
  frase:string='CuRso de AnGuLar'
}
