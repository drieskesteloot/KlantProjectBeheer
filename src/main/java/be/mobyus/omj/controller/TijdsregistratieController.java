package be.mobyus.omj.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.HuidigeGebruiker;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Rol;
import be.mobyus.omj.model.Tijdsregistratie;
import be.mobyus.omj.service.gebruiker.GebruikerService;
import be.mobyus.omj.service.tijdsregistratie.TijdsregistratieService;
import be.mobyus.omj.validator.TijdsregistratieValidator;

@Controller
public class TijdsregistratieController {
	
	@Autowired
	TijdsregistratieService tijdsregistratieService;
	
	@Autowired
	TijdsregistratieValidator tijdsregistratieValidator;
	
	@Autowired
	GebruikerService gebruikerService;
	
	@Autowired
	ProjectRepository projectrepo;

	@InitBinder("tijdsregistratie")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(tijdsregistratieValidator);
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	private Gebruiker huidigeGebruiker;
	private List<Gebruiker> huidigeGebruikers;
	private Project huidigProject;
	private Collection<Project> huidigeProjecten;

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/projectTijdsregistratie/{projectId}/{gebruikerId}",method=RequestMethod.GET)
	public String tijdsregistratieNew(@PathVariable(value="projectId") Long projectId, @PathVariable(value="gebruikerId") Long gebruikerId, Model model){
		Project project = projectrepo.findOne(projectId);
		huidigProject = project;
		
		Iterable<Project> projecten = projectrepo.findAll();
		List<Project> hulpProjecten = new ArrayList<>();
		for(Project prj : projecten){
			if(prj.getId() == projectId){
				hulpProjecten.add(prj);
			}
		}
		
		Gebruiker gebruiker = gebruikerService.getGebruikerById(gebruikerId);
		huidigeGebruiker = gebruiker;
		
		Iterable<Gebruiker> gebruikers = gebruikerService.getAllGebruikers();
		List<Gebruiker> hulpGebruikers = new ArrayList<>();
		for(Gebruiker gebr : gebruikers){
			if(gebr.getId() == gebruikerId){
				hulpGebruikers.add(gebr);
			}
		}
		
		/*
		if(gebruiker.getRol() == Rol.ADMIN){
			hulpProjecten = (List<Project>) projectrepo.findAll();
		}
		*/
		
		if(gebruiker.getRol() == Rol.ADMIN){
			/*
			hulpGebruikers = (List<Gebruiker>) gebruikerService.getGebruikersByRol(Rol.NORMAL);
			*/
			hulpGebruikers = project.getGebruikers();
		}
		
		huidigeProjecten = hulpProjecten;
		huidigeGebruikers = hulpGebruikers;

		model.addAttribute("hulpGebruikers", hulpGebruikers);
		model.addAttribute("hulpProjecten", hulpProjecten);
		//model.addAttribute("hulpProject", project);
		//model.addAttribute("hulpGebruiker", gebruiker);
		
		Tijdsregistratie tijdsregistratie = new Tijdsregistratie();
		tijdsregistratie.setGebruiker(gebruiker);
		tijdsregistratie.setProject(project);
		model.addAttribute("tijdsregistratie", tijdsregistratie);
		
		return "nieuweTijdsregistratie";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/tijdsregistratie",method=RequestMethod.POST)
	public String tijdsregistratieAdd(@Valid @ModelAttribute Tijdsregistratie tijdsregistratie, BindingResult result, Model model) {
		if(result.hasErrors()){
			//model.addAttribute("hulpProject", huidigProject);
			model.addAttribute("hulpProjecten", huidigeProjecten);
			//model.addAttribute("hulpGebruiker", huidigeGebruiker);
			model.addAttribute("hulpGebruikers", huidigeGebruikers);
			return "nieuweTijdsregistratie";
		}
		tijdsregistratieService.create(tijdsregistratie);
		
		return "redirect:/mijnProjecten/" + huidigeGebruiker.getId();
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/nieuweTijdsregistratie/{id}",method=RequestMethod.GET)
	public String tijdsregistratieNewAlt(@PathVariable Long id, Model model){
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		Collection<Project> projecten = projectrepo.findByGebruikers(gebruiker);
		
		huidigeGebruiker = gebruiker;
		
		Iterable<Gebruiker> gebruikers = gebruikerService.getAllGebruikers();
		List<Gebruiker> hulpGebruikers = new ArrayList<>();
		for(Gebruiker gebr : gebruikers){
			if(gebr.getId() == id){
				hulpGebruikers.add(gebr);
			}
		}
		
		if(gebruiker.getRol() == Rol.ADMIN){
			projecten = (List<Project>) projectrepo.findAll();
		}
		
		if(gebruiker.getRol() == Rol.ADMIN){
			hulpGebruikers = (List<Gebruiker>) gebruikerService.getGebruikersByRol(Rol.NORMAL);
		}
		huidigeProjecten = projecten;
		huidigeGebruikers = hulpGebruikers;
		
		model.addAttribute("tijdsregistratie", new Tijdsregistratie());
		model.addAttribute("hulpProjecten", projecten);
		model.addAttribute("hulpGebruikers", hulpGebruikers);
		return "nieuweTijdsregistratieAlt";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/tijdsregistratieAlt",method=RequestMethod.POST)
	public String tijdsregistratieAddAlt(@Valid @ModelAttribute Tijdsregistratie tijdsregistratie, BindingResult result, Model model) {
		if(result.hasErrors()){
			model.addAttribute("hulpProjecten", huidigeProjecten);
			model.addAttribute("hulpGebruikers", huidigeGebruikers);
			return "nieuweTijdsregistratieAlt";
		}
		tijdsregistratieService.create(tijdsregistratie);

		return "redirect:/mijnTijdsregistraties/" + huidigeGebruiker.getId();
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mijnTijdsregistraties/{id}",method=RequestMethod.GET)
	public String mijnTijdsregistratiesList(@PathVariable Long id, Model model) {
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		Collection<Tijdsregistratie> mijnTijdsregistraties = tijdsregistratieService.getTijdsregistratieByGebruiker(gebruiker);
		
		if(gebruiker.getRol() == Rol.ADMIN){
			mijnTijdsregistraties = tijdsregistratieService.getAllTijdsregistraties();
		}
		
		model.addAttribute("tijdsregistraties", mijnTijdsregistraties);
		return "mijnTijdsregistraties";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="tijdsregistratie/{id}",method=RequestMethod.GET)
	public String tijdsregistratieDetails(@PathVariable Long id, Model model){
		Tijdsregistratie tijdsregistratie = tijdsregistratieService.getTijdsregistratieById(id);
		
		Project project = projectrepo.findOne(tijdsregistratie.getProject().getId());
		huidigProject = project;
		
		Iterable<Project> projecten = projectrepo.findAll();
		List<Project> hulpProjecten = new ArrayList<>();
		for(Project prj : projecten){
			if(prj.getId() == tijdsregistratie.getProject().getId()){
				hulpProjecten.add(prj);
			}
		}
		huidigeProjecten = hulpProjecten;
		
		Gebruiker gebruiker = gebruikerService.getGebruikerById(tijdsregistratie.getGebruiker().getId());
		huidigeGebruiker = gebruiker;
		
		Iterable<Gebruiker> gebruikers = gebruikerService.getAllGebruikers();
		List<Gebruiker> hulpGebruikers = new ArrayList<>();
		for(Gebruiker gebr : gebruikers){
			if(gebr.getId() == tijdsregistratie.getGebruiker().getId()){
				hulpGebruikers.add(gebr);
			}
		}
		huidigeGebruikers = hulpGebruikers;
		
		model.addAttribute("hulpGebruikers", hulpGebruikers);
		model.addAttribute("hulpProjecten", hulpProjecten);
		model.addAttribute("tijdsregistratie", tijdsregistratie);
		return "tijdsregistratie";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mijnProjectTijdsregistraties/{projectId}/{gebruikerId}",method=RequestMethod.GET)
	public String mijnProjectTijdsregistraties(@PathVariable(value="projectId") Long projectId, @PathVariable(value="gebruikerId") Long gebruikerId, Model model){
		Project project = projectrepo.findOne(projectId);
		Gebruiker gebruiker = gebruikerService.getGebruikerById(gebruikerId);
		Collection<Tijdsregistratie> tijdsregistraties = tijdsregistratieService.getTijdsRegistratieByProjectAndGebruiker(project, gebruiker);
		
		if(gebruiker.getRol() == Rol.ADMIN){
			tijdsregistraties = tijdsregistratieService.getTijdsregistratieByProject(project);
		}
	
		model.addAttribute("tijdsregistraties", tijdsregistraties);
		return "mijnTijdsregistratiesPerProject";
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="tijdsregistratieUpdate/{id}",method=RequestMethod.POST)
	public String tijdsregistratieEdit(@PathVariable Long id, @Valid @ModelAttribute Tijdsregistratie tijdsregistratie, 
			BindingResult result, Model model){
		
		if(result.hasErrors()){
			model.addAttribute("hulpGebruikers", huidigeGebruikers);
			model.addAttribute("hulpProjecten", huidigeProjecten);
			return "tijdsregistratie";
		}
		
		tijdsregistratieService.update(tijdsregistratie);
		
        model.addAttribute("$aangepasteTijdsregistratie", tijdsregistratie);
        return "redirect:/tijdsregistratie/" + tijdsregistratie.getId();
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/submittedTijdsregistraties/{id}",method=RequestMethod.GET)
	public String submittedTijdsregistraties(@PathVariable Long id, Model model){
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		//Collection<Tijdsregistratie> mijnTijdsregistraties = tijdsregistratieService.getTijdsregistratieByGebruiker(gebruiker);
		Collection<Tijdsregistratie> mijnSubmittedTijdsregistraties = tijdsregistratieService.getAllSubmittedTijdsregistratiesByGebruiker(gebruiker, true);
		
		if(gebruiker.getRol() == Rol.ADMIN){
			mijnSubmittedTijdsregistraties = tijdsregistratieService.getAllSubmittedTijdsregistraties(true);
		}
		
		model.addAttribute("tijdsregistraties", mijnSubmittedTijdsregistraties);
		
		return "mijnSubmittedTijdsregistraties";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/unSubmittedTijdsregistraties/{id}",method=RequestMethod.GET)
	public String unSubmittedTijdsregistraties(@PathVariable Long id, Model model){
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		//Collection<Tijdsregistratie> mijnTijdsregistraties = tijdsregistratieService.getTijdsregistratieByGebruiker(gebruiker);
		Collection<Tijdsregistratie> mijnUnSubmittedTijdsregistraties = tijdsregistratieService.getAllSubmittedTijdsregistratiesByGebruiker(gebruiker, false);
		
		if(gebruiker.getRol() == Rol.ADMIN){
			mijnUnSubmittedTijdsregistraties = tijdsregistratieService.getAllSubmittedTijdsregistraties(false);
		}
		
		model.addAttribute("tijdsregistraties", mijnUnSubmittedTijdsregistraties);
		
		return "mijnUnSubmittedTijdsregistraties";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/rejectedTijdsregistraties/{id}",method=RequestMethod.GET)
	public String rejectedTijdsregistraties(@PathVariable Long id, Model model){
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		//Collection<Tijdsregistratie> mijnTijdsregistraties = tijdsregistratieService.getTijdsregistratieByGebruiker(gebruiker);
		Collection<Tijdsregistratie> mijnRejectedTijdsregistraties = tijdsregistratieService.getAllRejectedTijdsregistratiesByGebruiker(gebruiker, true);
		
		if(gebruiker.getRol() == Rol.ADMIN){
			mijnRejectedTijdsregistraties = tijdsregistratieService.getAllRejectedTijdsregistraties(true);
		}
		
		model.addAttribute("tijdsregistraties", mijnRejectedTijdsregistraties);
		
		return "mijnRejectedTijdsregistraties";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/gevalideerdeTijdsregistraties/{id}",method=RequestMethod.GET)
	public String gevalideerdeTijdsregistraties(@PathVariable Long id, Model model){
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		//Collection<Tijdsregistratie> mijnTijdsregistraties = tijdsregistratieService.getTijdsregistratieByGebruiker(gebruiker);
		Collection<Tijdsregistratie> mijnGevalideerdeTijdsregistraties = tijdsregistratieService.getAllGevalideerdeTijdsregistratiesByGebruiker(gebruiker, true);
		
		if(gebruiker.getRol() == Rol.ADMIN){
			mijnGevalideerdeTijdsregistraties = tijdsregistratieService.getAllGevalideerdeTijdsregistraties(true);
		}
		
		model.addAttribute("tijdsregistraties", mijnGevalideerdeTijdsregistraties);
		
		return "mijnGevalideerdeTijdsregistraties";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/rejectedTijdsregistraties")
	public @ResponseBody Collection<Tijdsregistratie> rejectedTijdsregistratiesAjaxCall(){
		HuidigeGebruiker ingelogdeGebruiker = (HuidigeGebruiker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Gebruiker gebruiker = ingelogdeGebruiker.getGebruiker();
	    Collection<Tijdsregistratie> mijnRejectedTijdsregistraties = tijdsregistratieService.getAllRejectedTijdsregistratiesByGebruiker(gebruiker, true);

	    return mijnRejectedTijdsregistraties; 
	}

}
