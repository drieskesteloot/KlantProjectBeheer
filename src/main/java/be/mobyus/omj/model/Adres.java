package be.mobyus.omj.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Adres implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String straat;
	private String huisNummer;
	private String bus;
	private String postcode;
	private String gemeente;
	
	public Adres(){}
	
	public Adres(String straat, String huisNummer, String bus, String postcode, String gemeente) {
		super();
		this.straat = straat;
		this.huisNummer = huisNummer;
		this.bus = bus;
		this.postcode = postcode;
		this.gemeente = gemeente;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getStraat() {
		return straat;
	}
	
	public void setStraat(String straat) {
		this.straat = straat;
	}
	
	public String getHuisNummer() {
		return huisNummer;
	}
	public void setHuisNummer(String huisNummer) {
		this.huisNummer = huisNummer;
	}
	
	public String getBus() {
		return bus;
	}
	
	public void setBus(String bus) {
		this.bus = bus;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getGemeente() {
		return gemeente;
	}
	
	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}

	@Override
	public String toString() {
		return "Adres [id=" + id + ", straat=" + straat + ", huisNummer=" + huisNummer + ", bus=" + bus + ", postcode="
				+ postcode + ", gemeente=" + gemeente + "]";
	}
	
	
	

}
