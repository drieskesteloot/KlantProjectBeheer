package be.mobyus.omj.validator;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import be.mobyus.omj.model.Factuur;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Tijdsregistratie;
import be.mobyus.omj.service.tijdsregistratie.TijdsregistratieService;


@Component
public class FactuurValidator implements Validator {
	
	@Autowired
	TijdsregistratieService tijdsregistratieService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Factuur.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Factuur factuur = (Factuur)target;
		/*
		List<Project> projecten = factuur.getProjecten();
		Collection<Tijdsregistratie> tijdsregistraties = null;
		Collection<Tijdsregistratie> gevalideerdeTijdsregistraties = null;
		
		for(Project pr : projecten){
			tijdsregistraties = tijdsregistratieService.getTijdsregistratieByProject(pr);
			for(Tijdsregistratie tijdsreg : tijdsregistraties){
				if(tijdsreg.getGevalideerd()){
					gevalideerdeTijdsregistraties.add(tijdsreg);
				}
			}
			if
		}
		*/
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "omschrijving", "factuur.omschrijving");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subTotaal", "factuur.subTotaal");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "totaal", "factuur.totaal");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "BTW", "factuur.btw");
		
		if(factuur.getSubTotaal() == 0.0f){
			errors.rejectValue("subTotaal", "factuur.subTotaalNotNull");
		}
		if(factuur.getKlant() == null){
			errors.rejectValue("klant", "factuur.klant");
		}
		if(factuur.getDatum() == null){
			errors.rejectValue("datum", "factuur.datumNotNull");
		}
		if(factuur.getBetaalDatum() == null){
			errors.rejectValue("betaalDatum", "factuur.betaalDatumNotNull");
		}
		if(factuur.getDatum() != null && factuur.getBetaalDatum() != null && factuur.getDatum().after(factuur.getBetaalDatum())){
			errors.rejectValue("betaalDatum", "factuur.verstuurVerleden");
		}
		
	}
	
}
