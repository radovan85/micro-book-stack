import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private targetUrl = `http://localhost:8082/api/auth`;
  private router = inject(Router);

  getCurrentUser() {
    return axios.get(`${this.targetUrl}/me`);
  }


  collectAllUsers() {
    return axios.get(`${this.targetUrl}/users`);
  }

  async suspendUser(userId: any) {
    return await axios.put(`${this.targetUrl}/suspend/${userId}`);
  }

  async reactivateUser(userId: any) {
    return await axios.put(`${this.targetUrl}/reactivate/${userId}`);
  }

  async deleteUser(userId: any) {
    return await axios.delete(`${this.targetUrl}/delete/${userId}`);
  }

  redirectAllUsers() {
    this.router.navigate([`users`]);
  }

  getTargetUrl() {
    return this.targetUrl;
  }
}
