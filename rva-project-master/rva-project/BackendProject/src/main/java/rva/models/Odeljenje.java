package rva.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Odeljenje {

	@Id
	@SequenceGenerator(name = "odeljenje_seq", sequenceName = "odeljenje_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "odeljenje_seq")

	private long id;
	private String naziv;
	private String lokacija;

	@ManyToOne
	@JoinColumn(name = "bolnica")
	private Bolnica bolnica;

	@OneToMany(mappedBy = "odeljenje", cascade = CascadeType.ALL)
	private List<Pacijent> pacijent;

	public Odeljenje() {
	}

	public Odeljenje(String naziv, String lokacija, Bolnica bolnica) {
		super();
		this.naziv = naziv;
		this.lokacija = lokacija;
		this.bolnica = bolnica;
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

	public String getLokacija() {
		return lokacija;
	}

	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}

	public Bolnica getBolnica() {
		return bolnica;
	}

	public void setBolnica(Bolnica bolnica) {
		this.bolnica = bolnica;
	}


}
