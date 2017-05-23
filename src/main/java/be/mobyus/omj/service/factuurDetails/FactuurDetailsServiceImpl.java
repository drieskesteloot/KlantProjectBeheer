package be.mobyus.omj.service.factuurDetails;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.mobyus.omj.dao.FactuurDetailsRepository;
import be.mobyus.omj.model.Factuur;
import be.mobyus.omj.model.FactuurDetails;

@Service
public class FactuurDetailsServiceImpl implements FactuurDetailsService {
	
	@Autowired
	FactuurDetailsRepository factuurDetailsrepo;
	
	@Override
	public FactuurDetails getFactuurDetailsById(long id){
		return null;
	}
	
	@Override
	public Collection<FactuurDetails> getAllFactuurDetails(){
		return factuurDetailsrepo.findAll();
	}
	
	@Override
	public Collection<FactuurDetails> getAllFactuurDetailsByFactuur(Factuur factuur){
		return factuurDetailsrepo.findByFactuur(factuur);
	}
	
	@Override
	public FactuurDetails create(FactuurDetails factuurDetails){
		return factuurDetailsrepo.save(factuurDetails);
	}
	
	@Override
	public FactuurDetails update(FactuurDetails factuurDetails){
		return factuurDetailsrepo.save(factuurDetails);
	}
}
