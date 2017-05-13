package be.mobyus.omj.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.service.gebruiker.GebruikerService;
import be.mobyus.omj.validator.GebruikerValidator;

@Controller
public class GebruikerController {
	
	@Autowired
	GebruikerService gebruikerService;
	
	@Autowired
	GebruikerValidator gebruikerValidator;
	
	@Autowired
	ProjectRepository projectrepo;
	
	@InitBinder("gebruiker")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(gebruikerValidator);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/gebruiker",method=RequestMethod.GET)
	public String gebruikerNew(Model model) {
		model.addAttribute("gebruiker", new Gebruiker());
		return "nieuweGebruiker";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/gebruiker",method=RequestMethod.POST)
	public String gebruikersAdd(@Valid @ModelAttribute Gebruiker gebruiker, BindingResult result, Model model) {
		
		if(result.hasErrors()){
			return "nieuweGebruiker";
		}
		
        gebruikerService.create(gebruiker);
        return "redirect:/gebruikers";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/gebruiker/{id}")
	public String gebruikerDetails(@PathVariable Long id, Model model) {
		//gebruikerService.getGebruikerById(id);
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		model.addAttribute("gebruiker", gebruiker);
		model.addAttribute("projecten", projectrepo.findAll());
        return "gebruiker";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/gebruiker/{id}", method=RequestMethod.POST)
	public String updateGebruikerDetails(@PathVariable Long id, @Valid @ModelAttribute Gebruiker gebruiker,
			BindingResult result, Model model) {
		
		if(result.hasErrors()){
			model.addAttribute("gebruiker", gebruiker);
			return "gebruiker";
		}

		gebruikerService.update(gebruiker);
		
        model.addAttribute("$aangepasteGebruiker", gebruiker);
        return "redirect:/gebruiker/" + gebruiker.getId();
        
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/verwijderGebruiker/{id}", method=RequestMethod.GET)
	public String projectDelete(@PathVariable Long id, Model model) {
        gebruikerService.delete(id);
        return "redirect:/gebruikers";
	}

}
