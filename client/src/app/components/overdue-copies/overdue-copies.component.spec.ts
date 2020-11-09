import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OverdueCopiesComponent } from './overdue-copies.component';

describe('OverdueCopiesComponent', () => {
  let component: OverdueCopiesComponent;
  let fixture: ComponentFixture<OverdueCopiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OverdueCopiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OverdueCopiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
