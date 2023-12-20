import {Injectable} from '@angular/core';
import axios from "axios";
import {User} from "../interface/user/user";
import {House} from "../interface/house";
@Injectable({
  providedIn: 'root'
})
export class HouseService {

  private instance = axios.create({
    baseURL: 'http://localhost:8080',
  });

  constructor() {
    this.setupInterceptors()
  }

  private setupInterceptors(): void {
    this.instance.interceptors.request.use((config) => {
      const storedUser: User = JSON.parse(localStorage.getItem('user') || '{}');

      if (storedUser && storedUser.token) {
        config.headers.Authorization = `Bearer ${storedUser.token}`;
      }
      return config;
    }, (error) => {
      return Promise.reject(error);
    });
  }

  getHouses = async () => {
    try {
      const response = await this.instance.get('');
      const houses: House[] = response.data.map((house: House) => {

        return {
          type: "house",
          id: house.id,
          name: house.name,
          allOn: house.allOn,
          anyOn: house.anyOn,
          favorite: house.favorite
        }
      })
      return houses
    } catch (error: any) {
      if (error.response && error.response.status === 401) {
        throw new Error('Username, email, or password is incorrect. Please try again.');
      } else {
        throw new Error('An error occurred during the login attempt: ' + error.message);
      }
    }
  }

  switchOn = async (houseId: number, on: boolean) => {
    try {
      let state = on ? 'on' : 'off'
      const response = await this.instance.put(`/house/${houseId}/turn_${state}`);
      return response.data
    } catch (error: any) {
      if (error.response && error.response.status === 401) {
        throw new Error('Username, email, or password is incorrect. Please try again.');
      } else {
        throw new Error('An error occurred during the login attempt: ' + error.message);
      }
    }
  }
}
