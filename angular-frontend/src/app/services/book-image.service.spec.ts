import { TestBed } from '@angular/core/testing';

import { BookImageService } from './book-image.service';

describe('BookImageService', () => {
  let service: BookImageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookImageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
