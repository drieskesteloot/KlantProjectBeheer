package be.mobyus.omj.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class HulpZoekOpDatum implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDatum;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date eindDatum;
	
	private Gebruiker gebruiker;
	
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

	public Gebruiker getGebruiker() {
		return gebruiker;
	}

	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker = gebruiker;
	}
	
	
	
}
