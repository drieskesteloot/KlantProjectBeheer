package be.mobyus.omj.dao;


import org.springframework.data.repository.CrudRepository;

import be.mobyus.omj.model.Klant;
import be.mobyus.omj.model.Project;

public interface KlantRepository extends CrudRepository<Klant, Long> {

}
