import {Component, Input, OnInit} from '@angular/core';
import { House } from '../../interface/house';
import { Room } from '../../interface/room';
import { Light } from '../../interface/light';
import {HouseService} from "../../service/house.service";
import {RoomService} from "../../service/room.service";
import {LightService} from "../../service/light.service";
import {Router} from "@angular/router";

type ObjectPerso = House | Room | Light;
type Service = HouseService | RoomService | LightService;

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit{

  constructor(private router : Router) {
  }
  @Input() object: ObjectPerso | null = null;
  @Input() service: Service | null = null;

  on : boolean = false;
  ngOnInit(): void {
  }

  redirect = (event: Event) => {
    event.stopPropagation();
    switch (this.object?.type) {
      case 'house': {
        this.router.navigate(['auth','house', this.object?.id, 'rooms'])
        break;
      }
      case 'room': {
        console.log("lala")
        this.router.navigate(['auth','house', 'room', this.object?.id, 'lights']);
        break;
      }
    }
  }

  switchLight = (event: Event) => {
    event.stopPropagation();
    this.on = !this.on
    // @ts-ignore
    this.service?.switchOn(this.object?.id, this.on)
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }

  updatePercentage(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
  }
}
