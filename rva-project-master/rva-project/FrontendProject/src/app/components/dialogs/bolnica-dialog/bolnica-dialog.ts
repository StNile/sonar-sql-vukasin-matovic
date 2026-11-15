import { Component, Inject } from '@angular/core';

import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { BolnicaService } from '../../../services/bolnica-service';
import { Bolnica } from '../../../models/bolnica';

@Component({
  selector: 'app-bolnica-dialog',
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule, FormsModule],
  templateUrl: './bolnica-dialog.html',
  styleUrl: './bolnica-dialog.css',
})
export class BolnicaDialogComponent {
  flag!: number;
  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<BolnicaDialogComponent>,
    private service: BolnicaService,
    @Inject(MAT_DIALOG_DATA) public data: Bolnica,
  ) {}
  private validacija(): boolean {
    if (isNaN(Number(this.data.budzet))) {
      this.snackBar.open('Budžet mora biti broj!', 'Ok', { duration: 2500 });
      return false;
    }

    if (Number(this.data.budzet) <= 0) {
      this.snackBar.open('Budžet mora biti veći od 0!', 'Ok', { duration: 2500 });
      return false;
    }

    return true;
  }
  public add(): void {
    if (!this.validacija()) {
      return;
    }
    this.service.createBolnica(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Bolnica pod nazivom: ${data.naziv} je uspešno kreirana`,
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
    if (!this.validacija()) {
      return;
    }
    this.service.updateBolnica(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Bolnica pod nazivom: ${data.naziv} je uspešno ažurirana!`,
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
    this.service.deleteBolnica(this.data.id).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Bolnica je uspešno obrisana!`, 'Ok', { duration: 2500 });
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
