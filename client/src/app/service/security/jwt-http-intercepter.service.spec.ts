import { TestBed } from '@angular/core/testing';

import { JwtHttpIntercepterService } from './jwt-http-intercepter.service';

describe('JwtHttpIntercepterService', () => {
  let service: JwtHttpIntercepterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtHttpIntercepterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
