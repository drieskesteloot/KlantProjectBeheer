package be.mobyus.omj.validator;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.HuidigeGebruiker;
import be.mobyus.omj.model.HulpZoekOpDatum;
import be.mobyus.omj.model.Rol;

@Component
public class HulpZoekOpDatumValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return HulpZoekOpDatum.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		HulpZoekOpDatum object = (HulpZoekOpDatum) target;
		HuidigeGebruiker ingelogdeGebruiker = (HuidigeGebruiker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Gebruiker gebruiker = ingelogdeGebruiker.getGebruiker();
	    if(gebruiker.getRol().equals(Rol.ADMIN) && object.getGebruiker() == null){
	    	errors.rejectValue("gebruiker", "project.gebruikerNotNull");
	    }
		
	    if(object.getStartDatum() != null && object.getEindDatum() == null){
	    	errors.rejectValue("eindDatum", "tijdsregistratie.startDatumCombEindDatum");
	    }
	    if(object.getStartDatum() == null && object.getEindDatum() != null){
	    	errors.rejectValue("startDatum", "tijdsregistratie.eindDatumCombStartDatum");
	    }
		if(object.getStartDatum() != null && object.getEindDatum() != null && object.getStartDatum().after(object.getEindDatum())){
			errors.rejectValue("startDatum", "project.startDatum");
		}
		
	}

}
