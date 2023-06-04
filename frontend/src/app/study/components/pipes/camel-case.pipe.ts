import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'camelCase'
})
export class CamelCasePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    let result=value.split(' ');
    for(let i=0;i<result.length;i++){
      result[i]=result[i].charAt(0).toUpperCase()+result[i].slice(1).toLowerCase();
    }

    return result.join('');
  }

}
