import { Component, OnInit } from '@angular/core';
import { PartHttpService } from './partHttpService';

@Component({
  selector: 'app-part-list',
  templateUrl: './part-list.component.html',
  styleUrls: ['./part-list.component.less']
})

export class PartListComponent implements OnInit {
  error: any;
  results: any;

  constructor(private partHttpService: PartHttpService) { }

  ngOnInit(): void {
    this.getPartList();
  }

  getPartList() {
    this.partHttpService.getPartList()
      .subscribe(
        (data) => this.results = { ...data }, // success path
        (error) => this.error = error // error path
      );
  }

}
//
//
// @Component({
//   selector: 'app-config',
//   templateUrl: './config.component.html',
//   providers: [ ConfigService ],
//   styles: ['.error {color: red;}']
// })
// export class ConfigComponent {
//   error: any;
//   headers: string[];
//   config: Config;
//
//   constructor(private configService: ConfigService) {}
//
//   clear() {
//     this.config = undefined;
//     this.error = undefined;
//     this.headers = undefined;
//   }
//
//
//   showConfig_v1() {
//     this.configService.getConfig_1()
//       .subscribe((data: Config) => this.config = {
//           heroesUrl: data.heroesUrl,
//           textfile:  data.textfile
//       });
//   }
//
//   showConfig_v2() {
//     this.configService.getConfig()
//       // clone the data object, using its known Config shape
//       .subscribe((data: Config) => this.config = { ...data });
//   }
//
//   showConfigResponse() {
//     this.configService.getConfigResponse()
//       // resp is of type `HttpResponse<Config>`
//       .subscribe(resp => {
//         // display its headers
//         const keys = resp.headers.keys();
//         this.headers = keys.map(key =>
//           `${key}: ${resp.headers.get(key)}`);
//
//         // access the body directly, which is typed as `Config`.
//         this.config = { ... resp.body };
//       });
//   }
//   makeError() {
//     this.configService.makeIntentionalError().subscribe(null, error => this.error = error );
//   }
// }
//
