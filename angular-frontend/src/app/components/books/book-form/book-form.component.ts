import { AfterViewInit, Component, OnInit, inject } from '@angular/core';
import { Book } from '../../../classes/book';
import { BookGenreService } from '../../../services/book-genre.service';
import { BookGenre } from '../../../classes/book-genre';
import { CommonModule } from '@angular/common';
import { ValidationService } from '../../../services/validation.service';
import axios from 'axios';
import { BookService } from '../../../services/book.service';


@Component({
  selector: 'app-book-form',
  imports: [CommonModule],
  templateUrl: './book-form.component.html',
  styleUrl: './book-form.component.css'
})
export class BookFormComponent implements OnInit, AfterViewInit {

  private genreService = inject(BookGenreService);
  private genreList: BookGenre[] = [];
  private validationService = inject(ValidationService);
  private bookService = inject(BookService);

  ngOnInit(): void {
    Promise.all([
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
        await axios.post(`${this.bookService.getTargetUrl()}`, {
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

  collectAllGenres(): Promise<any> {
    return new Promise(() => {
      this.genreService.collectAllGenres()
        .then((response) => {
          this.genreList = response.data;
        })
    })
  }

  getAllGenres() {
    return this.genreList;
  }

  validateNumber(event: any) {
    return this.validationService.validateNumber(event);
  }

}
