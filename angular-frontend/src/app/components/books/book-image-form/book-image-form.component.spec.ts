import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookImageFormComponent } from './book-image-form.component';

describe('BookImageFormComponent', () => {
  let component: BookImageFormComponent;
  let fixture: ComponentFixture<BookImageFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookImageFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookImageFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
