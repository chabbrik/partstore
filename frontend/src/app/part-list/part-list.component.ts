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
    displayedColumns: string[] = ['name', 'brand', 'year', 'price'];
    dataSource: MatTableDataSource<CarPart>;

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

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }
}
