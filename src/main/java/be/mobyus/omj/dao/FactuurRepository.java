package be.mobyus.omj.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import be.mobyus.omj.model.Factuur;
import be.mobyus.omj.model.Klant;

public interface FactuurRepository extends JpaRepository<Factuur, Long> {
	
	public Collection<Factuur> findByKlant(Klant klant);

}
