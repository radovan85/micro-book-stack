import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { BookGenre } from '../../../classes/book-genre';
import { BookGenreService } from '../../../services/book-genre.service';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-genre-list',
  imports: [CommonModule,RouterModule],
  templateUrl: './genre-list.component.html',
  styleUrl: './genre-list.component.css'
})
export class GenreListComponent implements OnInit {

  private authService = inject(AuthService);
  private genreList: BookGenre[] = [];
  private genreService = inject(BookGenreService);
  private pageSize = 5;
  private currentPage = 1;
  private totalPages = 1;
  private paginatedGenres: BookGenre[] = [];

  ngOnInit(): void {
    Promise.all([
      this.collectAllGenres()
    ])

      .catch((error) => {
        console.error('Error loading functions', error);
      });
  }

  setPage(page: number) {
    if (page < 1 || page > this.totalPages) {
      return;
    }
    this.currentPage = page;
    this.paginatedGenres = this.genreList.slice((page - 1) * this.pageSize, page * this.pageSize);
  }

  nextPage() {
    this.setPage(this.currentPage + 1);
  }

  prevPage() {
    this.setPage(this.currentPage - 1);
  }

  public getPaginatedGenres(): BookGenre[] {
    return this.paginatedGenres;
  }

  public getCurrentPage(): number {
    return this.currentPage;
  }

  public getTotalPages(): number {
    return this.totalPages;
  }

  collectAllGenres(): Promise<any> {
    return new Promise(() => {
      this.genreService.collectAllGenres()
        .then((response) => {
          this.genreList = response.data;
          this.totalPages = Math.ceil(this.genreList.length / this.pageSize);
          this.setPage(1);
        })
    })
  }

  hasAuthorityAdmin(){
    return this.authService.isAdmin();
  }

  getGenreList() {
    return this.genreList;
  }

  deleteGenre(genreId: any): Promise<any> {
    return new Promise(() => {
      if (confirm(`Remove this genre? It will affect all related data!`)) {
        this.genreService.deleteGenre(genreId)
          .then(() => {
            this.genreList = this.genreList.filter(
              (tempGenre) => tempGenre.genreId !== genreId
            );

            this.totalPages = Math.max(
              1,
              Math.ceil(this.genreList.length / this.pageSize)
            );

            if (
              (this.currentPage - 1) * this.pageSize >=
              this.genreList.length &&
              this.currentPage > 1
            ) {
              this.currentPage--;
            }

            this.setPage(this.currentPage);
            this.paginatedGenres = [
              ...this.genreList.slice(
                (this.currentPage - 1) * this.pageSize,
                this.currentPage * this.pageSize
              ),
            ];
          });
      }
    });
  }


}
