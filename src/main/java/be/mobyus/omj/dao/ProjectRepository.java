package be.mobyus.omj.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Klant;
import be.mobyus.omj.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	public Collection<Project> findByGebruikers(Gebruiker gebruiker);
	public Collection<Project> findByKlant(Klant klant);

}
