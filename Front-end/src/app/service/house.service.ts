import {Injectable} from '@angular/core';
import {createAxiosInstance} from "./axios.interceptor";
import {House} from "../interface/house";
import {CookieManagerService} from "./cookie-manager.service";
@Injectable({
  providedIn: 'root'
})
export class HouseService {

  private axiosInstance;
  constructor(private cookieManagerService: CookieManagerService) {
    this.axiosInstance = createAxiosInstance(cookieManagerService)
  }

  getHouses = async () => {
    try {
      const response = await this.axiosInstance.get('');
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
      const response = await this.axiosInstance.put(`/house/${houseId}/turn_${state}`);
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
