import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltroGastoComponent } from './filtro-gasto.component';

describe('FiltroGastoComponent', () => {
  let component: FiltroGastoComponent;
  let fixture: ComponentFixture<FiltroGastoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FiltroGastoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FiltroGastoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
