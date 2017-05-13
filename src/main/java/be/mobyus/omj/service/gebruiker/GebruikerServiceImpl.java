package be.mobyus.omj.service.gebruiker;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import be.mobyus.omj.dao.GebruikerRepository;
import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Rol;

@Service
public class GebruikerServiceImpl implements GebruikerService {
	
	@Autowired
	GebruikerRepository gebruikerrepo;
	
	@Autowired
	ProjectRepository projectrepo;
	
	@Override
	public Gebruiker getGebruikerById(long id){
		return gebruikerrepo.findOne(id);
	}
	
	@Override
    public Optional<Gebruiker> getGebruikerByEmail(String email){
		return gebruikerrepo.findOneByEmail(email);
	}
	
	@Override
    public Collection<Gebruiker> getAllGebruikers(){
		return gebruikerrepo.findAll(new Sort("email"));
	}
	
	@Override
	public Collection<Gebruiker> getGebruikersByRol(Rol rol){
		return gebruikerrepo.findByRol(rol);
	}
	
	@Override
    public Gebruiker create(Gebruiker gebruiker){
		gebruiker.setWachtwoord(new BCryptPasswordEncoder().encode(gebruiker.getWachtwoord()));
		return gebruikerrepo.save(gebruiker);
	}
	
	@Override
	public Gebruiker update(Gebruiker gebruiker){
		/*
		Gebruiker gebruiker2 = new Gebruiker();
		gebruiker2.setNaam(gebruiker.getNaam());
		gebruiker2.setVoornaam((gebruiker.getVoornaam()));
		gebruiker2.setEmail(gebruiker.getEmail());
		gebruiker2.setWachtwoord(new BCryptPasswordEncoder().encode(gebruiker.getWachtwoord()));
		gebruiker2.setRol(gebruiker.getRol());
		return gebruikerrepo.save(gebruiker2);
		*/
		gebruiker.setWachtwoord(new BCryptPasswordEncoder().encode(gebruiker.getWachtwoord()));
		return gebruikerrepo.save(gebruiker);
	}
	
	@Override
	public Collection<Project> getProjectsByGebruiker(Gebruiker gebruiker){
		return projectrepo.findByGebruikers(gebruiker);
	}
	
	@Override
	public void delete(long id){
		gebruikerrepo.delete(id);
	}

}
