import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadatroContaComponent } from './cadatro-conta.component';

describe('CadatroContaComponent', () => {
  let component: CadatroContaComponent;
  let fixture: ComponentFixture<CadatroContaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadatroContaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadatroContaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
