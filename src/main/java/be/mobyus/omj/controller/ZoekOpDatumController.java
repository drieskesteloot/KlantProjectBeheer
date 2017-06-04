package be.mobyus.omj.controller;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.HuidigeGebruiker;
import be.mobyus.omj.model.HulpZoekOpDatum;
import be.mobyus.omj.model.Rol;
import be.mobyus.omj.model.Tijdsregistratie;
import be.mobyus.omj.service.gebruiker.GebruikerService;
import be.mobyus.omj.service.tijdsregistratie.TijdsregistratieService;
import be.mobyus.omj.validator.HulpZoekOpDatumValidator;

@Controller
public class ZoekOpDatumController {
	
	@Autowired
	TijdsregistratieService tijdsregistratieService;
	
	@Autowired
	GebruikerService gebruikerService;
	
	@Autowired
	ProjectRepository projectrepo;
	
	@Autowired
	HulpZoekOpDatumValidator hulpZoekOpDatumValidator;
	
	private List<Gebruiker> huidigeGebruikers;
	
	@InitBinder("hulpZoekOpDatum")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(hulpZoekOpDatumValidator);
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/tijdsregistratiesOpDatum",method=RequestMethod.GET)
	public String zoekTijdsregistratiesOpDatum(Model model){
		HulpZoekOpDatum hulpZoekOpDatum = new HulpZoekOpDatum();
		HuidigeGebruiker ingelogdeGebruiker = (HuidigeGebruiker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Gebruiker gebruiker = ingelogdeGebruiker.getGebruiker();
	    List<Gebruiker> hulpGebruikers = new ArrayList<>();
	    hulpGebruikers.add(gebruiker);
	    huidigeGebruikers = hulpGebruikers;
	    
	    model.addAttribute("hulpGebruikers", hulpGebruikers);
		model.addAttribute("hulpZoekOpDatum", hulpZoekOpDatum);
		return "zoekTijdsregistratieOpDatum";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/tijdsregistratiesOpDatum/{id}",method=RequestMethod.GET)
	public String mijnTijdsregistratiesOpDatum(@Valid @ModelAttribute HulpZoekOpDatum hulpZoekOpDatum, BindingResult result, @PathVariable Long id, Model model){
		if(result.hasErrors()){
			model.addAttribute("hulpGebruikers", huidigeGebruikers);
			return "zoekTijdsregistratieOpDatum";
		}
		
		Gebruiker gebruiker = gebruikerService.getGebruikerById(id);
		Collection<Tijdsregistratie> tijdsregistraties = tijdsregistratieService.getTijdsregistratieByGebruiker(gebruiker);
		
		if(gebruiker.getRol() == Rol.ADMIN){
			tijdsregistraties = tijdsregistratieService.getAllTijdsregistraties();
		}
		
		List<Tijdsregistratie> gevondenTijdsregistraties = new ArrayList<>();
		
		for(Tijdsregistratie tijdsreg : tijdsregistraties){
			if((tijdsreg.getDatum().getTime() == hulpZoekOpDatum.getStartDatum().getTime() || tijdsreg.getDatum().after(hulpZoekOpDatum.getStartDatum())) && (tijdsreg.getDatum().getTime() == hulpZoekOpDatum.getEindDatum().getTime() || tijdsreg.getDatum().before(hulpZoekOpDatum.getEindDatum()))){
				gevondenTijdsregistraties.add(tijdsreg);
			}
		}
				
		model.addAttribute("tijdsregistraties", gevondenTijdsregistraties);
		return "mijnTijdsregistratiesOpDatum";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/submitTijdsregistraties",method=RequestMethod.GET)
	public String zoekTeBevestigenTijdsregistraties(Model model){
		HulpZoekOpDatum hulpZoekOpDatum = new HulpZoekOpDatum();
		List<Gebruiker> gebruikers = (List<Gebruiker>) gebruikerService.getGebruikersByRol(Rol.NORMAL);
		model.addAttribute("gebruikers", gebruikers);
		model.addAttribute("hulpZoekOpDatum", hulpZoekOpDatum);
		return "submitTijdsregistraties";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/submitTijdsregistraties",method=RequestMethod.POST)
	public String bevestigTijdsregistraties(@Valid @ModelAttribute HulpZoekOpDatum hulpZoekOpDatum, BindingResult result, Model model){
		HuidigeGebruiker ingelogdeGebruiker = (HuidigeGebruiker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Gebruiker gebruiker = ingelogdeGebruiker.getGebruiker();
	    if(gebruiker.getRol().equals(Rol.ADMIN) && hulpZoekOpDatum.getGebruiker() != null){
	    	gebruiker = hulpZoekOpDatum.getGebruiker();
	    }
	    
	    Collection<Tijdsregistratie> tijdsregistraties = tijdsregistratieService.getAllSubmittedTijdsregistratiesByGebruiker(gebruiker, false);
	    Collection<Tijdsregistratie> gevondenTijdsregistraties = new ArrayList<>();
		
		if(result.hasErrors()){
			model.addAttribute("hulpZoekOpDatum", hulpZoekOpDatum);
			model.addAttribute("gebruikers", (List<Gebruiker>) gebruikerService.getGebruikersByRol(Rol.NORMAL));
			return "submitTijdsregistraties";
		}
	
		/*
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date startDate = cal.getTime();
		*/
		//ZonedDateTime d = ZonedDateTime.ofInstant(hulpZoekOpDatum.getStartDatum().toInstant(), ZoneId.systemDefault());
		if(hulpZoekOpDatum.getStartDatum() == null && hulpZoekOpDatum.getEindDatum() == null){
			Date datumVorigeMaand = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
			Date datumVandaag = new Date();
			for(Tijdsregistratie tijdsreg : tijdsregistraties){
				if((tijdsreg.getDatum().equals(datumVorigeMaand) || tijdsreg.getDatum().after(datumVorigeMaand)) && (tijdsreg.getDatum().equals(datumVandaag) || tijdsreg.getDatum().before(datumVandaag))){
					tijdsreg.setSubmitted(true);
					tijdsregistratieService.update(tijdsreg);
					gevondenTijdsregistraties.add(tijdsreg);
				}
			}
		}
		else{
			for(Tijdsregistratie tijdsreg : tijdsregistraties){
				if((hulpZoekOpDatum.getStartDatum().equals(tijdsreg.getDatum()) || tijdsreg.getDatum().after(hulpZoekOpDatum.getStartDatum())) && (hulpZoekOpDatum.getEindDatum().equals(tijdsreg.getDatum()) || tijdsreg.getDatum().before(hulpZoekOpDatum.getEindDatum()))){
					tijdsreg.setSubmitted(true);
					tijdsregistratieService.update(tijdsreg);
					gevondenTijdsregistraties.add(tijdsreg);
				}
			}
		}
		
		tijdsregistraties = tijdsregistratieService.getAllSubmittedTijdsregistratiesByGebruiker(gebruiker, false);
		model.addAttribute("tijdsregistraties", tijdsregistraties);
		return "mijnUnsubmittedTijdsregistraties";
	}
		
	
}
