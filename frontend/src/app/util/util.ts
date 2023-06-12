export class Util {


  static getMeses() {
    return [
      { nome: 'Janeiro', valor: '01' },
      { nome: 'Fevereiro', valor: '02' },
      { nome: 'Março', valor: '03' },
      { nome: 'Abril', valor: '04' },
      { nome: 'Maio', valor: '05' },
      { nome: 'Junho', valor: '06' },
      { nome: 'Julho', valor: '07' },
      { nome: 'Agosto', valor: '08' },
      { nome: 'Setembro', valor: '09' },
      { nome: 'Outubro', valor: '10' },
      { nome: 'Novembro', valor: '11' },
      { nome: 'Dezembro', valor: '12' }
    ]
  }
  //yyyy-mm-dd
  static getDate(date: Date): string {
    let day = date.getDate();
    let stringDay=day<10?"0"+day:day;
    let month = date.getMonth();
    let stringMonth=month<10?"0"+month:month
    let year = date.getFullYear();

    return `${year}-${stringMonth}-${stringDay}`
  }
}