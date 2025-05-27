import { AfterViewInit, Component, OnInit, inject } from '@angular/core';
import { BookGenre } from '../../../classes/book-genre';
import { ActivatedRoute } from '@angular/router';
import { BookGenreService } from '../../../services/book-genre.service';
import { ValidationService } from '../../../services/validation.service';
import axios from 'axios';

@Component({
  selector: 'app-genre-update-form',
  imports: [],
  templateUrl: './genre-update-form.component.html',
  styleUrl: './genre-update-form.component.css'
})
export class GenreUpdateFormComponent implements OnInit , AfterViewInit {

  private genre: BookGenre = new BookGenre;
  private route = inject(ActivatedRoute);
  private genreService = inject(BookGenreService);
  private validationService = inject(ValidationService);

  ngOnInit(): void {
    Promise.all([
      this.getGenreDetails(this.route.snapshot.params[`genreId`])
    ])

      .catch((error) => {
        console.log(`Error loading the functions ${error}`);
      })

  }

  

  ngAfterViewInit() {
    var form = document.getElementById(`genreForm`) as HTMLFormElement;

    form.addEventListener(`submit`, async (event) => {
      event.preventDefault();

      var formData = new FormData(form);
      var serializedData: { [key: string]: string } = {};
      formData.forEach((value, key) => {
        serializedData[key] = value.toString().trim();
      });

      if (this.validationService.validateGenre()) {
        await axios.put(`${this.genreService.getTargetUrl()}/${this.genre.genreId}`, {
          name: serializedData[`name`],
          description: serializedData[`description`]
        })
          .then(() => {
            this.genreService.redirectAllGenres();
          })

          .catch((error) => {

            if (error.response.status === 409) {
              alert(error.response.data);
            } else {
              console.log(error);
            }
          });
      }
    });
  }


  getGenreDetails(genreId: any): Promise<any> {
    return new Promise(() => {
      this.genreService.getGenreDetails(genreId)
      .then((response) => {
        this.genre = response.data;
      })
    })
  }

  getGenre(){
    return this.genre;
  }


  
}
