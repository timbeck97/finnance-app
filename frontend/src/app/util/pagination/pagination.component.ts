import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Filtro } from 'src/app/finances/model/Filtro';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {

  @Input()
  filtro: Filtro

  @Output()
  handleFiltro:EventEmitter<Filtro> = new EventEmitter<Filtro>();

  constructor() {

  }
  ngOnInit(): void {
    this.handleFiltro.emit(this.filtro);
  }
  handlePagination(next: boolean) {
    if (next) {
      if (this.filtro.pageNumber < this.filtro.maxPages) {
        this.filtro.pageNumber++;
        this.handleFiltro.emit(this.filtro);
      }
    } else {
      if (this.filtro.pageNumber > 1) {
        this.filtro.pageNumber--;
        this.handleFiltro.emit(this.filtro);
      }
    }
  }
  selectPage(page: number) {
    this.filtro.pageNumber = page;
    this.handleFiltro.emit(this.filtro);
  }
  getPages() {
    let array = [];
    for (let i = 1; i <= this.filtro.maxPages; i++) {
      array.push(i)
    }
    return array;
  }
  handlePageSizeChange(add: boolean) {
    if (add) {
      this.filtro.pageSize++;
    } else {
      if (this.filtro.pageSize > 5) {
        this.filtro.pageSize--;
      }
    }
    this.filtro.pageNumber = 1;
    this.handleFiltro.emit(this.filtro);

  }

}
