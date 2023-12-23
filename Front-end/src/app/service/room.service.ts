import { Injectable } from '@angular/core';
import {CookieManagerService} from "./cookie-manager.service";
import {createAxiosInstance} from "./axios.interceptor";
import {Room} from "../interface/room";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private axiosInstance;
  constructor(private cookieManagerService: CookieManagerService) {
    this.axiosInstance = createAxiosInstance(cookieManagerService)
  }

  getRooms = async () => {
    try {
      const response = await this.axiosInstance.get('house/6/rooms')
      const rooms: Room[] = response.data.map((room: Room) => {

        return {
          type: "room",
          id: room.id,
          name: room.name,
          allOn: room.allOn,
          anyOn: room.anyOn,
        }
      })
      return rooms
    } catch (error: any) {
      throw error
    }
  }

  switchOn = async (roomId: number, on: boolean) => {
    try {
      let state = on ? 'on' : 'off'
      const response = await this.axiosInstance.put(`/house/room/${roomId}/turn_${state}`);
      return response.data
    } catch (error: any) {
      throw error
    }
  }
}
