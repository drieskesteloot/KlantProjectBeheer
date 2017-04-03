package be.mobyus.omj.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Gebruiker {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String voornaam;
	
	private String naam;
	
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String email;
	
	private String wachtwoord;
	
	@Transient
	private String wachtwoordHerhaald;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	@ManyToMany
	private List<Project> projecten;
	
	@ManyToMany
	private List<Tijdsregistratie> tijdsRegistraties;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
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

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public String getWachtwoordHerhaald() {
		return wachtwoordHerhaald;
	}

	public void setWachtwoordHerhaald(String wachtwoordHerhaald) {
		this.wachtwoordHerhaald = wachtwoordHerhaald;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Project> getProjecten() {
		return projecten;
	}

	public void setProjecten(List<Project> projecten) {
		this.projecten = projecten;
	}

	public List<Tijdsregistratie> getTijdsRegistraties() {
		return tijdsRegistraties;
	}

	public void setTijdsRegistraties(List<Tijdsregistratie> tijdsRegistraties) {
		this.tijdsRegistraties = tijdsRegistraties;
	}
	

}
