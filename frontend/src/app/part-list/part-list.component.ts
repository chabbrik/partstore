import { Component, ViewChild, OnInit } from '@angular/core';
import { PartHttpService } from './partHttpService';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

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
    price: number;
}

export class TableExpandableRowsExample {
    columnsToDisplay = ['name', 'weight', 'symbol', 'position'];
    expandedElement: CarPart | null;
}
@Component({
    selector: 'app-part-list',
    templateUrl: './part-list.component.html',
    styleUrls: ['./part-list.component.less']
})

export class PartListComponent implements OnInit {
    displayedColumns: string[] = ['name', 'brand', 'year', 'finalPrice', 'amount', 'buyButton'];
    title = {
        name: 'Pavadinimas',
        brand: 'Markė',
        year: 'Metai',
        finalPrice: 'Kaina',
        amount: 'Kiekis',
        buyButton: ''
    };

    dataSource: MatTableDataSource<CarPart>;
    cart = [];

    @ViewChild(MatSort) sort: MatSort;
    error: any;
    results: any;

    constructor(private partHttpService: PartHttpService) {}

    ngOnInit() {
        this.getPartList();
    }

    getPartList() {
        this.partHttpService.getPartList()
            .subscribe(
                (data: CarPart[]) => {
                    this.dataSource = new MatTableDataSource(data);
                    this.dataSource.sort = this.sort;
                    },
                (error) => this.error = error
            );
    }

    addToCart(item) {
        const priorPurchase = this.cart.filter(e => e.Name === item.id);

        /* Strong assumption that there is at least one item for sale */
        if (priorPurchase.length === 0) {
            this.cart.push({ id: item.getId(), amount: 1 });
        }
        /* Strong assumption that there is only one element */
        else if (priorPurchase.length === 1 && priorPurchase[0].amount < item.amount) {
            priorPurchase[0].amount++;
        }

        this.partHttpService.sendCartItems(this.cart);
    }


    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }
}
