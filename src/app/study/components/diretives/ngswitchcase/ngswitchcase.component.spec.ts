import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgswitchcaseComponent } from './ngswitchcase.component';

describe('NgswitchcaseComponent', () => {
  let component: NgswitchcaseComponent;
  let fixture: ComponentFixture<NgswitchcaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NgswitchcaseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NgswitchcaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
