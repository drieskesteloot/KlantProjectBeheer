package be.mobyus.omj.service.factuurDetails;

import java.util.Collection;

import be.mobyus.omj.model.Factuur;
import be.mobyus.omj.model.FactuurDetails;

public interface FactuurDetailsService {
	
	FactuurDetails getFactuurDetailsById(long id);
	Collection<FactuurDetails> getAllFactuurDetails();
	Collection<FactuurDetails> getAllFactuurDetailsByFactuur(Factuur factuur);
	
	FactuurDetails create(FactuurDetails factuurDetails);
	FactuurDetails update(FactuurDetails factuurDetails);
}
