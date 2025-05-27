import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Book } from '../../../classes/book';
import { AuthService } from '../../../services/auth.service';
import { BookService } from '../../../services/book.service';
import { BookGenreService } from '../../../services/book-genre.service';
import { BookGenre } from '../../../classes/book-genre';
import { BookImage } from '../../../classes/book-image';
import { BookImageService } from '../../../services/book-image.service';

@Component({
  selector: 'app-book-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.css'
})
export class BookListComponent implements OnInit {

  private bookList: Book[] = [];
  private paginatedBooks: Book[] = [];
  private bookService = inject(BookService);
  private imageService = inject(BookImageService);
  private genreList: BookGenre[] = [];
  private authService = inject(AuthService);
  private allImages: BookImage[] = [];
  private genreService = inject(BookGenreService);
  private pageSize = 5;
  private currentPage = 1;
  private totalPages = 1;
  //private imageCache = new Map<number, string>();
  private readonly DEFAULT_IMAGE_URL = 'https://t4.ftcdn.net/jpg/04/99/93/31/360_F_499933117_ZAUBfv3P1HEOsZDrnkbNCt4jc3AodArl.jpg';

  ngOnInit(): void {
    Promise.all([
      this.listAllImages(),
      this.collectAllGenres(),
      this.listAllBooks()
    ])

      .catch((error) => {
        console.log(`Error loading the functions ${error}`);
      })

  }

  getPaginatedBooks(): Book[] {
    return this.paginatedBooks;
  }

  getPageSize(): number {
    return this.pageSize;
  }

  getCurrentPage(): number {
    return this.currentPage;
  }

  getTotalPages(): number {
    return this.totalPages;
  }

  setPage(page: number) {
    if (page < 1 || page > this.totalPages) {
      return;
    }
    this.currentPage = page;
    this.paginatedBooks = this.bookList.slice((page - 1) * this.pageSize, page * this.pageSize);
  }

  nextPage() {
    this.setPage(this.currentPage + 1);
  }

  prevPage() {
    this.setPage(this.currentPage - 1);
  }

  listAllBooks(): Promise<any> {
    return new Promise(() => {
      this.bookService.collectAllBooks()
        .then((response) => {
          this.bookList = response.data;
          this.totalPages = Math.ceil(this.bookList.length / this.pageSize);
          this.setPage(1);
        })
    })
  }

  getBookList(): Book[] {
    return this.bookList;
  }

  listAllImages(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.imageService.collectAllImages()
        .then((response) => {
          //console.log("Fetched images:", response.data); // Provera podataka iz baze
          this.allImages = response.data;
          resolve(this.allImages);
        })
        .catch((error) => {
          console.error("Error fetching images:", error);
          reject(error);
        });
    });
  }

  getAllImages(): BookImage[] {
    return this.allImages;
  }

  

  
  collectAllGenres() {
    this.genreService.collectAllGenres()
      .then((response) => {
        console.log(`Ucitani zanrovi: ${response.data}`);
        this.genreList = response.data;
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

  deleteBook(bookId: any): Promise<any> {
    return new Promise(() => {
      if (confirm(`Remove this book? It will affect all related data!`)) {
        this.bookService.deleteBook(bookId)
          .then(() => {
            this.bookList = this.bookList.filter(
              (tempBook) => tempBook.bookId !== bookId
            );

            this.totalPages = Math.max(
              1,
              Math.ceil(this.bookList.length / this.pageSize)
            );

            if (
              (this.currentPage - 1) * this.pageSize >=
              this.bookList.length &&
              this.currentPage > 1
            ) {
              this.currentPage--;
            }

            this.setPage(this.currentPage);
            this.paginatedBooks = [
              ...this.bookList.slice(
                (this.currentPage - 1) * this.pageSize,
                this.currentPage * this.pageSize
              ),
            ];
          });
      }
    });
  }


}
