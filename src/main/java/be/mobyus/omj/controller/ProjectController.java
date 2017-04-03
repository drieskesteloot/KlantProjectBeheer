package be.mobyus.omj.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import be.mobyus.omj.model.Status;
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
	ProjectValidator projectValidator;
	
	@InitBinder("project")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(projectValidator);
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
    @RequestMapping(value="/projecten",method=RequestMethod.GET)
	public String projectenList(Model model) {
        model.addAttribute("projecten", projectrepo.findAll());
        return "projecten";
	}
    
	@RequestMapping(value="/project",method=RequestMethod.GET)
	public String projectNew(Model model) {
		model.addAttribute("typeprojecten", typeprojectrepo.findAll());
		model.addAttribute("klanten", klantrepo.findAll());
		model.addAttribute("project", new Project());
		return "nieuwProject";
	}
	
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
    
	/*
    @RequestMapping(value="/project",method=RequestMethod.POST)
	public String projectenAdd(@RequestParam String naam, 
						@RequestParam Long typeProjectId, @RequestParam float prijs, @RequestParam Date startDatum, 
						@RequestParam Date eindDatum, @RequestParam Long klantId, @RequestParam String contactPersoon, 
						Model model) {
        Project newProject = new Project();
        newProject.setNaam(naam);
        
        TypeProject typeProject = typeprojectrepo.findOne(typeProjectId);
        newProject.setTypeProject(typeProject);
        
        newProject.setPrijs(prijs);
        newProject.setStartDatum(startDatum);
        newProject.setEindDatum(eindDatum);
        
        Klant klant = klantrepo.findOne(klantId);
        newProject.setKlant(klant);
        
        newProject.setContactPersoonKlant(contactPersoon);
        
        Status status = new Status();
        status = statusrepo.findOne(1L);
        newProject.setStatus(status);
        
        projectrepo.save(newProject);

        model.addAttribute("project", newProject);
        //model.addAttribute("projecten", projectrepo.findAll());
        return "redirect:/projecten";
        //return "redirect:/project/" + newProject.getId();
	}
    */
    
	@RequestMapping("/project/{id}")
	public String projectDetails(@PathVariable Long id, Model model) {
		Project project = projectrepo.findOne(id);
		model.addAttribute("project", project);
        model.addAttribute("statussen", statusrepo.findAll());
        model.addAttribute("typeprojecten", typeprojectrepo.findAll());
        model.addAttribute("klanten", klantrepo.findAll());
        return "project";
	}
	
	@RequestMapping(value = "/project/{id}", method=RequestMethod.POST)
	public String updateProjectDetails(@PathVariable Long id, @Valid @ModelAttribute Project project,
			BindingResult result, Model model) {
		
		if(result.hasErrors()){
			model.addAttribute("project", project);
			model.addAttribute("statussen", statusrepo.findAll());
			model.addAttribute("typeprojecten", typeprojectrepo.findAll());
			model.addAttribute("klanten", klantrepo.findAll());
			
			return "project";
		}

        projectrepo.save(project);
		
        model.addAttribute("$aangepastProject", project);
        return "redirect:/project/" + project.getId();
        
	}
    
	@RequestMapping(value="/verwijderProject/{id}", method=RequestMethod.GET)
	public String projectDelete(@PathVariable Long id, Model model) {
        projectrepo.delete(id);
        return "redirect:/projecten";
	}

}
