package be.mobyus.omj.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TypeProject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String naam;
	
	@OneToMany(mappedBy="typeProject")
	List<Project> projecten;
	
	public TypeProject(){}

	public TypeProject(String naam) {
		super();
		this.naam = naam;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	@Override
	public String toString() {
		return "TypeProject [id=" + id + ", naam=" + naam + "]";
	}
	
	

}
