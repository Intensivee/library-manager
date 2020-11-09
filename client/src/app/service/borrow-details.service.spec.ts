import { TestBed } from '@angular/core/testing';

import { BorrowDetailsService } from './borrow-details.service';

describe('BorrowDetailsService', () => {
  let service: BorrowDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BorrowDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
