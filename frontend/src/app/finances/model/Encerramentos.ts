

interface Encerramentos{
    fixo:string,
    variavel:string,
}
interface Pagamento{
    id?: number,
    valor: number,
    data: string,
}

export { Encerramentos, Pagamento }