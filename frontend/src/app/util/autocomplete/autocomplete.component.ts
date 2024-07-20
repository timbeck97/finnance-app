import { Component, Input,EventEmitter, HostListener, Output } from '@angular/core'; 
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Gasto } from 'src/app/finances/model/Gasto';
import { take } from 'rxjs';
import { URL } from '../environment';

@Component({
  selector: 'app-autocomplete',
  templateUrl: './autocomplete.component.html',
  styleUrls: ['./autocomplete.component.css']
})

export class AutocompleteComponent {


  timer:any;

  dados:any=[];

  @Input()
  label:string='Autocomplete'

  @Input()
  key:string='descricaoAutoComplete';

  @Input()
  value:string='id'

  @Input()
  urlParam:string='/gastos/autocomplete'

  @Input()
  msgToolTip:string|null=null;
  
  dataSelected:any={descricaoAutoComplete:''}

  @Input()
  data:any;

  @Input()
  filtro:string='';

  @Output()
  event:EventEmitter<any>=new EventEmitter<any>();

 

  showItens:boolean=false;

  @HostListener('document:click', ['$event'])
  documentClick(event: any): void {
    console.log('hehe');
    
    if(event.target.nodeName!=='LI' && this.showItens){
      this.dataSelected={descricaoAutoComplete:''}
      this.dados=[];
      this.showItens=false;
      this.event.emit(null)
      
    }
  }

  constructor(private http: HttpClient){

  }
  ngOnInit(){
    if(this.data){
      
      this.http.get<Gasto>(URL + this.urlParam+'?id='+this.data)
      .pipe(
        take(1)
      )
      .subscribe((result:any) => {
        result=result[0];
        let obj:any={}
        obj[this.key]=result[this.key]
        obj[this.value]=result[this.value]
        this.dataSelected=obj;
      })
    }
  }
  getDescription(){
    return this.dataSelected[this.key]?this.dataSelected[this.key]:''
  }
  onClick(data:any){
    this.dataSelected=data;
    this.showItens=false;
    this.event.emit(data[this.value])
  }

  handleChange(event:KeyboardEvent){
    clearInterval(this.timer)
    this.timer=setTimeout(()=>{
      let value=(<HTMLInputElement>event.target).value;
      this.findGastos(value)
    },500)
    

  }
  findGastos(value:string) {
    let params = new HttpParams({
      fromObject: {
        filtro:value,
        competencia:this.filtro
      }
    })
    this.http.get<Gasto[]>(URL + this.urlParam, { params: params, observe: 'response' })
      .pipe(
        take(1)
      )
      .subscribe((result) => {
       this.dados=result.body;
       this.showItens=true;
       
      })
  }

}
