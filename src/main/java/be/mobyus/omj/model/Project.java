package be.mobyus.omj.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Project {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String naam;

	@ManyToOne
	private TypeProject typeProject;
	
	private float prijs;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDatum;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date eindDatum;
	
	@ManyToOne
	private Klant klant;

	private String contactPersoonKlant;
	
	@ManyToOne
	private Status status;
	
	@ManyToMany
	private List<Gebruiker> gebruikers;
	
	public Project(){}

	public Project(String naam, TypeProject typeProject, float prijs, Date startDatum, Date eindDatum, Klant klant,
			String contactPersoonKlant, Status status) {
		super();
		this.naam = naam;
		this.typeProject = typeProject;
		this.prijs = prijs;
		this.startDatum = startDatum;
		this.eindDatum = eindDatum;
		this.klant = klant;
		this.contactPersoonKlant = contactPersoonKlant;
		this.status = status;
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
	
	public TypeProject getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(TypeProject typeProject) {
		this.typeProject = typeProject;
	}

	public float getPrijs() {
		return prijs;
	}

	public void setPrijs(float prijs) {
		this.prijs = prijs;
	}

	public Date getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(Date startDatum) {
		this.startDatum = startDatum;
	}

	public Date getEindDatum() {
		return eindDatum;
	}

	public void setEindDatum(Date eindDatum) {
		this.eindDatum = eindDatum;
	}

	public Klant getKlant() {
		return klant;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public String getContactPersoonKlant() {
		return contactPersoonKlant;
	}

	public void setContactPersoonKlant(String contactPersoonKlant) {
		this.contactPersoonKlant = contactPersoonKlant;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Gebruiker> getGebruikers() {
		return gebruikers;
	}

	public void setGebruikers(List<Gebruiker> gebruikers) {
		this.gebruikers = gebruikers;
	}
	
	
	
}
