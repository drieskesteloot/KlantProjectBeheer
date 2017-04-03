package be.mobyus.omj.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import be.mobyus.omj.model.HuidigeGebruiker;

@ControllerAdvice
public class HuidigeGebruikerControllerAdvice {
	
	@ModelAttribute("huidigeGebruiker")
	public HuidigeGebruiker getHuidigeGebruiker(Authentication authentication){
		return (authentication == null) ? null : (HuidigeGebruiker)authentication.getPrincipal();
	}
}
