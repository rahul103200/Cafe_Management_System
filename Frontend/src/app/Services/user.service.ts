import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  url = environment.apiUrl

  constructor(private httpClient: HttpClient) { }

  signup(data: any) {
    return this.httpClient.post(this.url +
      "/user/signup", JSON.stringify(data), {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    })
  }

  forgetPassword(data: any) {
    return this.httpClient.post(this.url +
      "/user/forgetPassword", JSON.stringify(data), {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    })
  }

  login(data: any) {
    return this.httpClient.post(this.url +
      "/user/login", JSON.stringify(data), {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    })
  }

  checkToken() {
    return this.httpClient.get(this.url + "/user/checkToken");
  }

  changePassword(data: any) {
    return this.httpClient.post(this.url +
      "/user/changePassword", JSON.stringify(data), {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    })
  }

  getUsers() {
    return this.httpClient.get(this.url + "/user/get");
  }

  update(data:any){
    return this.httpClient.post(this.url+"/user/update",data,{
      headers:new HttpHeaders().set('Content-Type',"application/json")
    })
  }
}
