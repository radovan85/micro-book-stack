import { Component, OnInit, inject } from '@angular/core';
import { Book } from '../../../classes/book';
import { BookService } from '../../../services/book.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { BookImageService } from '../../../services/book-image.service';
import { BookGenreService } from '../../../services/book-genre.service';
import { BookImage } from '../../../classes/book-image';
import { BookGenre } from '../../../classes/book-genre';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-book-details',
  imports: [CommonModule, RouterLink],
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.css'
})
export class BookDetailsComponent implements OnInit {

  private book = new Book;
  private bookService = inject(BookService);
  private route = inject(ActivatedRoute);
  private location = inject(Location);
  private imageService = inject(BookImageService);
  private genreService = inject(BookGenreService);
  private authService = inject(AuthService);
  private allImages:BookImage[] = [];
  private genreList:BookGenre[] = [];
  private readonly DEFAULT_IMAGE_URL = 'https://t4.ftcdn.net/jpg/04/99/93/31/360_F_499933117_ZAUBfv3P1HEOsZDrnkbNCt4jc3AodArl.jpg';

  ngOnInit(): void {
    Promise.all([
      this.collectBookDetails(this.route.snapshot.params[`bookId`]),
      this.collectAllGenres(),
      this.listAllImages()
    ])

      .catch((error) => {
        console.error('Error loading functions', error);
      });
  }

  collectBookDetails(bookId: any): Promise<any> {
    return new Promise(() => {
      this.bookService.collectBookDetails(bookId)
        .then((response) => {
          this.book = response.data;
        })
    })
  }

  getBook() {
    return this.book;
  }

  goBack() {
    this.location.back();
  }

  deleteBook(bookId:any):Promise<any> {
    return new Promise(() => {
      if(confirm(`Remove this book? It will affect all related data!`)){
        this.bookService.deleteBook(bookId)
        .then(() => {
          this.bookService.redirectAllBooks();
        })

        .catch(() => {
          alert(`Failed`);
        })
      }
    })
  }

  hasAuthorityAdmin() {
    return this.authService.isAdmin();
  }

  getBookImage(book: Book): string {
    var image = this.allImages.find(img => img.bookId === book.bookId);
    if (image) {
      return `data:image/jpg;base64,${image.data}`;
    } else {
      // Return URL for default image
      return this.DEFAULT_IMAGE_URL;
    }
  }

  getGenre(book: Book): BookGenre {
    var genre = this.genreList.find(tempGenre => tempGenre.genreId === book.genreId);
    return genre || new BookGenre;
  }

  collectAllGenres() {
    this.genreService.collectAllGenres()
      .then((response) => {
        console.log(`Ucitani zanrovi: ${response.data}`);
        this.genreList = response.data;
      })
  }

  listAllImages(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.imageService.collectAllImages()
        .then((response) => {
          this.allImages = response.data;
          resolve(this.allImages);
        })
        .catch((error) => {
          console.error("Error fetching images:", error);
          reject(error);
        });
    });
  }

}
