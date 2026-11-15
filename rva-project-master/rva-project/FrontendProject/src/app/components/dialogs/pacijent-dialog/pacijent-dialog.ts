import { Component, Inject, OnInit } from '@angular/core';

import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { PacijentService } from '../../../services/pacijent-service';
import { Pacijent } from '../../../models/pacijent';
import { MatSelectModule } from '@angular/material/select';
import { Odeljenje } from '../../../models/odeljenje';
import { OdeljenjeService } from '../../../services/odeljenje-service';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { Dijagnoza } from '../../../models/dijagnoza';
import { DijagnozaService } from '../../../services/dijagnoza-service';

@Component({
  selector: 'app-pacijent-dialog',
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    MatSelectModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ],
  templateUrl: './pacijent-dialog.html',
  styleUrl: './pacijent-dialog.css',
})
export class PacijentDialogComponent implements OnInit {
  flag!: number;
  odeljenja: Odeljenje[] = [];
  dijagnoze: Dijagnoza[] = [];
  maxDate = new Date();
  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<PacijentDialogComponent>,
    private service: PacijentService,
    private dijagnozaService: DijagnozaService,
    @Inject(MAT_DIALOG_DATA) public data: Pacijent,
    private odeljenjeService: OdeljenjeService,
  ) {}

  ngOnInit(): void {
    this.odeljenjeService.getAllOdeljenjes().subscribe((data) => (this.odeljenja = data));
    this.dijagnozaService.getAllDijagnozas().subscribe((data) => (this.dijagnoze = data));
    if (this.flag === 1 && this.data.zdrOsiguranje == null) {
      this.data.zdrOsiguranje = false;
    }
  }

  public add(): void {
    this.service.createPacijent(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Pacijent pod imenom: ${data.ime} ${data.prezime} je uspešno kreiran`,
          'Ok',
          { duration: 2500 },
        );
      },
      error: (err) => {
        this.snackBar.open(`Greška pri POST zahtevu!`, 'Ok', { duration: 2500 });
        console.log(err.message);
      },
    });
  }

  public update(): void {
    this.service.updatePacijent(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Pacijent pod imenom: ${data.ime} ${data.prezime} je uspešno ažuriran!`,
          'Ok',
          { duration: 2500 },
        );
      },
      error: (err) => {
        this.snackBar.open(`Greška pri PUT zahtevu!`, 'Ok', { duration: 2500 });
        console.log(err.message);
      },
    });
  }

  public delete(): void {
    this.service.deletePacijent(this.data.id).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Pacijent je uspešno obrisan!`, 'Ok', { duration: 2500 });
      },
      error: (err) => {
        this.snackBar.open(`Greška pri DELETE zahtevu!`, 'Ok', {
          duration: 2500,
        });
        console.log(err.message);
      },
    });
  }
  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open(`Odustali ste od izmena!`, 'Ok', { duration: 2500 });
  }
  public compare(a: any, b: any): boolean {
    if (!a || !b) {
      return false;
    }
    return a.id === b.id;
  }
}
