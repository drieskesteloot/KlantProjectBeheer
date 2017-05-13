package be.mobyus.omj.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tijdsregistratie {
	
	//private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datum;
	
	private Integer aantalUren;
	private String omschrijving;
	private Boolean gevalideerd;
	private Boolean rejected;
	private Boolean submitted;

	@ManyToOne
	@JsonBackReference
	private Gebruiker gebruiker;

	@ManyToOne
	@JsonBackReference
	private Project project;

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

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}
	
	public Boolean getGevalideerd() {
		return gevalideerd;
	}

	public void setGevalideerd(Boolean gevalideerd) {
		this.gevalideerd = gevalideerd;
	}

	public Boolean getRejected() {
		return rejected;
	}

	public void setRejected(Boolean rejected) {
		this.rejected = rejected;
	}

	public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}

	public Gebruiker getGebruiker() {
		return gebruiker;
	}

	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker = gebruiker;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Tijdsregistratie [id=" + id + ", datum=" + datum + ", aantalUren=" + aantalUren + ", omschrijving="
				+ omschrijving + ", gevalideerd=" + gevalideerd + ", rejected=" + rejected + ", submitted=" + submitted
				+ ", gebruiker=" + gebruiker + ", project=" + project + "]";
	}
	
	

}
