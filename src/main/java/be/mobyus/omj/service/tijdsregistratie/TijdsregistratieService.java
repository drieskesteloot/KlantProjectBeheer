package be.mobyus.omj.service.tijdsregistratie;

import java.util.Collection;
import java.util.List;

import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Tijdsregistratie;

public interface TijdsregistratieService {
	
	Tijdsregistratie getTijdsregistratieById(long id);
	
	Collection<Tijdsregistratie> getAllTijdsregistraties();
	
	Collection<Tijdsregistratie> getTijdsregistratieByGebruiker(Gebruiker gebruiker);
	
	Collection<Tijdsregistratie> getAllSubmittedTijdsregistraties(Boolean submitted);
	
	Collection<Tijdsregistratie> getAllSubmittedTijdsregistratiesByGebruiker(Gebruiker gebruiker, Boolean submitted);
	
	Collection<Tijdsregistratie> getAllRejectedTijdsregistraties(Boolean rejected);
	
	Collection<Tijdsregistratie> getAllRejectedTijdsregistratiesByGebruiker(Gebruiker gebruiker, Boolean rejected);
	
	Collection<Tijdsregistratie> getAllGevalideerdeTijdsregistraties(Boolean gevalideerd);
	
	Collection<Tijdsregistratie> getAllGevalideerdeTijdsregistratiesByGebruiker(Gebruiker gebruiker, Boolean gevalideerd);
	
	Collection<Tijdsregistratie> getTijdsregistratieByProject(Project project);
	
	Collection<Tijdsregistratie> getTijdsRegistratieByProjectAndGebruiker(Project project, Gebruiker gebruiker);
	
	Tijdsregistratie create(Tijdsregistratie tijdsregistratie);
	
	Tijdsregistratie update(Tijdsregistratie tijdsregistratie);

}
