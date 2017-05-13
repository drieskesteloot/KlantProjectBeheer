package be.mobyus.omj.dao;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Rol;

public interface GebruikerRepository extends JpaRepository<Gebruiker, Long> {
	
	Optional<Gebruiker> findOneByEmail(String email);
	Collection<Gebruiker> findByRol(Rol rol);
	
}
