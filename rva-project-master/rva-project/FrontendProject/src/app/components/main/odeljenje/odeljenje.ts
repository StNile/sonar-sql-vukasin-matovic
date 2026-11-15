import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { OdeljenjeService } from '../../../services/odeljenje-service';
import { Odeljenje } from '../../../models/odeljenje';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialog } from '@angular/material/dialog';
import { OdeljenjeDialogComponent } from '../../dialogs/odeljenje-dialog/odeljenje-dialog';
import { CommonModule } from '@angular/common';
import { PacijentComponent } from '../pacijent/pacijent';
import { Bolnica } from '../../../models/bolnica';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-odeljenje',
  imports: [
    MatTableModule,
    MatIconModule,
    MatToolbarModule,
    PacijentComponent,
    MatPaginatorModule,
    MatSortModule,
  ],
  templateUrl: './odeljenje.html',
  styleUrl: './odeljenje.css',
})
export class OdeljenjeComponent implements OnInit, AfterViewInit {
  displayedColumns = ['id', 'naziv', 'lokacija', 'bolnica', 'actions'];
  dataSource: MatTableDataSource<Odeljenje> = new MatTableDataSource<Odeljenje>([]);
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  parentSelectedOdeljenje!: Odeljenje;
  constructor(
    private service: OdeljenjeService,
    private dialog: MatDialog,
  ) {}
  ngAfterViewInit(): void {
    /*sortiranje po bolnici ne radi bez ovoga */
    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'bolnica':
          return item.bolnica?.naziv ?? '';
        default:
          return (item as any)[property];
      }
    };

    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
  ngOnInit(): void {
    this.loadData();
  }
  public loadData(): void {
    this.service.getAllOdeljenjes().subscribe({
      next: (data) => {
        this.dataSource.data = data.sort((a: Odeljenje, b: Odeljenje) => a.id - b.id);
      },
      error: (err) => console.log(err),
    });
  }

  public openDialog(
    flag: number,
    id?: number,
    naziv?: string,
    lokacija?: string,
    bolnica?: Bolnica,
  ): void {
    const ref = this.dialog.open(OdeljenjeDialogComponent, {
      data: { id, naziv, lokacija, bolnica },
    });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response) {
        this.loadData();
      }
    });
  }
  public selectRow(row: Odeljenje) {
    this.parentSelectedOdeljenje = row;
  }
}
