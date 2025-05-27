import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  validateGenre(): boolean {
    var name = (<HTMLInputElement>document.getElementById(`name`)).value.trim();
    var description = (<HTMLInputElement>document.getElementById(`description`)).value.trim(); // Ispravljeno!
    var nameError = document.getElementById(`nameError`);
    var descriptionError = document.getElementById(`descriptionError`);
    var returnValue = true;

    if (nameError) {
      if (name.length < 3 || name.length > 40) {
        returnValue = false;
        nameError.style.visibility = `visible`;
      } else {
        nameError.style.visibility = `hidden`;
      }
    }

    if (descriptionError) {
      if (description.length < 3 || description.length > 100) {
        returnValue = false;
        descriptionError.style.visibility = `visible`;
      } else {
        descriptionError.style.visibility = `hidden`;
      }
    }

    return returnValue;
  }

  validateBook(): boolean {
    var title = (<HTMLInputElement>document.getElementById(`title`)).value.trim();
    var author = (<HTMLInputElement>document.getElementById(`author`)).value.trim();
    var description = (<HTMLInputElement>document.getElementById(`description`)).value.trim();
    var genreId = (<HTMLSelectElement>document.getElementById(`genreId`)).value;
    var price = (<HTMLInputElement>document.getElementById(`price`)).value.trim();

    var titleError = document.getElementById(`titleError`);
    var authorError = document.getElementById(`authorError`);
    var descriptionError = document.getElementById(`descriptionError`);
    var genreError = document.getElementById(`genreError`);
    var priceError = document.getElementById(`priceError`);

    var returnValue = true;

    if (titleError) {
      if (title.length < 3 || title.length > 75) {
        returnValue = false;
        titleError.style.visibility = `visible`;
      } else {
        titleError.style.visibility = `hidden`;
      }
    }

    if (authorError) {
      if (author.length < 3 || author.length > 50) {
        returnValue = false;
        authorError.style.visibility = `visible`;
      } else {
        authorError.style.visibility = `hidden`;
      }
    }

    if (descriptionError) {
      if (description.length < 3 || description.length > 100) {
        returnValue = false;
        descriptionError.style.visibility = `visible`;
      } else {
        descriptionError.style.visibility = `hidden`;
      }
    }

    if (genreError) {
      if (genreId === "") {
        returnValue = false;
        genreError.style.visibility = `visible`;
      } else {
        genreError.style.visibility = `hidden`;
      }
    }

    if (priceError) {
      if (price === `` || Number(price) <= 0) {
        returnValue = false;
        priceError.style.visibility = `visible`;
      } else {
        priceError.style.visibility = `hidden`;
      }
    }

    return returnValue;
  }

  validateUser() {
    var firstName = (<HTMLInputElement>document.getElementById(`firstName`)).value.trim();
    var lastName = (<HTMLInputElement>document.getElementById(`lastName`)).value.trim();
    var email = (<HTMLInputElement>document.getElementById(`email`)).value.trim();
    var password = (<HTMLInputElement>document.getElementById(`password`)).value.trim();
    var confirmpass = (<HTMLInputElement>document.getElementById(`confirmpass`)).value.trim();

    var firstNameError = document.getElementById(`firstNameError`);
    var lastNameError = document.getElementById(`lastNameError`);
    var emailError = document.getElementById(`emailError`);
    var passwordError = document.getElementById(`passwordError`);

    var regEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/g;
    var returnValue = true;

    if (confirmpass !== password) {
      returnValue = false;
      alert(`Password does not match!`);
    } else {

      if (firstNameError) {
        if (firstName.trim() === `` || firstName.length > 30) {
          returnValue = false;
          firstNameError.style.visibility = `visible`;
        } else {
          firstNameError.style.visibility = `hidden`;
        }
      }

      if (lastNameError) {
        if (lastName.trim() === `` || lastName.length > 30) {
          returnValue = false;
          lastNameError.style.visibility = `visible`;
        } else {
          lastNameError.style.visibility = `hidden`;
        }
      }

      if (emailError) {
        if (email === `` || email.length > 50 || !regEmail.test(email)) {
          returnValue = false;
          emailError.style.visibility = `visible`;
        } else {
          emailError.style.visibility = `hidden`;
        }
      }

      if (passwordError) {
        if (password.trim().length < 6 || password.length > 30) {
          returnValue = false;
          passwordError.style.visibility = `visible`;
        } else {
          passwordError.style.visibility = `hidden`;
        }
      }

    }

    return returnValue;
  }

  validateNumber(event: KeyboardEvent): void {
    var allowedKeys = ['Backspace', 'ArrowLeft', 'ArrowRight', 'Tab'];

    if (!/^[\d.]$/.test(event.key) && !allowedKeys.includes(event.key)) {
      event.preventDefault();
    }
  }

}
