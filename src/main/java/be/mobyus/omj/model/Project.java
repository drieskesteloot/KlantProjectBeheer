package be.mobyus.omj.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Project {
	
	//private static final long serialVersionUID = 1L;
	
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
	@JsonBackReference
	private Klant klant;

	private String contactPersoonKlant;
	
	@ManyToOne
	private Status status;
	
	@ManyToMany
	@JsonBackReference
	private List<Gebruiker> gebruikers;
	
	@OneToMany(mappedBy="project")
	@JsonManagedReference
	private List<Tijdsregistratie> tijdsRegistraties;
	
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
	
	public List<Tijdsregistratie> getTijdsRegistraties() {
		return tijdsRegistraties;
	}

	public void setTijdsRegistraties(List<Tijdsregistratie> tijdsRegistraties) {
		this.tijdsRegistraties = tijdsRegistraties;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", naam=" + naam + ", typeProject=" + typeProject + ", prijs=" + prijs
				+ ", startDatum=" + startDatum + ", eindDatum=" + eindDatum + ", klant=" + klant
				+ ", contactPersoonKlant=" + contactPersoonKlant + ", status=" + status + "]";
	}
	
	
	
}
