package be.mobyus.omj.dao;

import org.springframework.data.repository.CrudRepository;

import be.mobyus.omj.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
