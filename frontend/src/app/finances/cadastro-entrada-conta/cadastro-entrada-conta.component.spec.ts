import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroEntradaContaComponent } from './cadastro-entrada-conta.component';

describe('CadastroEntradaContaComponent', () => {
  let component: CadastroEntradaContaComponent;
  let fixture: ComponentFixture<CadastroEntradaContaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadastroEntradaContaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadastroEntradaContaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
