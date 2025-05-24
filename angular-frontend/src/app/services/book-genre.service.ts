import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class BookGenreService {

  private targetUrl = `http://localhost:8082/api/genres`;
  private router = inject(Router);

  collectAllGenres() {
    return axios.get(`${this.targetUrl}`);
  }

  getGenreDetails(genreId: any) {
    return axios.get(`${this.targetUrl}/${genreId}`);
  }

  redirectAllGenres() {
    this.router.navigate([`genres`]);
  }

  async deleteGenre(genreId: any) {
    return await axios.delete(`${this.targetUrl}/${genreId}`);
  }

  getTargetUrl() {
    return this.targetUrl;
  }
}
