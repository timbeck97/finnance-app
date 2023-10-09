import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputCompetenciaComponent } from './input-competencia.component';

describe('InputCompetenciaComponent', () => {
  let component: InputCompetenciaComponent;
  let fixture: ComponentFixture<InputCompetenciaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputCompetenciaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InputCompetenciaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
