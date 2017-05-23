package be.mobyus.omj.service.factuur;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.mobyus.omj.dao.FactuurRepository;
import be.mobyus.omj.dao.KlantRepository;
import be.mobyus.omj.model.Factuur;
import be.mobyus.omj.model.Klant;

@Service
public class FactuurServiceImpl implements FactuurService {
	
	@Autowired
	FactuurRepository factuurrepo;
	
	@Autowired
	KlantRepository klantrepo;
	
	@Override
	public Factuur getFactuurById(long id){
		return factuurrepo.findOne(id);
	}
	
	@Override
	public Collection<Factuur> getAllFacturen(){
		return factuurrepo.findAll();
	}
	
	@Override
	public Collection<Factuur> GetFacturenByKlant(Klant klant){
		return factuurrepo.findByKlant(klant);
	}
	
	@Override
	public Factuur create(Factuur factuur){
		return factuurrepo.save(factuur);
	}
	
	@Override
	public Factuur update(Factuur factuur){
		return factuurrepo.save(factuur);
	}
	
}
