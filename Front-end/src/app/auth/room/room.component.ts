import { Component } from '@angular/core';
import {RoomService} from "../../service/room.service";
import {Room} from "../../interface/room";

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.css'
})
export class RoomComponent {
  constructor(protected service: RoomService) { }
  rooms : Room [] = []

  ngOnInit(): void {
    this.service.getRooms()
      .then(
        rooms => {
          this.rooms = rooms
        }
      )
  }
}
