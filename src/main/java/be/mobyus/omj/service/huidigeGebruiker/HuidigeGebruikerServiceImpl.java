package be.mobyus.omj.service.huidigeGebruiker;

import be.mobyus.omj.model.HuidigeGebruiker;
import be.mobyus.omj.model.Rol;

public class HuidigeGebruikerServiceImpl implements HuidigeGebruikerService {
	
	@Override
	public Boolean canAccessGebruiker(HuidigeGebruiker huidigeGebruiker, Long gebruikerId){
		return huidigeGebruiker != null && 
				(huidigeGebruiker.getRol() == Rol.ADMIN || huidigeGebruiker.getId().equals(gebruikerId));
	}
	
}
