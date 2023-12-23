import { Injectable } from '@angular/core';
import {CookieManagerService} from "./cookie-manager.service";
import {createAxiosInstance} from "./axios.interceptor";
import {Light} from "../interface/light";

@Injectable({
  providedIn: 'root'
})
export class LightService {

  private axiosInstance;
  constructor(private cookieManagerService: CookieManagerService) {
    this.axiosInstance = createAxiosInstance(cookieManagerService)
  }

  getLights = async (roomId: number) => {
    try {
      const response = await this.axiosInstance.get(`/house/room/${roomId}/lights`);
      const lights: Light[] = response.data.map((light: Light) => {

        return {
          type: "light",
          id: light.id,
          name: light.name,
          isOn : light.isOn,
          constructor_name : light.constructor_name
        }
      })
      return lights
    } catch (error: any) {
      throw error
    }
  }


  switchOn = async (lightId: number, on: boolean) => {
    try {
      let state = on ? 'on' : 'off'
      const response = await this.axiosInstance.put(`/house/room/light/${lightId}/turn_${state}`);
      return response.data
    } catch (error: any) {
      throw error
    }
  }

}
