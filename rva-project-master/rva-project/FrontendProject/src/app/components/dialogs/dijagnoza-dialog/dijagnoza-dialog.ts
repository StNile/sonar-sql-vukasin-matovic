import { Component, Inject } from '@angular/core';

import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { DijagnozaService } from '../../../services/dijagnoza-service';
import { Dijagnoza } from '../../../models/dijagnoza';

@Component({
  selector: 'app-dijagnoza-dialog',
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule, FormsModule],
  templateUrl: './dijagnoza-dialog.html',
  styleUrl: './dijagnoza-dialog.css',
})
export class DijagnozaDialogComponent {
  flag!: number;
  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<DijagnozaDialogComponent>,
    private service: DijagnozaService,
    @Inject(MAT_DIALOG_DATA) public data: Dijagnoza,
  ) {}
  public add(): void {
    this.service.createDijagnoza(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Dijagnoza pod nazivom: ${data.naziv} je uspešno kreirana`,
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
    this.service.updateDijagnoza(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Dijagnoza pod nazivom: ${data.naziv} je uspešno ažurirana!`,
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
    this.service.deteleDijagnoza(this.data.id).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Dijagnoza je uspešno obrisana!`, 'Ok', { duration: 2500 });
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
}
