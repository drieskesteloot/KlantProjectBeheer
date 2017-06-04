package be.mobyus.omj.validator;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Tijdsregistratie;

@Component
public class TijdsregistratieValidator implements Validator {
	
	@Autowired
	ProjectRepository projectrepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Tijdsregistratie.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Tijdsregistratie tijdsregistratie = (Tijdsregistratie)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "omschrijving", "tijdsregistratie.omschrijving");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aantalUren", "tijdsregistratie.aantalUrenNotNull");
		
		
		List<Project> projecten = (List<Project>) projectrepo.findByGebruikers(tijdsregistratie.getGebruiker());
		if(tijdsregistratie.getProject() != null && !projecten.contains(tijdsregistratie.getProject())){
			errors.rejectValue("project", "tijdsregistratie.gebruikerProjectVerkeerd");
		}
		if(tijdsregistratie.getProject() == null){
			errors.rejectValue("project", "tijdsregistratie.projectNotNull");
		}
		if(tijdsregistratie.getGebruiker() == null){
			errors.rejectValue("gebruiker", "tijdsregistratie.gebruikerNotNull");
		}
		/*
		if(tijdsregistratie.getAantalUren() == null){
			errors.rejectValue("aantalUren", "tijdsregistratie.aantalUrenNotNull");
		}
		*/
		if(tijdsregistratie.getAantalUren() != null && tijdsregistratie.getAantalUren() < 0){
			errors.rejectValue("aantalUren", "tijdsregistratie.aantalUrenNegatief");
		}
		
		if(tijdsregistratie.getDatum() == null){
			errors.rejectValue("datum", "tijdsregistratie.datumNotNull");
		}
		if(tijdsregistratie.getDatum() != null && tijdsregistratie.getDatum().after(new Date())){
			errors.rejectValue("datum", "tijdsregistratie.datumInToekomst");
		}
		if(tijdsregistratie.getGevalideerd() == true && tijdsregistratie.getRejected() == true){
			errors.rejectValue("gevalideerd", "tijdsregistratie.gevalideerd");
		}
		if((tijdsregistratie.getGevalideerd() == true || tijdsregistratie.getRejected() == true) && tijdsregistratie.getSubmitted() == false){
			errors.rejectValue("submitted", "tijdsregistratie.nietSubmitted");
		}
	}
	
}
