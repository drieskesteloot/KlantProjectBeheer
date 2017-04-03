package be.mobyus.omj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String klantprojectIndex(Model model) {
		 return "klantprojectbeheer";
	}
	
	@RequestMapping(value="/beginscherm",method=RequestMethod.GET)
	public String keerTerugNaarIndex(Model model) {
		 return "klantprojectbeheer";
	}
	
	

}
