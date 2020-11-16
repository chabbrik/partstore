import { Injectable } from '@angular/core';
import { BRAND } from '../constants/car.brands';

export interface CarPart {
    id: number;
    name: string;
    power: string;
    engineVolume: string;
    gearbox: string;
    fuelType: string;
    itemCode: string;
    brand: string;
    year: string;
    supplier: string;
    supplierAddress: string;
    amount: number;
    finalPrice: number;
}

export interface CartItem {
    item: CarPart;
    amount: number;
}

@Injectable()
export class CartService {
    private AUDI_THRESHOLD = 200;
    private VW_THRESHOLD = 150;

    private BMW_DISCOUNT = 0.05;
    private AUDI_DISCOUNT = 0.088;
    private VW_DISCOUNT = 0.035;
    private cart: CartItem[] = [];
    private bestDiscount: number;

    addItem(item: CartItem) {
        this.cart.push(item);

        /* If number of discount rules would grow, I would refactor it.
           I would iterate over an array of functions and assemble their results.
         */
        this.bestDiscount = Math.max(
            this.calculateAudiDiscount(),
            this.calculateBMWDiscount(),
            this.calculateVWDiscount()
        );
    }

    getBestDiscount() {
        return this.bestDiscount;
    }

    getItems(): CartItem[] {
        return this.cart;
    }

    private calculateBMWDiscount() {
        return this.cart
            .filter(part => part.item.brand === BRAND.BMW)
            .map(x => x.item.finalPrice * x.amount * this.BMW_DISCOUNT)
            .reduce((acc, value) => acc + value, 0);
    }

    private calculateVWDiscount() {
        const vwCartItemValue = this.cart
            .filter(part => part.item.brand === BRAND.VW)
            .map(x => x.item.finalPrice * x.amount)
            .reduce((acc, value) => acc + value, 0);

        return vwCartItemValue > this.VW_THRESHOLD ? vwCartItemValue * this.VW_DISCOUNT : 0;
    }

    private calculateAudiDiscount() {
        const totalCartItemValue = this.cart
            .map(x => x.item.finalPrice * x.amount)
            .reduce((acc, value) => acc + value, 0);

        return totalCartItemValue > this.AUDI_THRESHOLD ? this.cart
            .filter(part => part.item.brand === BRAND.Audi)
            .map(x => x.item.finalPrice * x.amount * this.AUDI_DISCOUNT)
            .reduce((acc, value) => acc + value, 0) : 0;
    }
}