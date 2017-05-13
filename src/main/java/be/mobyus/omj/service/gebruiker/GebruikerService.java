package be.mobyus.omj.service.gebruiker;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Rol;

public interface GebruikerService {
	
	Gebruiker getGebruikerById(long id);

    Optional<Gebruiker> getGebruikerByEmail(String email);

    Collection<Gebruiker> getAllGebruikers();
    
    Collection<Gebruiker> getGebruikersByRol(Rol rol);

    Gebruiker create(Gebruiker gebruiker);
    
    Gebruiker update(Gebruiker gebruiker);
    
    void delete(long id);
    
    Collection<Project> getProjectsByGebruiker(Gebruiker gebruiker);

}
