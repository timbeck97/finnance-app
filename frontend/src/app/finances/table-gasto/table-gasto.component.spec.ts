import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableGastoComponent } from './table-gasto.component';

describe('TableGastoComponent', () => {
  let component: TableGastoComponent;
  let fixture: ComponentFixture<TableGastoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableGastoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TableGastoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
