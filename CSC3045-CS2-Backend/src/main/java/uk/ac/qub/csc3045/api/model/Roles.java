package uk.ac.qub.csc3045.api.model;

import javax.persistence.*;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Roles other = (Roles) obj;
		
		if (developer == null) {
			if (other.developer != null) {
				return false;
			}
		} else if (!developer.equals(other.developer)) {
			return false;
		}
		
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		if (productOwner == null) {
			if (other.productOwner != null) {
				return false;
			}
		} else if (!productOwner.equals(other.productOwner)) {
			return false;
		}
		
		if (scrumMaster == null) {
			if (other.scrumMaster != null) {
				return false;
			}
		} else if (!scrumMaster.equals(other.scrumMaster)) {
			return false;
		}
		
		return true;
	}
	
}