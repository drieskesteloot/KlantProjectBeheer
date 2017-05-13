package be.mobyus.omj.dao;


import org.springframework.data.repository.CrudRepository;

import be.mobyus.omj.model.Klant;

public interface KlantRepository extends CrudRepository<Klant, Long> {
		
}
