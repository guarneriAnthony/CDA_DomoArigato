import {Component, OnInit} from '@angular/core';
import {LightService} from "../../service/light.service";
import {Light} from "../../interface/light";

@Component({
  selector: 'app-light',
  templateUrl: './light.component.html',
  styleUrl: './light.component.css'
})
export class LightComponent implements OnInit {

  constructor(protected service: LightService) { }
  lights : Light [] = []

  ngOnInit(): void {
    this.service.getLights()
      .then(
        lights => {
          this.lights = lights
        }
      )
  }

}
