import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private targetUrl = `http://localhost:8080/api/books`;
  private router = inject(Router);

  collectAllBooks() {
    return axios.get(`${this.targetUrl}`);
  }

  collectBookDetails(bookId: any) {
    return axios.get(`${this.targetUrl}/${bookId}`);
  }

  async deleteBook(bookId: any) {
    return await axios.delete(`${this.targetUrl}/${bookId}`);
  }

  getTargetUrl() {
    return this.targetUrl;
  }

  redirectAllBooks() {
    this.router.navigate([`books`]);
  }


}
