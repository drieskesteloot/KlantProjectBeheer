package be.mobyus.omj.service.factuur;

import java.util.Collection;

import be.mobyus.omj.model.Factuur;
import be.mobyus.omj.model.Klant;

public interface FactuurService {
	
	Factuur getFactuurById(long id);
	Collection<Factuur> getAllFacturen();
	Collection<Factuur> GetFacturenByKlant(Klant klant);
	
	Factuur create(Factuur factuur);
	Factuur update(Factuur factuur);

}
