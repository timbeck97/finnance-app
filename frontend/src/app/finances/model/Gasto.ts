export interface Gasto{
    id?:number,
    descricao:string,
    categoria:string,
    valor:number,
    data?:Date,
    formaPagamento:string   
}