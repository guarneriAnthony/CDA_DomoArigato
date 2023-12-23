import {Component, OnInit} from '@angular/core';
import {LightService} from "../../service/light.service";
import {Light} from "../../interface/light";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-light',
  templateUrl: './light.component.html',
  styleUrl: './light.component.css'
})
export class LightComponent implements OnInit {
  constructor(protected service: LightService, private route : ActivatedRoute) { }
  roomId : number = 0
  lights : Light [] = []

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.roomId = +params['roomId']
    })
    this.service.getLights(this.roomId)
      .then(
        lights => {
          this.lights = lights
        }
      )
  }

}
