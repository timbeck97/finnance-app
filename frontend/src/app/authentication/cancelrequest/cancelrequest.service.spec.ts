import { TestBed } from '@angular/core/testing';

import { CancelrequestService } from './cancelrequest.service';

describe('CancelrequestService', () => {
  let service: CancelrequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CancelrequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
