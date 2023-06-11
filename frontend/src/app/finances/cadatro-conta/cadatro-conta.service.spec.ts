import { TestBed } from '@angular/core/testing';

import { CadatroContaService } from './cadatro-conta.service';

describe('CadatroContaService', () => {
  let service: CadatroContaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CadatroContaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
