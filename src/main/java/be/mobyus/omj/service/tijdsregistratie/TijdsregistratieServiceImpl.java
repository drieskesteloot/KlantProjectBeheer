package be.mobyus.omj.service.tijdsregistratie;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import be.mobyus.omj.dao.GebruikerRepository;
import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.dao.TijdsRegistratieRepository;
import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Tijdsregistratie;

@Service
public class TijdsregistratieServiceImpl implements TijdsregistratieService {
	
	@Autowired
	TijdsRegistratieRepository tijdsregistratierepo;
	
	@Autowired
	GebruikerRepository gebruikerrepo;
	
	@Autowired
	ProjectRepository projectrepo;
	
	
	@Override
	public Tijdsregistratie getTijdsregistratieById(long id){
		return tijdsregistratierepo.findOne(id);
	}
	
	@Override
	public Collection<Tijdsregistratie> getAllTijdsregistraties(){
		return tijdsregistratierepo.findAll();
	}
	
	@Override
	public Collection<Tijdsregistratie> getTijdsregistratieByGebruiker(Gebruiker gebruiker){
		return tijdsregistratierepo.findByGebruiker(gebruiker);
	}
	
	@Override
	public Collection<Tijdsregistratie> getAllSubmittedTijdsregistraties(Boolean submitted){
		return tijdsregistratierepo.findBySubmitted(submitted);
	}
	
	@Override
	public Collection<Tijdsregistratie> getAllSubmittedTijdsregistratiesByGebruiker(Gebruiker gebruiker, Boolean submitted){
		return tijdsregistratierepo.findByGebruikerAndSubmitted(gebruiker, submitted);
	}

	@Override
	public Collection<Tijdsregistratie> getAllRejectedTijdsregistraties(Boolean rejected){
		return tijdsregistratierepo.findByRejected(rejected);
	}
	
	@Override
	public Collection<Tijdsregistratie> getAllRejectedTijdsregistratiesByGebruiker(Gebruiker gebruiker, Boolean rejected){
		return tijdsregistratierepo.findByGebruikerAndRejected(gebruiker, rejected);
	}
	
	@Override
	public Collection<Tijdsregistratie> getAllGevalideerdeTijdsregistraties(Boolean gevalideerd){
		return tijdsregistratierepo.findByGevalideerd(gevalideerd);
	}
	
	@Override
	public Collection<Tijdsregistratie> getAllGevalideerdeTijdsregistratiesByGebruiker(Gebruiker gebruiker, Boolean gevalideerd){
		return tijdsregistratierepo.findByGebruikerAndGevalideerd(gebruiker, gevalideerd);
	}

	@Override
	public Collection<Tijdsregistratie> getTijdsregistratieByProject(Project project){
		return tijdsregistratierepo.findByProject(project);
	}
	
	@Override
	public Collection<Tijdsregistratie> getTijdsRegistratieByProjectAndGebruiker(Project project, Gebruiker gebruiker){
		return tijdsregistratierepo.findByProjectAndGebruiker(project, gebruiker);
	}
	
	@Override
	public Tijdsregistratie create(Tijdsregistratie tijdsregistratie){
		return tijdsregistratierepo.save(tijdsregistratie);
	}
	
	@Override
	public Tijdsregistratie update(Tijdsregistratie tijdsregistratie){
		return tijdsregistratierepo.save(tijdsregistratie);
	}
	
}
