import {Component, OnInit} from '@angular/core';
import {HouseService} from "../../service/house.service";
import {House} from "../../interface/house";

@Component({
  selector: 'app-house',
  templateUrl: './house.component.html',
  styleUrl: './house.component.css'
})
export class HouseComponent implements OnInit {
  constructor(protected service: HouseService) {}
  houses: House[] = [];
  ngOnInit(): void {
    this.service.getHouses()
      .then(
        houses => {
          this.houses = houses
        }
      )
  }
}
