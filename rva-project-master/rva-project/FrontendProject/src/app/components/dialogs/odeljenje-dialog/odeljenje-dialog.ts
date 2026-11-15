import { Component, Inject, OnInit } from '@angular/core';

import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { OdeljenjeService } from '../../../services/odeljenje-service';
import { Odeljenje } from '../../../models/odeljenje';
import { Bolnica } from '../../../models/bolnica';
import { BolnicaService } from '../../../services/bolnica-service';
import { MatSelectModule } from '@angular/material/select';
import { PacijentService } from '../../../services/pacijent-service';
import { Pacijent } from '../../../models/pacijent';
@Component({
  selector: 'app-odeljenje-dialog',
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    MatSelectModule,
  ],
  templateUrl: './odeljenje-dialog.html',
  styleUrl: './odeljenje-dialog.css',
})
export class OdeljenjeDialogComponent implements OnInit {
  flag!: number;
  bolnice: Bolnica[] = [];
  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<OdeljenjeDialogComponent>,
    private service: OdeljenjeService,
    @Inject(MAT_DIALOG_DATA) public data: Odeljenje,
    private bolnicaService: BolnicaService,
    private pacijentService: PacijentService,
  ) {}

  ngOnInit(): void {
    this.bolnicaService.getAllBolnicas().subscribe((data) => (this.bolnice = data));
  }

  public add(): void {
    this.service.createOdeljenje(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Odeljenje pod nazivom: ${data.naziv} je uspešno kreirano`,
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
    this.service.updateOdeljenje(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Odeljenje pod nazivom:${data.naziv} je uspešno ažurirano!`,
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
    this.pacijentService.getAllPacijents().subscribe({
      next: (pacijenti) => {
        const imaPacijenata = pacijenti.some((p: Pacijent) => p.odeljenje?.id === this.data.id);

        if (imaPacijenata) {
          this.snackBar.open(
            'Ne možete obrisati odeljenje jer postoje pacijenti u njemu!',
            'Ok',
            { duration: 3000 },
          );
          return;
        }

        this.service.deleteOdeljenje(this.data.id).subscribe({
          next: () => {
            this.dialogRef.close(1);
            this.snackBar.open('Odeljenje je uspešno obrisano!', 'Ok', { duration: 2500 });
          },
          error: (err) => {
            this.snackBar.open('Greška pri DELETE zahtevu!', 'Ok', {
              duration: 2500,
            });
            console.log(err.message);
          },
        });
      },
      error: (err) => {
        this.snackBar.open('Greška pri proveri pacijenata!', 'Ok', { duration: 2500 });
        console.log(err.message);
      },
    });
  }
  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open(`Odustali ste od izmena!`, 'Ok', { duration: 2500 });
  }
  public compare(a: any, b: any) {
    return a.id == b.id;
  }
}
