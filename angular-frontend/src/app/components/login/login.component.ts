import { AfterViewInit, Component, inject } from '@angular/core';
import axios from 'axios';
import { User } from '../../classes/user';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements AfterViewInit {

  private authService = inject(AuthService);
  private router = inject(Router);

  ngAfterViewInit(): void {
    this.executeLoginForm();
  }

  redirectRegister() {
    this.router.navigate([`registration`]);
  }

  executeLoginForm() {
    var form = document.getElementById(`loginForm`) as HTMLFormElement;
    var alertMessage = document.getElementById(`alertMessage`);
    var authUser: User = new User;
    form.addEventListener(`submit`, async (event) => {
      event.preventDefault();

      var formData = new FormData(form);
      var serializedData: { [key: string]: string } = {};
      formData.forEach((value, key) => {
        serializedData[key] = value.toString().trim();
      });


      await axios.post(`${this.authService.getTargetUrl()}api/auth/login`, {
        username: serializedData[`email`],
        password: serializedData[`password`]
      })

        .then((response) => {
          localStorage.setItem(`currentUser`, JSON.stringify(response));
          authUser = response.data;
          var tokenStr = authUser.authToken;
          var authToken = '';
          if (tokenStr) {
            authToken = `Bearer ${tokenStr}`;
            localStorage.setItem(`authToken`, authToken);
            var rolesIds = authUser.rolesIds;
            if (rolesIds) {
              var roleId = Object.values(rolesIds)[0];
              if (roleId === 1) {
                localStorage.setItem(`role`, `ADMIN`);
              }

              if (roleId === 2) {
                localStorage.setItem(`role`, `ROLE_USER`);
              }
            }
          }

          if (alertMessage) {
            alertMessage.style.visibility = `hidden`;
          }



          console.log(`Login completed!`);
          window.location.reload();

        })

        .catch(() => {
          if (alertMessage) {
            alertMessage.style.visibility = `visible`;
          }
        })
    });
  }
}
