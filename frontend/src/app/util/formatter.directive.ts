import { Directive, ElementRef, HostListener, Input, OnChanges } from '@angular/core';

@Directive({
  selector: '[formatterDirective]'
})
export class FormatterDirective {

  private el: HTMLInputElement;

  @Input('tipo')
  public tipo:string = '';

  constructor(
    private elementRef: ElementRef,

  ) {
    this.el = this.elementRef.nativeElement;
  }

  ngOnInit() {
    this.el.value = this.formatValue(this.el.value);
  }
  @HostListener('ngModelChange', ['$event']) onChange(value:any) {
    this.el.value = this.formatValue(value);
  }

  formatValue(value:any):string {
    console.log(value);
    
    if (this.tipo == 'double2Decimal'){
      return this.formatValorNumeroInput(String(value),2);
    }
    
    return value
  }
  formatValorNumeroInput(v:string, casasDecimais:number):string {
    
    var negativo = false;
    if (v.charAt(0) == '-') {
        negativo = true;
    }
    v = v.replace(/\D/g, "");

    var regex, partes;

    //Coloca ponto entre o segundo e o terceiro dígitos
    if (v.length > casasDecimais) {
        regex = "([0-9]{" + casasDecimais + "})$";
        partes = ",$1";
        v = v.replace(new RegExp(regex, "g"), partes);
    }
    if (v.length > casasDecimais + 4) {
        regex = "([0-9]{3}),([0-9]{" + casasDecimais + "}$)";
        partes = ".$1,$2";
        v = v.replace(new RegExp(regex, "g"), partes);
    }
    if (v.length > casasDecimais + 8) {
        regex = "([0-9]{3}).([0-9]{3}),([0-9]{" + casasDecimais + "}$)";
        partes = ".$1.$2,$3";
        v = v.replace(new RegExp(regex, "g"), partes);
    }
    if (v.length > casasDecimais + 12) {
        regex = "([0-9]{3}).([0-9]{3}).([0-9]{3}),([0-9]{" + casasDecimais + "}$)";
        partes = ".$1.$2.$3,$4";
        v = v.replace(new RegExp(regex, "g"), partes);
    }
    if (v.length > casasDecimais + 16) {
        regex = "([0-9]{3}).([0-9]{3}).([0-9]{3}).([0-9]{3}),([0-9]{" + casasDecimais + "}$)";
        partes = ".$1.$2.$3.$4,$5";
        v = v.replace(new RegExp(regex, "g"), partes);
    }

    return (negativo ? "-" : "") + v;
}
 

}
