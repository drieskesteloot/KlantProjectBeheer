package be.mobyus.omj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
import be.mobyus.omj.model.Tijdsregistratie;
import be.mobyus.omj.model.TypeProject;
import be.mobyus.omj.service.gebruiker.GebruikerService;
import be.mobyus.omj.service.tijdsregistratie.TijdsregistratieService;

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
	
	@Autowired
	TijdsregistratieService tijdsregistratieService;

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
		
		Gebruiker gebruikerNormal2 = new Gebruiker();
		gebruikerNormal2.setNaam("Smith2");
		gebruikerNormal2.setVoornaam("Adam2");
		gebruikerNormal2.setEmail("adam.smith@test2.com");
		gebruikerNormal2.setWachtwoord("Test");
		gebruikerNormal2.setRol(Rol.NORMAL);
		gebruikerService.create(gebruikerNormal2);
		
		List<Gebruiker> normalGebruikers = new ArrayList<>();
		normalGebruikers.add(gebruikerNormal);
		normalGebruikers.add(gebruikerNormal2);
		List<Gebruiker> normalGebruikerAdam = new ArrayList<>();
		normalGebruikerAdam.add(gebruikerNormal);
		List<Gebruiker> normalGebruikerAdam2 = new ArrayList<>();
		normalGebruikerAdam2.add(gebruikerNormal2);
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 60);
		Project eersteProject = new Project("Eerste project", fixedPrice, 2500, new Date(), cal.getTime(), 
				mobyus, "contact mobyus", nieuw);
		eersteProject.setGebruikers(normalGebruikers);
		projectrepo.save(eersteProject);
		
		cal.add(Calendar.DATE, 30);
		Project tweedeProject = new Project("Tweede project", timeMaterial, 50000, new Date(), cal.getTime(),
				john, "contact john", nieuw);
		tweedeProject.setGebruikers(normalGebruikerAdam);
		projectrepo.save(tweedeProject);
		
		Project derdeProject = new Project("Derde project", fixedPrice, 100000, new Date(), cal.getTime(),
				mobyus, "contact mobyus", bezig);
		derdeProject.setGebruikers(normalGebruikerAdam2);
		projectrepo.save(derdeProject);
		
		cal.add(Calendar.DATE, 150);
		Project vierdeProject = new Project("Vierde project", timeMaterial, 200000, new Date(), cal.getTime(),
				john, "contact john", bezig);
		vierdeProject.setGebruikers(normalGebruikers);
		projectrepo.save(vierdeProject);
		
		Tijdsregistratie tijdsregistratie1 = new Tijdsregistratie();
		tijdsregistratie1.setProject(eersteProject);
		tijdsregistratie1.setGebruiker(gebruikerNormal);
		Date tijdsregistratieDatum = parseDate("2017-04-01");
		tijdsregistratie1.setDatum(tijdsregistratieDatum);
		tijdsregistratie1.setOmschrijving("test");
		tijdsregistratie1.setAantalUren(1);
		tijdsregistratie1.setSubmitted(false);
		tijdsregistratie1.setRejected(false);
		tijdsregistratie1.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie1);
		Tijdsregistratie tijdsregistratie2 = new Tijdsregistratie();
		tijdsregistratie2.setProject(eersteProject);
		tijdsregistratie2.setGebruiker(gebruikerNormal);
		tijdsregistratieDatum = parseDate("2017-04-02");
		tijdsregistratie2.setDatum(tijdsregistratieDatum);
		tijdsregistratie2.setOmschrijving("test2");
		tijdsregistratie2.setAantalUren(2);
		tijdsregistratie2.setSubmitted(true);
		tijdsregistratie2.setRejected(true);
		tijdsregistratie2.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie2);
		Tijdsregistratie tijdsregistratie3 = new Tijdsregistratie();
		tijdsregistratie3.setProject(eersteProject);
		tijdsregistratie3.setGebruiker(gebruikerNormal2);
		tijdsregistratieDatum = parseDate("2017-04-03");
		tijdsregistratie3.setDatum(tijdsregistratieDatum);
		tijdsregistratie3.setOmschrijving("test3");
		tijdsregistratie3.setAantalUren(3);
		tijdsregistratie3.setSubmitted(false);
		tijdsregistratie3.setRejected(false);
		tijdsregistratie3.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie3);
		Tijdsregistratie tijdsregistratie4 = new Tijdsregistratie();
		tijdsregistratie4.setProject(eersteProject);
		tijdsregistratie4.setGebruiker(gebruikerNormal2);
		tijdsregistratieDatum = parseDate("2017-04-04");
		tijdsregistratie4.setDatum(tijdsregistratieDatum);
		tijdsregistratie4.setOmschrijving("test4");
		tijdsregistratie4.setAantalUren(4);
		tijdsregistratie4.setSubmitted(false);
		tijdsregistratie4.setRejected(false);
		tijdsregistratie4.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie4);
		
		Tijdsregistratie tijdsregistratie5 = new Tijdsregistratie();
		tijdsregistratie5.setProject(tweedeProject);
		tijdsregistratie5.setGebruiker(gebruikerNormal);
		tijdsregistratieDatum = parseDate("2017-04-05");
		tijdsregistratie5.setDatum(tijdsregistratieDatum);
		tijdsregistratie5.setOmschrijving("test5");
		tijdsregistratie5.setAantalUren(5);
		tijdsregistratie5.setSubmitted(true);
		tijdsregistratie5.setRejected(true);
		tijdsregistratie5.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie5);
		Tijdsregistratie tijdsregistratie6 = new Tijdsregistratie();
		tijdsregistratie6.setProject(tweedeProject);
		tijdsregistratie6.setGebruiker(gebruikerNormal);
		tijdsregistratieDatum = parseDate("2017-04-06");
		tijdsregistratie6.setDatum(tijdsregistratieDatum);
		tijdsregistratie6.setOmschrijving("test6");
		tijdsregistratie6.setAantalUren(6);
		tijdsregistratie6.setSubmitted(false);
		tijdsregistratie6.setRejected(false);
		tijdsregistratie6.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie6);
		Tijdsregistratie tijdsregistratie7 = new Tijdsregistratie();
		tijdsregistratie7.setProject(tweedeProject);
		tijdsregistratie7.setGebruiker(gebruikerNormal);
		tijdsregistratieDatum = parseDate("2017-04-07");
		tijdsregistratie7.setDatum(tijdsregistratieDatum);
		tijdsregistratie7.setOmschrijving("test7");
		tijdsregistratie7.setAantalUren(7);
		tijdsregistratie7.setSubmitted(true);
		tijdsregistratie7.setRejected(false);
		tijdsregistratie7.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie7);
		Tijdsregistratie tijdsregistratie8 = new Tijdsregistratie();
		tijdsregistratie8.setProject(tweedeProject);
		tijdsregistratie8.setGebruiker(gebruikerNormal);
		tijdsregistratieDatum = parseDate("2017-04-08");
		tijdsregistratie8.setDatum(tijdsregistratieDatum);
		tijdsregistratie8.setOmschrijving("test8");
		tijdsregistratie8.setAantalUren(8);
		tijdsregistratie8.setSubmitted(true);
		tijdsregistratie8.setRejected(false);
		tijdsregistratie8.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie8);
		
		Tijdsregistratie tijdsregistratie9 = new Tijdsregistratie();
		tijdsregistratie9.setProject(derdeProject);
		tijdsregistratie9.setGebruiker(gebruikerNormal2);
		tijdsregistratieDatum = parseDate("2017-04-09");
		tijdsregistratie9.setDatum(tijdsregistratieDatum);
		tijdsregistratie9.setOmschrijving("test9");
		tijdsregistratie9.setAantalUren(9);
		tijdsregistratie9.setSubmitted(false);
		tijdsregistratie9.setRejected(false);
		tijdsregistratie9.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie9);
		Tijdsregistratie tijdsregistratie10 = new Tijdsregistratie();
		tijdsregistratie10.setProject(derdeProject);
		tijdsregistratie10.setGebruiker(gebruikerNormal2);
		tijdsregistratieDatum = parseDate("2017-04-10");
		tijdsregistratie10.setDatum(tijdsregistratieDatum);
		tijdsregistratie10.setOmschrijving("test10");
		tijdsregistratie10.setAantalUren(10);
		tijdsregistratie10.setSubmitted(false);
		tijdsregistratie10.setRejected(false);
		tijdsregistratie10.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie10);
		Tijdsregistratie tijdsregistratie11 = new Tijdsregistratie();
		tijdsregistratie11.setProject(derdeProject);
		tijdsregistratie11.setGebruiker(gebruikerNormal2);
		tijdsregistratieDatum = parseDate("2017-04-11");
		tijdsregistratie11.setDatum(tijdsregistratieDatum);
		tijdsregistratie11.setOmschrijving("test11");
		tijdsregistratie11.setAantalUren(11);
		tijdsregistratie11.setSubmitted(true);
		tijdsregistratie11.setRejected(false);
		tijdsregistratie11.setGevalideerd(true);
		
		tijdsregistratieService.create(tijdsregistratie11);
		Tijdsregistratie tijdsregistratie12 = new Tijdsregistratie();
		tijdsregistratie12.setProject(derdeProject);
		tijdsregistratie12.setGebruiker(gebruikerNormal2);
		tijdsregistratieDatum = parseDate("2017-04-12");
		tijdsregistratie12.setDatum(tijdsregistratieDatum);
		tijdsregistratie12.setOmschrijving("test12");
		tijdsregistratie12.setAantalUren(12);
		tijdsregistratie12.setSubmitted(true);
		tijdsregistratie12.setRejected(false);
		tijdsregistratie12.setGevalideerd(true);
		tijdsregistratieService.create(tijdsregistratie12);
		
		Tijdsregistratie tijdsregistratie13 = new Tijdsregistratie();
		tijdsregistratie13.setProject(vierdeProject);
		tijdsregistratie13.setGebruiker(gebruikerNormal);
		tijdsregistratieDatum = parseDate("2017-04-13");
		tijdsregistratie13.setDatum(tijdsregistratieDatum);
		tijdsregistratie13.setOmschrijving("test13");
		tijdsregistratie13.setAantalUren(13);
		tijdsregistratie13.setSubmitted(true);
		tijdsregistratie13.setRejected(false);
		tijdsregistratie13.setGevalideerd(true);
		tijdsregistratieService.create(tijdsregistratie13);
		Tijdsregistratie tijdsregistratie14 = new Tijdsregistratie();
		tijdsregistratie14.setProject(vierdeProject);
		tijdsregistratie14.setGebruiker(gebruikerNormal);
		tijdsregistratieDatum = parseDate("2017-04-14");
		tijdsregistratie14.setDatum(tijdsregistratieDatum);
		tijdsregistratie14.setOmschrijving("test14");
		tijdsregistratie14.setAantalUren(14);
		tijdsregistratie14.setSubmitted(false);
		tijdsregistratie14.setRejected(false);
		tijdsregistratie14.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie14);
		Tijdsregistratie tijdsregistratie15 = new Tijdsregistratie();
		tijdsregistratie15.setProject(vierdeProject);
		tijdsregistratie15.setGebruiker(gebruikerNormal2);
		tijdsregistratieDatum = parseDate("2017-04-15");
		tijdsregistratie15.setDatum(tijdsregistratieDatum);
		tijdsregistratie15.setOmschrijving("test15");
		tijdsregistratie15.setAantalUren(15);
		tijdsregistratie15.setSubmitted(true);
		tijdsregistratie15.setRejected(false);
		tijdsregistratie15.setGevalideerd(true);
		tijdsregistratieService.create(tijdsregistratie15);
		Tijdsregistratie tijdsregistratie16 = new Tijdsregistratie();
		tijdsregistratie16.setProject(vierdeProject);
		tijdsregistratie16.setGebruiker(gebruikerNormal2);
		tijdsregistratieDatum = parseDate("2017-04-16");
		tijdsregistratie16.setDatum(tijdsregistratieDatum);
		tijdsregistratie16.setOmschrijving("test16");
		tijdsregistratie16.setAantalUren(16);
		tijdsregistratie16.setSubmitted(false);
		tijdsregistratie16.setRejected(false);
		tijdsregistratie16.setGevalideerd(false);
		tijdsregistratieService.create(tijdsregistratie16);

	}
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
}
