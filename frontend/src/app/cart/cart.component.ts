import { Component } from '@angular/core';
import { CartItem, CartService} from '../services/data.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.less']
})
export class CartComponent {
  displayedColumns: string[] = ['name', 'amount', 'finalPrice', 'brand', 'year'];

  transactions: CartItem[];
  private totalPrice: number;
  private discount: number;

  constructor(private cartService: CartService) {
    this.transactions = cartService.getItems();
  }

  getTotalCost() {
    this.totalPrice =  this.transactions
        .map(t => t.item.finalPrice * t.amount)
        .reduce((acc, value) => acc + value, 0);
    this.discount = this.cartService.getBestDiscount();

    return this.totalPrice - this.discount;
  }
}
