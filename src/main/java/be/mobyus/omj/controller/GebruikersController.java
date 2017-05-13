package be.mobyus.omj.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import be.mobyus.omj.dao.KlantRepository;
import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.Klant;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Rol;
import be.mobyus.omj.service.gebruiker.GebruikerService;

@Controller
public class GebruikersController {
	
	@Autowired
	GebruikerService gebruikerService;
	
	@Autowired
	ProjectRepository projectrepo;
	
	@Autowired
	KlantRepository klantrepo;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/gebruikers",method=RequestMethod.GET)
	public String gebruikersList(Model model) {
		model.addAttribute("projecten", projectrepo.findAll());
        model.addAttribute("gebruikers", gebruikerService.getAllGebruikers());
        return "gebruikers";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mijnProjecten/{id}",method=RequestMethod.GET)
	public String mijnProjectenList(@PathVariable Long id, Model model) {
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		Collection<Project> mijnProjecten = gebruikerService.getProjectsByGebruiker(gebruiker);
		
		if(gebruiker.getRol() == Rol.ADMIN){
			mijnProjecten = (Collection<Project>) projectrepo.findAll();
		}
		
    	model.addAttribute("projecten", mijnProjecten);
        return "mijnProjecten";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mijnKlanten/{id}",method=RequestMethod.GET)
	public String mijnKlantenList(@PathVariable Long id, Model model) {
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		Collection<Project> mijnProjecten = gebruikerService.getProjectsByGebruiker(gebruiker);
		List<Klant> klanten = (List<Klant>) klantrepo.findAll();
		List<Klant> mijnKlanten = new ArrayList<>();
		
		for(Project project : mijnProjecten){
			Klant klant = klantrepo.findOne(project.getKlant().getId());
			if(!mijnKlanten.contains(klant)){
				mijnKlanten.add(klant);
			}
		}
    	
    	if(gebruiker.getRol() == Rol.ADMIN){
    		mijnKlanten = (List<Klant>) klantrepo.findAll();
    	}
    	
		model.addAttribute("klanten", mijnKlanten);
        return "mijnKlanten";
	}
	
	/*
	@RequestMapping(value = "/gebruikers", method=RequestMethod.GET)
	public ModelAndView getGebruikersPage(){
		return new ModelAndView("gebruikers", "gebruikers", gebruikerService.getAllGebruikers());
	}
	*/
	
}
