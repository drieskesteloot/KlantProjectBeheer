package be.mobyus.omj.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Klant implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotEmpty
	private String naam;
	@NotEmpty
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String email;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Adres adres;
	
	private String telefoonNummer;
	
	@NotEmpty
	private String omschrijving;
	
	@OneToMany(mappedBy="klant", cascade = CascadeType.REMOVE)
	@JsonManagedReference
	List<Project> projecten;
	
	@OneToMany(mappedBy="klant")
	@JsonManagedReference
	List<Factuur> facturen;
	
	public Klant(){}

	public Klant(String naam, String email, Adres adres, String telefoonNummer, String omschrijving) {
		super();
		this.naam = naam;
		this.email = email;
		this.adres = adres;
		this.telefoonNummer = telefoonNummer;
		this.omschrijving = omschrijving;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public String getTelefoonNummer() {
		return telefoonNummer;
	}

	public void setTelefoonNummer(String telefoonNummer) {
		this.telefoonNummer = telefoonNummer;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}
	
	public List<Project> getProjecten() {
		return projecten;
	}

	public void setProjecten(List<Project> projecten) {
		this.projecten = projecten;
	}
	
	public List<Factuur> getFacturen() {
		return facturen;
	}

	public void setFacturen(List<Factuur> facturen) {
		this.facturen = facturen;
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (!Klant.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final Klant other = (Klant) obj;
	    if (!(this.email.equals(other.email))) {
	    	return false;
	    }
	    if (!(this.id == other.id)) {
	        return false;
	    }
	    return true;
	}

	@Override
	public String toString() {
		return "Klant [id=" + id + ", naam=" + naam + ", email=" + email + ", adres=" + adres + ", telefoonNummer="
				+ telefoonNummer + ", omschrijving=" + omschrijving + "]";
	}
	
	

}
