import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class BookImageService {

  private targetUrl = `http://localhost:8082/api/images`;

  getTargetUrl(){
    return this.targetUrl;
  }

  collectAllImages(){
    return axios.get(`${this.targetUrl}`);
  }
}
