import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenreUpdateFormComponent } from './genre-update-form.component';

describe('GenreUpdateFormComponent', () => {
  let component: GenreUpdateFormComponent;
  let fixture: ComponentFixture<GenreUpdateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenreUpdateFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenreUpdateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
