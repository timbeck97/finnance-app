import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntradaContaComponent } from './entrada-conta.component';

describe('EntradaContaComponent', () => {
  let component: EntradaContaComponent;
  let fixture: ComponentFixture<EntradaContaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EntradaContaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EntradaContaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
