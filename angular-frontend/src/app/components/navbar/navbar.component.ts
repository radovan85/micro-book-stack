import { Component, OnInit, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { User } from '../../classes/user';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {

  private authUser: User = new User;
  private authService = inject(AuthService);
  private userService = inject(UserService);

  ngOnInit(): void {
    Promise.all([
      this.loadCurrentUser()
    ])
      .then(() => {

      })
      .catch((error) => {
        console.error('Error loading functions', error);
      });
  }


  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  loadCurrentUser(): Promise<any> {
    return new Promise(() => {
      if (this.isAuthenticated()) {
        this.userService.getCurrentUser()
          .then((response) => {
            setTimeout(() => {
              this.authUser = response.data;
            })
          })
      }
    })
  }

  getAuthUser() {
    return this.authUser;
  }

  redirectLogout() {
    this.authService.logout();
  }

  hasAuthorityAdmin() {
    return this.authService.isAdmin();
  }
}
