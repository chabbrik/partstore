import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';

import { throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

export interface cartItem {
  id: number;
  amount: number;
}

@Injectable()
export class HttpService {
  listUrl = 'http://localhost:8090/list';
  postUrl = 'http://localhost:8090/updatecart';

  constructor(private http: HttpClient) { }

  getPartList() {
    return this.http.get(this.listUrl);
  }

  sendCartItems(cartItems: cartItem[]) {
    this.http.post<any>(this.postUrl, { cart: cartItems })
        .subscribe(data => {
      return data;
    });
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }
}
