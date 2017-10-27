package uk.ac.qub.csc3045.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Roles {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Boolean developer = false;
	private Boolean scrumMaster = false;
	private Boolean productOwner = false;
	
	public Roles() {}
	
	public Roles(Boolean developer, Boolean scrumMaster, Boolean productOwner) {
		this.developer = developer;
		this.scrumMaster = scrumMaster;
		this.productOwner = productOwner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isDeveloper() {
		return developer;
	}

	public void setDeveloper(Boolean developer) {
		this.developer = developer;
	}

	public Boolean isScrumMaster() {
		return scrumMaster;
	}

	public void setScrumMaster(Boolean scrumMaster) {
		this.scrumMaster = scrumMaster;
	}

	public Boolean isProductOwner() {
		return productOwner;
	}

	public void setProductOwner(Boolean productOwner) {
		this.productOwner = productOwner;
	}
}