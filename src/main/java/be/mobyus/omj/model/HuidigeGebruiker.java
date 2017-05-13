package be.mobyus.omj.model;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class HuidigeGebruiker extends User {
	
	private static final long serialVersionUID = 1L;
	
	private Gebruiker gebruiker;
	
	public HuidigeGebruiker(Gebruiker gebruiker){
		super(gebruiker.getEmail(), gebruiker.getWachtwoord(), AuthorityUtils.createAuthorityList(gebruiker.getRol().toString()));
		this.gebruiker = gebruiker;
	}

	public Gebruiker getGebruiker() {
		return gebruiker;
	}
	
	public Long getId() {
        return gebruiker.getId();
    }

    public Rol getRol() {
        return gebruiker.getRol();
    }

    @Override
    public String toString() {
        return "HuidigeGebruiker{" +
                "gebruiker=" + gebruiker +
                "} " + super.toString();
    }
	
}
