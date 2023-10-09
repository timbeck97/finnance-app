import { AfterContentChecked, ChangeDetectorRef, Component, Input, Optional, Self, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, NgControl } from '@angular/forms';
import { SelectOptions } from '../model/SelectOptions';

@Component({
  selector: 'input-competencia',
  templateUrl: './input-competencia.component.html',
  styleUrls: ['./input-competencia.component.css'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    multi: true,
    useExisting: forwardRef(() => InputCompetenciaComponent)
  }]
})
export class InputCompetenciaComponent implements ControlValueAccessor, AfterContentChecked{

  @Input()
  label: string='';

  @Input()
  formControlName: string = '';

  @Input()
  className: string = 'col-md-12';


  onChange: any = () => { };
  onTouched: any = () => { };

  selectedValue: string = '';

  @Input()
  options: SelectOptions[]=[];

  constructor(private changeDetector: ChangeDetectorRef){

  }

  registerOnChange(fn: (value: any) => void): void {
      this.onChange = fn;
  }

  writeValue(obj: any): void {
    this.selectedValue=obj;
  }
  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }
  ngAfterContentChecked(): void {
    this.changeDetector.detectChanges();
  }
}
