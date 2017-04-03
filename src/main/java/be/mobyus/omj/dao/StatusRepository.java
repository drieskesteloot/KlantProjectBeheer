package be.mobyus.omj.dao;

import org.springframework.data.repository.CrudRepository;

import be.mobyus.omj.model.Status;

public interface StatusRepository extends CrudRepository<Status, Long> {

}
