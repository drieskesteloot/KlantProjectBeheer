package be.mobyus.omj.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
import be.mobyus.omj.model.Factuur;
import be.mobyus.omj.model.FactuurDetails;
import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.HuidigeGebruiker;
import be.mobyus.omj.model.HulpZoekOpDatum;
import be.mobyus.omj.model.Klant;
import be.mobyus.omj.model.Project;
import be.mobyus.omj.model.Tijdsregistratie;
import be.mobyus.omj.service.factuur.FactuurService;
import be.mobyus.omj.service.factuurDetails.FactuurDetailsService;
import be.mobyus.omj.service.tijdsregistratie.TijdsregistratieService;
import be.mobyus.omj.validator.FactuurValidator;
import be.mobyus.omj.validator.HulpZoekOpDatumValidator;

@Controller
public class FactuurController {
	
	@Autowired
	FactuurService factuurService;
	
	@Autowired
	FactuurDetailsService factuurDetailsService;
	
	@Autowired
	FactuurValidator factuurValidator;
	
	@Autowired
	HulpZoekOpDatumValidator hulpZoekOpDatumValidator;
	
	@Autowired
	KlantRepository klantrepo;
	
	@Autowired
	ProjectRepository projectrepo;
	
	@Autowired
	TijdsregistratieService tijdsregistratieService;
	
	private List<Project> huidigeProjecten;
	private List<Klant> huidigeKlanten;
	private List<FactuurDetails> huidigeFactuurDetails;
	private List<Gebruiker> huidigeGebruikers;
	
	@InitBinder("factuur")
	public void initFactuurBinder(WebDataBinder binder) {
		binder.addValidators(factuurValidator);
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@InitBinder("hulpZoekOpDatum")
	public void initHulpZoekOpDatumBinder(WebDataBinder binder) {
		binder.addValidators(hulpZoekOpDatumValidator);
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/factuurVoorProject/{id}", method=RequestMethod.GET)
	public String keuzeFactuurEenProject(@PathVariable Long id, Model model){
		Project project = projectrepo.findOne(id);
		List<Project> hulpProjecten = new ArrayList<>();
		hulpProjecten.add(project);
		huidigeProjecten = hulpProjecten;
		Klant klant = klantrepo.findOne(project.getKlant().getId());
		List<Klant> hulpKlanten = new ArrayList<>();
		hulpKlanten.add(klant);
		huidigeKlanten = hulpKlanten;
		Factuur factuur = new Factuur();
		factuur.setProjecten(huidigeProjecten);
		factuur.setKlant(klant);
		factuur.setBTW(21.0);
		
		List<FactuurDetails> factuurDetails = new ArrayList<>();
		
		if(project.getTypeProject().getNaam().equals("Fixed price")){
			huidigeFactuurDetails = null;
			FactuurDetails factuurDetail = new FactuurDetails();
			factuurDetail.setOmschrijving(project.getTypeProject().getNaam());
			factuurDetail.setAantal(1);
			factuurDetail.setPrijs(project.getPrijs());
			factuurDetail.setTotaal(factuurDetail.getAantal() * factuurDetail.getPrijs());
			factuurDetails.add(factuurDetail);
			huidigeFactuurDetails = factuurDetails;
			factuur.setSubTotaal(factuurDetail.getTotaal());	
		}
		else{
			Collection<Tijdsregistratie> tijdsregistraties = tijdsregistratieService.getTijdsregistratieByProject(project);
			float subTotaal = 0.0f;
			huidigeFactuurDetails = null;
			for(Tijdsregistratie tijdsreg : tijdsregistraties){
				if(tijdsreg.getGevalideerd()){
					FactuurDetails factuurDetail = new FactuurDetails();
					factuurDetail.setOmschrijving(tijdsreg.getOmschrijving());
					factuurDetail.setAantal(tijdsreg.getAantalUren());
					factuurDetail.setPrijs(85.0f);
					factuurDetail.setTotaal(factuurDetail.getAantal() * factuurDetail.getPrijs());
					factuurDetails.add(factuurDetail);
					subTotaal += factuurDetail.getTotaal();
				}
			}
			factuur.setSubTotaal(subTotaal);
			huidigeFactuurDetails = factuurDetails;
		}
		factuur.setTotaal((factuur.getSubTotaal() * 1.21f));
		
		model.addAttribute("hulpProjecten", hulpProjecten);
		model.addAttribute("hulpKlanten", hulpKlanten);
		model.addAttribute("factuur", factuur);
		return "factuurVoorEenProject";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/factuurProject", method=RequestMethod.POST)
	public String maakFactuurProjectAan(@Valid @ModelAttribute Factuur factuur, BindingResult result, Model model){
		if(result.hasErrors()){
			model.addAttribute("hulpProjecten", huidigeProjecten);
			model.addAttribute("hulpKlanten", huidigeKlanten);
			return "factuurVoorEenProject";
		}
		factuur.setDetails(huidigeFactuurDetails);
		factuurService.create(factuur);
		for(FactuurDetails fd : huidigeFactuurDetails){
			fd.setFactuur(factuur);
			factuurDetailsService.create(fd);
		}
		return "redirect:/facturen";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value="/facturen",method=RequestMethod.GET)
	public String facturenList(Model model) {
        model.addAttribute("facturen", factuurService.getAllFacturen());
        return "facturen";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value="/factuurDetails",method=RequestMethod.GET)
	public String facturenDetailsList(Model model) {
        model.addAttribute("factuurDetails", factuurDetailsService.getAllFactuurDetails());
        return "factuurDetails";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/keuzeFactuur/{id}", method=RequestMethod.GET)
	public String keuzeFactuurAlleProjecten(@PathVariable Long id, Model model){
		Klant klant = klantrepo.findOne(id);
		List<Klant> hulpKlanten = new ArrayList<>();
		hulpKlanten.add(klant);
		huidigeKlanten = null;
		huidigeKlanten = hulpKlanten;
		HulpZoekOpDatum hulpZoekOpDatum = new HulpZoekOpDatum();
		
		huidigeGebruikers = null;
		HuidigeGebruiker ingelogdeGebruiker = (HuidigeGebruiker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Gebruiker gebruiker = ingelogdeGebruiker.getGebruiker();
	    List<Gebruiker> hulpGebruikers = new ArrayList<>();
	    hulpGebruikers.add(gebruiker);
	    huidigeGebruikers = hulpGebruikers;
		
	    model.addAttribute("hulpGebruikers", hulpGebruikers);
		model.addAttribute("hulpKlanten", hulpKlanten);
		model.addAttribute("hulpZoekOpDatum", hulpZoekOpDatum);
		return "keuzeNieuweFactuur";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/keuzeFactuur", method=RequestMethod.POST)
	public String submitFactuurAlleProjecten(@Valid @ModelAttribute HulpZoekOpDatum hulpZoekOpDatum, BindingResult result, Model model){
		if(result.hasErrors()){
			model.addAttribute("hulpGebruikers", huidigeGebruikers);
			model.addAttribute("hulpZoekOpDatum", hulpZoekOpDatum);
			model.addAttribute("hulpKlanten", huidigeKlanten);
			return "keuzeNieuweFactuur";
		}
		
		List<Project> klantProjecten = (List<Project>) projectrepo.findByKlant(hulpZoekOpDatum.getKlant());
		
		for(Project pr : klantProjecten){
			
		}
		
		return "";
	}
	
}
