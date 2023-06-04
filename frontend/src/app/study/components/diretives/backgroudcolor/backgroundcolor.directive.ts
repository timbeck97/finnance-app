import { Directive, ElementRef, Renderer2, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[Backgroundcolor]'
})
export class BackgroundcolorDirective {
  @Input()
  backgroundColor: string='red';

  @Input()
  defaultColor: string = '#FFF';

  constructor(private ref: ElementRef, private render:Renderer2) { 
  }
  
  @HostListener('mouseenter') mouseEnter(){
    this.render.setStyle(this.ref.nativeElement, 'background-color', this.backgroundColor);

  }
  @HostListener('mouseleave')
  onMouseLeave(){
    this.render.setStyle(this.ref.nativeElement, 'background-color', this.defaultColor);
  }
  ngOnInit(){
    this.render.setStyle(this.ref.nativeElement, 'background-color', this.defaultColor);
  }
}
