import {
  AfterViewInit,
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { PacijentService } from '../../../services/pacijent-service';
import { Pacijent } from '../../../models/pacijent';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialog } from '@angular/material/dialog';
import { PacijentDialogComponent } from '../../dialogs/pacijent-dialog/pacijent-dialog';
import { DatePipe } from '@angular/common';
import { Odeljenje } from '../../../models/odeljenje';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { Dijagnoza } from '../../../models/dijagnoza';

@Component({
  selector: 'app-pacijent',
  imports: [
    MatTableModule,
    MatIconModule,
    MatToolbarModule,
    DatePipe,
    MatSortModule,
    MatPaginatorModule,
  ],
  templateUrl: './pacijent.html',
  styleUrl: './pacijent.css',
})
export class PacijentComponent implements OnInit, OnChanges, AfterViewInit {
  displayedColumns = [
    'id',
    'ime',
    'prezime',
    'zdrOsiguranje',
    'datumRodjenja',
    'dijagnoza',
    'actions',
  ];
  dataSource: MatTableDataSource<Pacijent> = new MatTableDataSource<Pacijent>([]);
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @Input()
  childSelectedOdeljenje!: Odeljenje;

  constructor(
    private service: PacijentService,
    private dialog: MatDialog,
  ) {}
  ngAfterViewInit(): void {
    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'bolnica':
          return item.dijagnoza?.naziv ?? '';
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
  ngOnChanges(changes: SimpleChanges): void {
    if (this.childSelectedOdeljenje) {
      this.loadData();
    }
  }

  public loadData(): void {
    this.service.getAllPacijents().subscribe({
      next: (data) => {
        if (this.childSelectedOdeljenje) {
          this.dataSource.data = data.filter(
            (p: Pacijent) => p.odeljenje?.id === this.childSelectedOdeljenje.id,
          );
        } else {
          this.dataSource.data = data;
        }
      },
      error: (err) => console.log(err),
    });
  }

  public openDialog(
    flag: number,
    id?: number,
    ime?: string,
    prezime?: string,
    zdrOsiguranje?: boolean,
    datumRodjenja?: Date,
    odeljenje?: Odeljenje,
    dijagnoza?: Dijagnoza,
  ): void {
    const ref = this.dialog.open(PacijentDialogComponent, {
      data: { id, ime, prezime, zdrOsiguranje, datumRodjenja, odeljenje, dijagnoza },
    });

    ref.componentInstance.flag = flag;

    ref.afterClosed().subscribe((response) => {
      if (response) {
        this.loadData();
      }
    });
  }
}
