import { AfterViewInit, Component, OnInit, inject } from '@angular/core';
import { ValidationService } from '../../../services/validation.service';
import { Book } from '../../../classes/book';
import { ActivatedRoute } from '@angular/router';
import { BookService } from '../../../services/book.service';
import { BookGenre } from '../../../classes/book-genre';
import { BookGenreService } from '../../../services/book-genre.service';
import { CommonModule } from '@angular/common';
import axios from 'axios';

@Component({
  selector: 'app-book-update-form',
  imports: [CommonModule],
  templateUrl: './book-update-form.component.html',
  styleUrl: './book-update-form.component.css'
})
export class BookUpdateFormComponent implements OnInit,AfterViewInit {

  private book = new Book;
  private validationService = inject(ValidationService);
  private route = inject(ActivatedRoute);
  private bookService = inject(BookService);
  private genreList: BookGenre[] = [];
  private genreService = inject(BookGenreService);

  ngOnInit(): void {
    Promise.all([
      this.collectBookDetails(this.route.snapshot.params[`bookId`]),
      this.collectAllGenres()
    ])

      .catch((error) => {
        console.error('Error loading functions', error);
      });
  }

  ngAfterViewInit() {
    var form = document.getElementById(`bookForm`) as HTMLFormElement;

    form.addEventListener(`submit`, async (event) => {
      event.preventDefault();

      var formData = new FormData(form);
      var serializedData: { [key: string]: string } = {};
      formData.forEach((value, key) => {
        serializedData[key] = value.toString().trim();
      });

      if (this.validationService.validateBook()) {
        await axios.put(`${this.bookService.getTargetUrl()}/${this.book.bookId}`, {
          title: serializedData[`title`],
          author: serializedData[`author`],
          description: serializedData[`description`],
          genreId: Number(serializedData[`genreId`]),
          price: Number(serializedData[`price`])

        })
          .then(() => {
            this.bookService.redirectAllBooks();
          })

          .catch((error) => {
            console.log(error);
          });
      }
    });
  }

  getBook() {
    return this.book;
  }

  collectBookDetails(bookId: any): Promise<any> {
    return new Promise(() => {
      this.bookService.collectBookDetails(bookId)
        .then((response) => {
          this.book = response.data;
        })
    })
  }

  getAllGenres() {
    return this.genreList;
  }

  validateNumber(event: any) {
    return this.validationService.validateNumber(event);
  }

  collectAllGenres(): Promise<any> {
    return new Promise(() => {
      this.genreService.collectAllGenres()
        .then((response) => {
          this.genreList = response.data;
        })
    })
  }

}
