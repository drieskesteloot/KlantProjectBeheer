package be.mobyus.omj.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Klant;

@Component
public class GebruikerValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Gebruiker.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Gebruiker gebruiker = (Gebruiker)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naam", "gebruiker.naam");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naam", "gebruiker.voornaam");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "gebruiker.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rol", "gebruiker.rol");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "wachtwoord", "gebruiker.wachtwoord");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "wachtwoordHerhaald", "gebruiker.wachtwoord");
		
		if(!gebruiker.getWachtwoord().equals(gebruiker.getWachtwoordHerhaald())){
			errors.rejectValue("wachtwoordHerhaald", "gebruiker.wachtwoordVerschillend");
		}
	}

}
