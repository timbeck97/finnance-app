import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardGastoComponent } from './card-gasto.component';

describe('CardGastoComponent', () => {
  let component: CardGastoComponent;
  let fixture: ComponentFixture<CardGastoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CardGastoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardGastoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
