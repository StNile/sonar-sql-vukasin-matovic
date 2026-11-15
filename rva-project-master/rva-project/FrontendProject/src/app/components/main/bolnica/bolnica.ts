import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { BolnicaService } from '../../../services/bolnica-service';
import { Bolnica } from '../../../models/bolnica';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialog } from '@angular/material/dialog';
import { BolnicaDialogComponent } from '../../dialogs/bolnica-dialog/bolnica-dialog';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-bolnica',
  imports: [MatTableModule, MatIconModule, MatToolbarModule, MatSortModule, MatPaginatorModule],
  templateUrl: './bolnica.html',
  styleUrl: './bolnica.css',
})
export class BolnicaComponent implements OnInit, AfterViewInit {
  displayedColumns = ['id', 'naziv', 'adresa', 'budzet', 'actions'];
  dataSource: MatTableDataSource<Bolnica> = new MatTableDataSource<Bolnica>([]);
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private service: BolnicaService,
    private dialog: MatDialog,
  ) {}
  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
  ngOnInit(): void {
    this.loadData();
  }
  public loadData(): void {
    this.service.getAllBolnicas().subscribe({
      next: (data) => {
        this.dataSource.data = data.sort((a: Bolnica, b: Bolnica) => a.id - b.id);
        console.log(data);
      },
      error: (err) => console.log(err),
    });
  }

  public openDialog(
    flag: number,
    id?: number,
    naziv?: string,
    adresa?: string,
    budzet?: number,
  ): void {
    const ref = this.dialog.open(BolnicaDialogComponent, { data: { id, naziv, adresa, budzet } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response) {
        this.loadData();
      }
    });
  }
}
