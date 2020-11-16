import { Component, ViewChild, OnInit } from '@angular/core';
import { HttpService } from '../services/http.service';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CarPart, CartService } from '../services/data.service';
import { BRAND } from '../constants/car.brands';

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
        buyButton: 'Pirkti'
    };

    dataSource: MatTableDataSource<CarPart>;
    originalData;

    toggles = {
        BMW: true,
        Audi: true,
        VW: true
    };

    @ViewChild(MatSort) sort: MatSort;
    error: any;
    results: any;

    private bwmParts: CarPart[];
    private audiParts: CarPart[];
    private vwParts: CarPart[];

    constructor(private partHttpService: HttpService,
                private dataService: CartService) {}

    ngOnInit() {
        this.getPartList();
    }

    toggle(brand: string) {
        this.toggles[brand] = !this.toggles[brand];
        let datasrc = [];

        if (this.toggles.BMW) {
            datasrc = datasrc.concat(this.bwmParts);
        }

        if (this.toggles.Audi) {
            datasrc = datasrc.concat(this.audiParts);
        }

        if (this.toggles.VW) {
            datasrc = datasrc.concat(this.vwParts);
        }

        this.dataSource = new MatTableDataSource(datasrc);
        this.dataSource.sort = this.sort;
    }

    getPartList() {
        this.partHttpService.getPartList()
            .subscribe(
                (data: CarPart[]) => {
                    this.originalData = data;

                    this.bwmParts = data.filter(x => x.brand === BRAND.BMW);
                    this.audiParts = data.filter(x => x.brand === BRAND.Audi);
                    this.vwParts = data.filter(x => x.brand === BRAND.VW);

                    this.dataSource = new MatTableDataSource(data);
                    this.dataSource.sort = this.sort;
                },
                (error) => this.error = error
            );
    }

    addToCart(item) {
        const priorPurchase = this.dataService.getItems().filter(e => e.item.id === item.id);

        if (priorPurchase.length > 1) {
            console.error('Something is terribly wrong: duplicates in the cart');
            return;
        }

        /* Assuming that, if it is in the list, there is at least one item for sale */
        if (priorPurchase.length === 0) {
            this.dataService.addItem({ item, amount: 1 });
        }
        /* Strong assumption that there is only one element */
        else if (priorPurchase.length === 1 ) {
            if (priorPurchase[0].amount < item.amount) {
                priorPurchase[0].amount++;
            } else {
                console.log('SOLD OUT! No more parts to buy');
            }
        }

        // this.partHttpService.sendCartItems(this.cart);
    }


    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }
}
