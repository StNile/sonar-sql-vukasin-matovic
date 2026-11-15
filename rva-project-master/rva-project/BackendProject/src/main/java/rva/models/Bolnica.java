package rva.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Bolnica {
	@Id
	@SequenceGenerator(name = "bolnica_seq", sequenceName = "bolnica_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bolnica_seq")
	private long id;
	private String naziv;
	private String adresa;
	private double budzet;

	@OneToMany(mappedBy = "bolnica", cascade = CascadeType.ALL)
	private List<Odeljenje> odeljenje;

	public Bolnica() {
	}

	public Bolnica(String naziv, String adresa, double budzet) {
		super();
		this.naziv = naziv;

		this.adresa = adresa;
		this.budzet = budzet;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public double getBudzet() {
		return budzet;
	}

	public void setBudzet(double budzet) {
		this.budzet = budzet;
	}

}
