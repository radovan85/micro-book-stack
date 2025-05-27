import { AfterViewInit, Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../../services/user.service';
import { ValidationService } from '../../../services/validation.service';
import axios from 'axios';

@Component({
  selector: 'app-registration-form',
  imports: [RouterLink],
  templateUrl: './registration-form.component.html',
  styleUrl: './registration-form.component.css'
})
export class RegistrationFormComponent implements AfterViewInit {

  private router = inject(Router);
  private userService = inject(UserService);
  private validationService = inject(ValidationService);

  ngAfterViewInit() {
    var form = document.getElementById(`registrationForm`) as HTMLFormElement;

    form.addEventListener(`submit`, async (event) => {
      event.preventDefault();

      var formData = new FormData(form);
      var serializedData: { [key: string]: string } = {};
      formData.forEach((value, key) => {
        serializedData[key] = value.toString().trim();
      });

      if (this.validationService.validateUser()) {
        await axios.post(`${this.userService.getTargetUrl()}/register`, {
          firstName: serializedData[`firstName`],
          lastName: serializedData[`lastName`],
          email: serializedData[`email`],
          password: serializedData[`password`]
        })
          .then(() => {
            this.redirectRegistrationCompleted();
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

  redirectRegistrationCompleted() {
    this.router.navigate([`register/completed`]);
  }

}
