import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { Book } from '../../../classes/book';
import { ActivatedRoute } from '@angular/router';
import { BookService } from '../../../services/book.service';
import axios from 'axios';
import { BookImageService } from '../../../services/book-image.service';

@Component({
  selector: 'app-book-image-form',
  imports: [],
  templateUrl: './book-image-form.component.html',
  styleUrl: './book-image-form.component.css'
})
export class BookImageFormComponent implements OnInit{

  @ViewChild('imageInput') imageInput: ElementRef | undefined;
  private currentBook = new Book;
  private route = inject(ActivatedRoute);
  private bookService = inject(BookService);
  private isFileSelected: boolean = false;
  private bookImageService = inject(BookImageService);

  ngOnInit(): void {

    this.getBookDetails(this.route.snapshot.params[`bookId`]);
    var form = document.querySelector(`form`);
    form?.addEventListener(`submit`, this.uploadImage.bind(this));

  }

  getBookDetails(bookId: any): Promise<any> {
    return new Promise(() => {
      this.bookService.collectBookDetails(bookId)
        .then((response) => { 
            this.currentBook = response.data;
          })
    })
  }

  getCurrentBook(): Book {
    return this.currentBook;
  }

  uploadImage(event: Event): void {
    event.preventDefault(); // Prevent the default form submission

    if (!this.imageInput || !this.imageInput.nativeElement.files[0]) {
      console.error('No file selected.');
      return;
    }

    var file: File = this.imageInput.nativeElement.files[0];
    var formData = new FormData();
    formData.append(`file`, file, file.name);

    axios.post(`${this.bookImageService.getTargetUrl()}/${this.currentBook.bookId}`, formData)
      .then((response) => {
        this.bookService.redirectAllBooks();
        // Handle success response
      })
      .catch((error) => {
        alert(`Error uploading image:`);
        console.log(error);
        // Handle error response
      });
  }

  onFileSelected(): void {
    if (this.imageInput && this.imageInput.nativeElement.files[0]) {
      this.isFileSelected = true;
    } else {
      this.isFileSelected = false;
    }
  }

  getisFileSelected(): boolean {
    return this.isFileSelected;
  }
}
