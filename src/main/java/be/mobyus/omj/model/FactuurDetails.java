package be.mobyus.omj.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class FactuurDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String omschrijving;
	private float aantal;
	private float prijs;
	private float totaal;
	
	@ManyToOne
	@JsonBackReference
	private Factuur factuur;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public float getAantal() {
		return aantal;
	}

	public void setAantal(float aantal) {
		this.aantal = aantal;
	}

	public float getPrijs() {
		return prijs;
	}

	public void setPrijs(float prijs) {
		this.prijs = prijs;
	}

	public float getTotaal() {
		return totaal;
	}

	public void setTotaal(float totaal) {
		this.totaal = totaal;
	}

	public Factuur getFactuur() {
		return factuur;
	}

	public void setFactuur(Factuur factuur) {
		this.factuur = factuur;
	}

	@Override
	public String toString() {
		return "FactuurDetails [id=" + id + ", omschrijving=" + omschrijving + ", aantal=" + aantal + ", prijs=" + prijs
				+ ", totaal=" + totaal + ", factuur=" + factuur + "]";
	}
	
	
}
