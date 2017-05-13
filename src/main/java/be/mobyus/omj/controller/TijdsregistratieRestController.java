package be.mobyus.omj.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.HuidigeGebruiker;
import be.mobyus.omj.model.Tijdsregistratie;
import be.mobyus.omj.service.gebruiker.GebruikerService;
import be.mobyus.omj.service.tijdsregistratie.TijdsregistratieService;

@RestController
public class TijdsregistratieRestController {
	
	@Autowired
	TijdsregistratieService tijdsregistratieService;
	
	@Autowired
	GebruikerService gebruikerService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/rejectedTijdsregistraties")
	public @ResponseBody Collection<Tijdsregistratie> rejectedTijdsregistratiesAjaxCall(){
		HuidigeGebruiker ingelogdeGebruiker = (HuidigeGebruiker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Gebruiker gebruiker = ingelogdeGebruiker.getGebruiker();
	    Collection<Tijdsregistratie> mijnRejectedTijdsregistraties = tijdsregistratieService.getAllRejectedTijdsregistratiesByGebruiker(gebruiker, true);
	    /*
	    String message = "Niets gevonden";
	    if(!mijnRejectedTijdsregistraties.isEmpty()){
	    	message = "U heeft afgekeurde tijdsregistraties";
	    }
	    */
	    return mijnRejectedTijdsregistraties; 
	}
	
	//@GetMapping("/updateUnsubmittedTijdregistraties")
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/updateUnsubmittedTijdregistraties", method=RequestMethod.POST)
	public @ResponseBody String updateUnsubmittedTijdregistraties(@RequestBody List<String> ids){
		/*for(Tijdsregistratie tijdsreg : tijdsregistraties){
			tijdsreg.setSubmitted(true);
			tijdsregistratieService.update(tijdsreg);
		}
		*/
		List<String> test = ids;
		return "Tijdsregistraties aangepast";
	}
}
