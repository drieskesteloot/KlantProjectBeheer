package be.mobyus.omj.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import be.mobyus.omj.model.Project;

@Component
public class ProjectValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Project project = (Project)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naam", "project.naam");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typeProject", "project.typeProject");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "klant", "project.klant");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPersoonKlant", "project.contactPersoonKlant");
		
		if(project.getStartDatum() == null){
			errors.rejectValue("startDatum", "project.startDatumNotNull");
		}
		if(project.getEindDatum() == null){
			errors.rejectValue("eindDatum", "project.eindDatumNotNull");
		}
		if(project.getStartDatum() != null && project.getEindDatum() != null && project.getStartDatum().after(project.getEindDatum())){
			errors.rejectValue("startDatum", "project.startDatum");
		}

	}

}
