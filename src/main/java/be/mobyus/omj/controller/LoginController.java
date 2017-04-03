package be.mobyus.omj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	/*
	@RequestMapping("/login")
	public String login() {
	    return "login";
	}
	*/
	
	//Login form met error
	@RequestMapping(value = "/login")
	public String loginError(Model model) {
	    model.addAttribute("loginError", true);
	    return "login";
	}
}
