package rva.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Pacijent {

	@Id
	@SequenceGenerator(name = "pacijent_seq", sequenceName = "pacijent_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pacijent_seq")

	private long id;
	private String ime;
	private String prezime;
	@Column(name = "datum_rodjenja")
	private Date datumRodjenja;
	@Column(name = "zdr_osiguranje")
	private boolean zdrOsiguranje;

	@ManyToOne
	@JoinColumn(name = "odeljenje")
	private Odeljenje odeljenje;

	@ManyToOne
	@JoinColumn(name = "dijagnoza")
	private Dijagnoza dijagnoza;

	public Pacijent() {
	}

	public Pacijent(String ime, String prezime, Date datumRodjenja, boolean zdrOsiguranje, Odeljenje odeljenje,
			Dijagnoza dijagnoza) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.zdrOsiguranje = zdrOsiguranje;
		this.odeljenje = odeljenje;
		this.dijagnoza = dijagnoza;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public boolean isZdrOsiguranje() {
		return zdrOsiguranje;
	}

	public void setZdrOsiguranje(boolean zdrOsiguranje) {
		this.zdrOsiguranje = zdrOsiguranje;
	}

	public Odeljenje getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(Odeljenje odeljenje) {
		this.odeljenje = odeljenje;
	}

	public Dijagnoza getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(Dijagnoza dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

}
