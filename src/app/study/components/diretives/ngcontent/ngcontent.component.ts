import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-ngcontent',
  templateUrl: './ngcontent.component.html',
  styleUrls: ['./ngcontent.component.css']
})
export class NgcontentComponent {

  @Input()
  header: string='' ;
}
