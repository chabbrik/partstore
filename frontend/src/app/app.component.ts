import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  title = 'PartStore';
  showBMW = true;
  showAudi = true;
  showVW = true;

  toggleBMW() { this.showBMW = !this.showBMW; }
  toggleAudi() { this.showAudi = !this.showAudi; }
  toggleVW() { this.showVW = !this.showVW; }
}
