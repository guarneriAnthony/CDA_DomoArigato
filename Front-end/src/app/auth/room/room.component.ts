import { Component } from '@angular/core';
import {RoomService} from "../../service/room.service";
import {Room} from "../../interface/room";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.css'
})
export class RoomComponent {
  constructor(protected service: RoomService, private route : ActivatedRoute) { }
  rooms : Room [] = []
  houseId : number = 0

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.houseId = +params['houseId']
    })
    this.service.getRooms(this.houseId)
      .then(
        rooms => {
          this.rooms = rooms
        }
      )
  }
}
