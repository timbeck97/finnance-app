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
    let month = date.getMonth()+1;
    let stringMonth=month<10?"0"+month:month
    let year = date.getFullYear();

    return `${year}-${stringMonth}-${stringDay}`
  }
  static getCompetenciaAtual(){
    const dataAtual = new Date();
    const mes = dataAtual.getMonth() + 1;
    const ano = dataAtual.getFullYear();
  
    return `${ano}${mes<10?"0"+mes:mes}`;
  }
  static getCompetencia(){
    const meses = [
      "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
      "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    ];
    
    const dataAtual = new Date();
    const competencias = [];
  
    for (let i = -6; i <= 6; i++) {
      let mes = dataAtual.getMonth() + i;
      let ano = dataAtual.getFullYear();
      
      if (mes < 0) {
        mes += 12;
        ano--;
      } else if (mes >= 12) {
        mes -= 12;
        ano++;
      }

      const mesFormatado = mes < 9 ? `0${mes + 1}` : mes + 1;
      const competencia = `${meses[mes]}/${ano}`;
      competencias.push({
        label: competencia,
        value: `${ano}${mesFormatado}`
      });
    }
  
    return competencias;
  }
}