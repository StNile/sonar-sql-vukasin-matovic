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

public class Dijagnoza {
	@Id
	@SequenceGenerator(name = "dijagnoza_seq", sequenceName = "dijagnoza_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dijagnoza_seq")
	private long id;
	private String naziv;
	private String opis;
	private String oznaka;

	@OneToMany(mappedBy = "dijagnoza", cascade = CascadeType.ALL)
	private List<Pacijent> pacijent;

	public Dijagnoza() {
	}

	public Dijagnoza(String naziv, String opis, String oznaka) {
		super();
		this.naziv = naziv;
		this.opis = opis;
		this.oznaka = oznaka;
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

}
