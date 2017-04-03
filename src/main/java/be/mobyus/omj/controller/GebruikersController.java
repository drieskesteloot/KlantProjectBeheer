package be.mobyus.omj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import be.mobyus.omj.service.gebruiker.GebruikerService;

@Controller
public class GebruikersController {
	
	@Autowired
	GebruikerService gebruikerService;
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/gebruikers",method=RequestMethod.GET)
	public String projectenList(Model model) {
        model.addAttribute("gebruikers", gebruikerService.getAllGebruikers());
        return "gebruikers";
	}
	
	/*
	@RequestMapping(value = "/gebruikers", method=RequestMethod.GET)
	public ModelAndView getGebruikersPage(){
		return new ModelAndView("gebruikers", "gebruikers", gebruikerService.getAllGebruikers());
	}
	*/
	
}
