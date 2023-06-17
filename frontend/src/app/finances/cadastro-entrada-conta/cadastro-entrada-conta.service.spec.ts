import { TestBed } from '@angular/core/testing';

import { CadastroEntradaContaService } from './cadastro-entrada-conta.service';

describe('CadastroEntradaContaService', () => {
  let service: CadastroEntradaContaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CadastroEntradaContaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
