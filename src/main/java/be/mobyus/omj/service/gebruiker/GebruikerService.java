package be.mobyus.omj.service.gebruiker;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import be.mobyus.omj.model.Gebruiker;

public interface GebruikerService {
	
	Optional<Gebruiker> getGebruikerById(long id);

    Optional<Gebruiker> getGebruikerByEmail(String email);

    Collection<Gebruiker> getAllGebruikers();

    Gebruiker create(Gebruiker gebruiker);
    
    void delete(long id);

}
