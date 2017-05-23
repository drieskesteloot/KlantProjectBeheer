package be.mobyus.omj.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import be.mobyus.omj.model.Factuur;
import be.mobyus.omj.model.FactuurDetails;

public interface FactuurDetailsRepository extends JpaRepository<FactuurDetails, Long>{
	
	public Collection<FactuurDetails> findByFactuur(Factuur factuur);
	
}
