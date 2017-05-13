package be.mobyus.omj.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import be.mobyus.omj.dao.AdresRepository;
import be.mobyus.omj.dao.KlantRepository;
import be.mobyus.omj.dao.ProjectRepository;
import be.mobyus.omj.model.Klant;

@Controller
public class KlantController {
	
	@Autowired
	KlantRepository klantrepo;
	
	@Autowired
	AdresRepository adresrepo;
	
	@Autowired
	ProjectRepository projectrepo;
	
	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value="/klanten",method=RequestMethod.GET)
	public String klantenList(Model model) {
        model.addAttribute("klanten", klantrepo.findAll());
        return "klanten";
	}
    
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/klant",method=RequestMethod.GET)
	public String klantNew(Model model) {
		model.addAttribute("klant", new Klant());
		return "nieuweklant";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/klant",method=RequestMethod.POST)
	public String klantenAdd(@Valid @ModelAttribute Klant klant, BindingResult result , Model model) {

		if(result.hasErrors()){
			return "nieuweklant";
		}

		klantrepo.save(klant);
        return "redirect:/klanten";
	}
    
	/*
    @RequestMapping(value="/klant",method=RequestMethod.POST)
	public String klantenAdd(@RequestParam String naam, 
						@RequestParam String email, @RequestParam String straat, @RequestParam String huisNummer, 
						@RequestParam String bus, @RequestParam String postcode, @RequestParam String gemeente, 
						@RequestParam String telefoonNummer, @RequestParam String omschrijving, Model model) {
        Klant newKlant = new Klant();
        newKlant.setNaam(naam);
        newKlant.setEmail(email);
        
        Adres klantAdres = new Adres();
        klantAdres.setStraat(straat);
        klantAdres.setHuisNummer(huisNummer);
        klantAdres.setBus(bus);
        klantAdres.setPostcode(postcode);
        klantAdres.setGemeente(gemeente);
        newKlant.setAdres(klantAdres);
        
        newKlant.setTelefoonNummer(telefoonNummer);
        newKlant.setOmschrijving(omschrijving);
        klantrepo.save(newKlant);

        //model.addAttribute("klant", newKlant);
        //model.addAttribute("projecten", projectrepo.findAll());
        return "redirect:/klanten";
        //return "redirect:/klant/" + newKlant.getId();
	}
	*/
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/klant/{id}")
	public String klantDetails(@PathVariable Long id, Model model) {
        model.addAttribute("klant", klantrepo.findOne(id));
        return "klant";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/klant/{id}", method=RequestMethod.POST)
	public String updateKlantDetails(@PathVariable Long id, @Valid @ModelAttribute Klant klant,
			BindingResult result, Model model) {
		
		if(result.hasErrors()){
			model.addAttribute("klant", klant);
			return "klant";
		}
		
        klantrepo.save(klant);
        
        model.addAttribute("$aangepasteKlant", klant);
        return "redirect:/klant/" + klant.getId();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/verwijderKlant/{id}", method=RequestMethod.GET)
	public String klantDelete(@PathVariable Long id, Model model) {
        klantrepo.delete(id);
        return "redirect:/klanten";
	}
	
}
