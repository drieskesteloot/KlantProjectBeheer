package be.mobyus.omj.service.huidigeGebruiker;

import be.mobyus.omj.model.HuidigeGebruiker;

public interface HuidigeGebruikerService {
	
	Boolean canAccessGebruiker(HuidigeGebruiker huidigeGebruiker, Long gebruikerId);

}
