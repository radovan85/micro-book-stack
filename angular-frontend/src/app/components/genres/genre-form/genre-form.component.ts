import { AfterViewInit, Component, inject } from '@angular/core';
import { ValidationService } from '../../../services/validation.service';
import { BookGenreService } from '../../../services/book-genre.service';
import axios from 'axios';

@Component({
  selector: 'app-genre-form',
  imports: [],
  templateUrl: './genre-form.component.html',
  styleUrl: './genre-form.component.css'
})
export class GenreFormComponent implements AfterViewInit {

  private genreService = inject(BookGenreService);
  private validationService = inject(ValidationService);

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
        await axios.post(`${this.genreService.getTargetUrl()}`, {
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

}
