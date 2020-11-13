import { Component } from '@angular/core';

export interface CartItem {
  name: String;
  amount: number;
  cost: number;
  brand: String;
  year: String;
}

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.less']
})
export class CartComponent {
  displayedColumns: string[] = ['name', 'amount', 'cost', 'brand', 'year'];
  
  transactions: CartItem[] = [
    {
      name: 'ngine',
      amount: 1,
      cost: 1000,
      brand: 'BMW',
      year: '2003'
  },
  {
      name: 'Wheel',
      amount: 4,
      cost: 50,
      brand: 'Audi',
      year: '2003'
  },
  {
      name: 'gearbox',
      amount: 1,
      cost: 255,
      brand: 'VW',
      year: '2003'
  },
  {
      name: 'Brakes',
      amount: 2,
      cost: 150,
      brand: 'BMW',
      year: '2003'
  }
]
    

  /** Gets the total cost of all transactions. */
  getTotalCost() {
    return this.transactions.map(t => t.cost).reduce((acc, value) => acc + value, 0);
  }
}
