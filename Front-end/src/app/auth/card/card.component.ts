import {Component, Input, OnInit} from '@angular/core';
import { House } from '../../interface/house';
import { Room } from '../../interface/room';
import { Light } from '../../interface/light';
import {HouseService} from "../../service/house.service";
import {RoomService} from "../../service/room.service";
import {LightService} from "../../service/light.service";

type ObjectPerso = House | Room | Light;
type Service = HouseService | RoomService | LightService;

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit{

  @Input() object: ObjectPerso | null = null;
  @Input() service: Service | null = null;

  on : boolean = false;
  ngOnInit(): void {

  }

  switchLight = (event: Event) => {
    event.stopPropagation();
    this.on = !this.on
    // @ts-ignore
    this.service?.switchOn(this.object?.id, this.on)
  }

  test(message: string, event: Event): void {
    event.stopPropagation();
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }

  updatePercentage(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
  }
}
