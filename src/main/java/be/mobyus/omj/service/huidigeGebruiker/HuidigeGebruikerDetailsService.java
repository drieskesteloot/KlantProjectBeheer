package be.mobyus.omj.service.huidigeGebruiker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import be.mobyus.omj.model.Gebruiker;
import be.mobyus.omj.model.HuidigeGebruiker;
import be.mobyus.omj.service.gebruiker.GebruikerService;

@Service
public class HuidigeGebruikerDetailsService implements UserDetailsService {
	
	@Autowired
	private GebruikerService gebruikerService;
	
	@Override
	public HuidigeGebruiker loadUserByUsername(String email) throws UsernameNotFoundException {
		Gebruiker gebruiker = gebruikerService.getGebruikerByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
		return new HuidigeGebruiker(gebruiker);
	}

}
