import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { DijagnozaService } from '../../../services/dijagnoza-service';
import { Dijagnoza } from '../../../models/dijagnoza';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialog } from '@angular/material/dialog';
import { DijagnozaDialogComponent } from '../../dialogs/dijagnoza-dialog/dijagnoza-dialog';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-dijagnoza',
  imports: [MatTableModule, MatIconModule, MatToolbarModule, MatPaginatorModule, MatSortModule],
  templateUrl: './dijagnoza.html',
  styleUrl: './dijagnoza.css',
})
export class DijagnozaComponent implements OnInit, AfterViewInit {
  displayedColumns = ['id', 'naziv', 'opis', 'oznaka', 'actions'];
  dataSource: MatTableDataSource<Dijagnoza> = new MatTableDataSource<Dijagnoza>([]);
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    private service: DijagnozaService,
    private dialog: MatDialog,
  ) {
    this.loadData();
  }
  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
  ngOnInit(): void {
    // this.loadData();
  }
  public loadData(): void {
    this.service.getAllDijagnozas().subscribe({
      next: (data) => {
        this.dataSource.data = data.sort((a: Dijagnoza, b: Dijagnoza) => a.id - b.id);
      },
      error: (err) => console.log(err),
    });
  }

  public openDialog(
    flag: number,
    id?: number,
    naziv?: string,
    opis?: string,
    oznaka?: string,
  ): void {
    const ref = this.dialog.open(DijagnozaDialogComponent, { data: { id, naziv, opis, oznaka } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response) {
        this.loadData();
      }
    });
  }
}
