import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { HouseService } from '../../service/house.service';
import { RoomService } from '../../service/room.service';
import { LightService } from '../../service/light.service';
import { User } from "../../interface/user/user";
import { House } from "../../interface/house";
import { Room } from "../../interface/room";
import { Light } from "../../interface/light";

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.css']
})
export class SettingComponent implements OnInit {
  user: User | null = null;
  houses: House[] = [];
  rooms: Map<number, Room[]> = new Map();
  lights: Map<number, Light[]> = new Map();

  constructor(
    private authService: AuthService,
    private houseService: HouseService,
    private roomService: RoomService,
    private lightService: LightService
  ) {}

  ngOnInit() {
    this.loadUserInfo();
  }

  async loadUserInfo() {
    try {
      const storedUserJson = localStorage.getItem('user');
      if (storedUserJson) {
        this.user = JSON.parse(storedUserJson);
        if (!this.user) {
          throw new Error('No user info in localStorage');
        }
        const token = this.user.token;
        console.log('User loaded:', this.user.userInfo);
        this.loadHouses();
      } else {
        console.log('No user info in localStorage');
      }
    } catch (error) {
      console.error('Error loading user info', error);
    }
  }

  async loadHouses() {
    try {
      this.houses = await this.houseService.getHouses();
      this.houses.forEach((house) => {
        this.loadRooms(house.id);
      });
    } catch (error) {
      console.error('Error fetching houses', error);
    }
  }

  async loadRooms(houseId: number) {
    try {
      const rooms = await this.roomService.getRooms(houseId);
      this.rooms.set(houseId, rooms);
      console.log(`Rooms for house ${houseId} loaded:`, rooms);
      rooms.forEach((room) => {
        this.loadLights(room.id);
      });
    } catch (error) {
      console.error(`Error fetching rooms for house ${houseId}`, error);
    }
  }

  async loadLights(roomId: number) {
    try {
      const lights = await this.lightService.getLights(roomId);
      this.lights.set(roomId, lights);
      console.log(`Lights for room ${roomId} loaded:`, lights);
    } catch (error) {
      console.error(`Error fetching lights for room ${roomId}`, error);
    }
  }
}
