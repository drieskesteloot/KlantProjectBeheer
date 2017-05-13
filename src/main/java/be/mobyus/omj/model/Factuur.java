package be.mobyus.omj.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Factuur implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private Date datum;
	private String omschrijving;
	private Date betaalDatum;
	private BigDecimal subTotaal;
	private Double BTW;
	private BigDecimal totaal;
	
	@ManyToOne
	@JsonBackReference
	private Klant klant;
	
	@OneToMany(mappedBy="factuur")
	@JsonManagedReference
	private List<FactuurDetails> details;
	
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

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public Date getBetaalDatum() {
		return betaalDatum;
	}

	public void setBetaalDatum(Date betaalDatum) {
		this.betaalDatum = betaalDatum;
	}

	public BigDecimal getSubTotaal() {
		return subTotaal;
	}

	public void setSubTotaal(BigDecimal subTotaal) {
		this.subTotaal = subTotaal;
	}

	public Double getBTW() {
		return BTW;
	}

	public void setBTW(Double bTW) {
		BTW = bTW;
	}

	public BigDecimal getTotaal() {
		return totaal;
	}

	public void setTotaal(BigDecimal totaal) {
		//totaal.multiply(new BigDecimal(this.BTW))
		this.totaal = totaal;
	}

	public Klant getKlant() {
		return klant;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public List<FactuurDetails> getDetails() {
		return details;
	}

	public void setDetails(List<FactuurDetails> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Factuur [id=" + id + ", datum=" + datum + ", omschrijving=" + omschrijving + ", betaalDatum="
				+ betaalDatum + ", subTotaal=" + subTotaal + ", BTW=" + BTW + ", totaal=" + totaal + "]";
	}
	
	

}
