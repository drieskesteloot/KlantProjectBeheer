package be.mobyus.omj.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Tijdsregistratie {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotEmpty
	private Date datum;
	
	@NotEmpty
	private Integer aantalUren;
	
	@NotEmpty
	@ManyToMany
	private List<Gebruiker> gebruikers;
	
	@NotEmpty
	private String omschrijving;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Integer getAantalUren() {
		return aantalUren;
	}

	public void setAantalUren(Integer aantalUren) {
		this.aantalUren = aantalUren;
	}

	public List<Gebruiker> getGebruikers() {
		return gebruikers;
	}

	public void setGebruikers(List<Gebruiker> gebruikers) {
		this.gebruikers = gebruikers;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

}
