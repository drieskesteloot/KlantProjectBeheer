package be.mobyus.omj;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import be.mobyus.omj.dao.AdresRepository;
import be.mobyus.omj.dao.KlantRepository;
import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.dao.StatusRepository;
import be.mobyus.omj.dao.TypeProjectRepository;
import be.mobyus.omj.model.Adres;
import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Klant;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Rol;
import be.mobyus.omj.model.Status;
import be.mobyus.omj.model.TypeProject;
import be.mobyus.omj.service.gebruiker.GebruikerService;

@SpringBootApplication
public class KlantProjectBeheerApplication implements CommandLineRunner {
	
	@Autowired
	AdresRepository adresrepo;
	
	@Autowired
	KlantRepository klantrepo;
	
	@Autowired
	ProjectRepository projectrepo;
	
	@Autowired
	StatusRepository statusrepo;
	
	@Autowired
	TypeProjectRepository typeprojectrepo;
	
	@Autowired
	GebruikerService gebruikerService;

	public static void main(String[] args) {
		SpringApplication.run(KlantProjectBeheerApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
		TypeProject fixedPrice = new TypeProject("Fixed price");
		TypeProject timeMaterial = new TypeProject("Time & Material");
		typeprojectrepo.save(fixedPrice);
		typeprojectrepo.save(timeMaterial);
		
		Status nieuw = new Status("Nieuw");
		Status bezig = new Status("Bezig");
		Status geannuleerd = new Status("Geannuleerd");
		Status wachtopklant = new Status("Wacht op Klant");
		Status opgeleverd = new Status("Opgeleverd");
		statusrepo.save(nieuw);
		statusrepo.save(bezig);
		statusrepo.save(geannuleerd);
		statusrepo.save(wachtopklant);
		statusrepo.save(opgeleverd);
		
		Klant mobyus = new Klant("Mobyus", "mobyus@mobyus.com", 
				new Adres("Geldenaaksebaan", "335", "", "3001", "Leuven"), "016/37 57 10", "Moved by us");
		klantrepo.save(mobyus);
		
		Klant john = new Klant("John Doe", "john.doe@johndoe.com", 
				new Adres("Grote baan", "25", "C", "9000", "Gent"), "", "Moving mind");
		klantrepo.save(john);
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 60);
		Project eersteProject = new Project("Mijn eerste project", fixedPrice, 2500, new Date(), cal.getTime(), 
				mobyus, "contact mobyus", nieuw);
		projectrepo.save(eersteProject);
		
		Gebruiker gebruikerAdmin = new Gebruiker();
		gebruikerAdmin.setNaam("Doe");
		gebruikerAdmin.setVoornaam("John");
		gebruikerAdmin.setEmail("john.doe@test.com");
		gebruikerAdmin.setWachtwoord("Test");
		gebruikerAdmin.setRol(Rol.ADMIN);
		gebruikerService.create(gebruikerAdmin);
		
		Gebruiker gebruikerNormal = new Gebruiker();
		gebruikerNormal.setNaam("Smith");
		gebruikerNormal.setVoornaam("Adam");
		gebruikerNormal.setEmail("adam.smith@test.com");
		gebruikerNormal.setWachtwoord("Test");
		gebruikerNormal.setRol(Rol.NORMAL);
		gebruikerService.create(gebruikerNormal);
		
	}
	
}
