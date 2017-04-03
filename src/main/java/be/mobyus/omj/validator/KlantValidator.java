package be.mobyus.omj.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import be.mobyus.omj.model.Klant;

@Component
public class KlantValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Klant.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Klant klant = (Klant)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naam", "klant.naam");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "klant.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telefoonNummer", "klant.telefoonNummer");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "omschrijving", "klant.omschrijving");
	}

}
