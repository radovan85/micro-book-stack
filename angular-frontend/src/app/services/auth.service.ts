import { Injectable, inject } from '@angular/core';
import { AuthenticationRequest } from '../classes/authentication-request';
import { User } from '../classes/user';
import { Router } from '@angular/router';
import axios from 'axios';

export var authInterceptor = axios.interceptors.request.use(
  config => {
    var authToken = localStorage.getItem('authToken');
    if (authToken) {
      config.headers.Authorization = `${authToken}`;
    }

    return config;
  });



export var errorInterceptor = axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response.status === 401) {
      localStorage.clear();
      window.location.reload();
    }

    console.log(`Error`);

    return Promise.reject(error);

  });

export var suspensionInterceptor = axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response.status === 451) {
      alert(`Account suspended`);
      localStorage.clear();
      window.location.reload();
    }

    return Promise.reject(error);

  });


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authRequest: AuthenticationRequest = new AuthenticationRequest;
  private authUser: User = new User;
  private router = inject(Router);
  private targetUrl = `http://localhost:8080/`;

  isAdmin(): boolean {
    const decoded = this.decodeToken();
    return decoded?.roles?.includes('ROLE_ADMIN') ?? false;
  }

  


  isUser(): boolean {
    var role = localStorage.getItem(`role`);
    if (role) {
      if (role == "ROLE_USER") {
        return true;
      } else {
        return false;
      }
    }

    return false;
  }

  isAuthenticated() {
    var returnValue = false;
    var authToken = localStorage.getItem('authToken');
    if (authToken) {
      returnValue = true;
    }

    return returnValue;
  }

  setAuthRequest(tempRequest: AuthenticationRequest) {
    this.authRequest = tempRequest;
  }


  async login() {
    var errorMessage = document.getElementById(`error-message`);
    await
      axios.interceptors.request.eject(authInterceptor);
    axios.post(`${this.targetUrl}login`, {
      username: this.authRequest.username,
      password: this.authRequest.password
    })
      .then((response) => {
        localStorage.setItem(`currentUser`, JSON.stringify(response));
        this.authUser = response.data;
        var tokenStr = this.authUser.authToken;
        var authToken = '';
        if (tokenStr) {
          authToken = `Bearer ${tokenStr}`;
          localStorage.setItem(`authToken`, authToken);
        }

        if (errorMessage) {
          errorMessage.style.visibility = `hidden`;
        }



        console.log(`Login completed!`);
        window.location.reload();

      })

      .catch((error) => {
        if (error.response.status === 422) {
          if (errorMessage) {
            errorMessage.style.visibility = `visible`;
          }
        }
        else {
          alert(`Failed!`);
        }

      })
  }

  logout() {
    localStorage.clear();
    this.router.navigate([`login`]);
  }

  redirectRegister() {
    this.router.navigate([`registration`]);
  }

  getTargetUrl(): string {
    return this.targetUrl;
  }

  decodeToken() {
    const token = localStorage.getItem('authToken');
    if (token) {
      const base64Url = token.split('.')[1]; // Uzimamo payload deo tokena
      const base64 = decodeURIComponent(atob(base64Url)); // Dekodiramo ga
      return JSON.parse(base64); // Parsiramo JSON sadržaj tokena
    }
    return null;
  }
  
}
