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
	
	private Boolean isDeveloper = false;
	private Boolean isScrumMaster = false;
	private Boolean isProductOwner = false;
	
	public Roles() {}
	
	public Roles(Boolean isDeveloper, Boolean isScrumMaster, Boolean isProductOwner) {
		this.isDeveloper = isDeveloper;
		this.isScrumMaster = isScrumMaster;
		this.isProductOwner = isProductOwner;
	}

	public Boolean getIsDeveloper() {
		return isDeveloper;
	}

	public void setIsDeveloper(Boolean isDeveloper) {
		this.isDeveloper = isDeveloper;
	}

	public Boolean getIsScrumMaster() {
		return isScrumMaster;
	}

	public void setIsScrumMaster(Boolean isScrumMaster) {
		this.isScrumMaster = isScrumMaster;
	}

	public Boolean getIsProductOwner() {
		return isProductOwner;
	}

	public void setIsProductOwner(Boolean isProductOwner) {
		this.isProductOwner = isProductOwner;
	}
	
}
