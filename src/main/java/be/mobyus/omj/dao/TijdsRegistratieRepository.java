package be.mobyus.omj.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Tijdsregistratie;

public interface TijdsRegistratieRepository extends JpaRepository<Tijdsregistratie, Long> {
	
	public Collection<Tijdsregistratie> findByGebruiker(Gebruiker gebruiker);
	public Collection<Tijdsregistratie> findByProject(Project project);
	public Collection<Tijdsregistratie> findByProjectAndGebruiker(Project project, Gebruiker gebruiker);
	public Collection<Tijdsregistratie> findBySubmitted(Boolean submitted);
	public Collection<Tijdsregistratie> findByGebruikerAndSubmitted(Gebruiker gebruiker, Boolean submitted);
	public Collection<Tijdsregistratie> findByRejected(Boolean rejected);
	public Collection<Tijdsregistratie> findByGebruikerAndRejected(Gebruiker gebruiker, Boolean rejected);
	public Collection<Tijdsregistratie> findByGevalideerd(Boolean gevalideerd);
	public Collection<Tijdsregistratie> findByGebruikerAndGevalideerd(Gebruiker gebruiker, Boolean gevalideerd);
	
}
