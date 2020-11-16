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
    cart = [];

    toggles = {
        BMW: true,
        Audi: true,
        VW: true
    };

    @ViewChild(MatSort) sort: MatSort;
    error: any;
    results: any;
    private Bmwparts: CarPart[];
    private AudiParts: CarPart[];
    private VWparts: CarPart[];

    constructor(private partHttpService: PartHttpService) {}

    ngOnInit() {
        this.getPartList();
    }

    toggle(brand: string) {
        this.toggles[brand] = !this.toggles[brand];
        let datasrc = [];

        if (this.toggles.BMW) {
            datasrc = datasrc.concat(this.Bmwparts);
        }

        if (this.toggles.Audi) {
            datasrc = datasrc.concat(this.AudiParts);
        }

        if (this.toggles.VW) {
            datasrc = datasrc.concat(this.VWparts);
        }

        this.dataSource = new MatTableDataSource(datasrc);
        this.dataSource.sort = this.sort;
    }

    getPartList() {
        this.partHttpService.getPartList()
            .subscribe(
                (data: CarPart[]) => {
                    this.originalData = data;

                    this.Bmwparts = data.filter(x => x.brand === 'BMW');
                    this.AudiParts = data.filter(x => x.brand === 'Audi');
                    this.VWparts = data.filter(x => x.brand === 'VW');

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
            this.cart.push({ id: item.id, amount: 1 });
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
