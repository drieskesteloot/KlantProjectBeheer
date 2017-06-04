package be.mobyus.omj.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import be.mobyus.omj.dao.KlantRepository;
import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.dao.StatusRepository;
import be.mobyus.omj.dao.TypeProjectRepository;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Rol;
import be.mobyus.omj.model.Status;
import be.mobyus.omj.service.gebruiker.GebruikerService;
import be.mobyus.omj.validator.ProjectValidator;

@Controller
public class ProjectController {
	
	@Autowired
	ProjectRepository projectrepo;
	
	@Autowired
	StatusRepository statusrepo;
	
	@Autowired
	TypeProjectRepository typeprojectrepo;
	
	@Autowired
	KlantRepository klantrepo;
	
	@Autowired
	GebruikerService gebruikerService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@InitBinder("project")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(projectValidator);
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value="/projecten",method=RequestMethod.GET)
	public String projectenList(Model model) {
        model.addAttribute("projecten", projectrepo.findAll());
        return "projecten";
	}
   
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/project",method=RequestMethod.GET)
	public String projectNew(Model model) {
		model.addAttribute("typeprojecten", typeprojectrepo.findAll());
		model.addAttribute("klanten", klantrepo.findAll());
		model.addAttribute("project", new Project());
		return "nieuwProject";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/project",method=RequestMethod.POST)
	public String projectenAdd(@Valid @ModelAttribute Project project, BindingResult result, Model model) {
		
		if(result.hasErrors()){
			model.addAttribute("typeprojecten", typeprojectrepo.findAll());
			model.addAttribute("klanten", klantrepo.findAll());
			return "nieuwProject";
		}
		
        Status status = new Status();
        status = statusrepo.findOne(1L);
        project.setStatus(status);
		
		projectrepo.save(project);
        return "redirect:/projecten";
	}
    
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/project/{id}")
	public String projectDetails(@PathVariable Long id, Model model) {
		Project project = projectrepo.findOne(id);
		model.addAttribute("project", project);
        model.addAttribute("statussen", statusrepo.findAll());
        model.addAttribute("typeprojecten", typeprojectrepo.findAll());
        model.addAttribute("klanten", klantrepo.findAll());
        model.addAttribute("gebruikersNormal", gebruikerService.getGebruikersByRol(Rol.NORMAL));
        return "project";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/projectUpdate/{id}", params="bewaarProject" , method=RequestMethod.POST)
	public String updateProjectDetails(@PathVariable Long id, @Valid @ModelAttribute Project project, 
			BindingResult result, Model model) {
		
		if(result.hasErrors()){
			model.addAttribute("project", project);
			model.addAttribute("statussen", statusrepo.findAll());
			model.addAttribute("typeprojecten", typeprojectrepo.findAll());
			model.addAttribute("klanten", klantrepo.findAll());
			model.addAttribute("gebruikersNormal", gebruikerService.getGebruikersByRol(Rol.NORMAL));
			
			return "project";
		}

        projectrepo.save(project);
		
        model.addAttribute("$aangepastProject", project);
        return "redirect:/project/" + project.getId();
        
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/verwijderProject/{id}", method=RequestMethod.GET)
	public String projectDelete(@PathVariable Long id, Model model) {
        projectrepo.delete(id);
        return "redirect:/projecten";
	}

}
