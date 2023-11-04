import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroMensalComponent } from './registro-mensal.component';

describe('RegistroMensalComponent', () => {
  let component: RegistroMensalComponent;
  let fixture: ComponentFixture<RegistroMensalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistroMensalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistroMensalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
