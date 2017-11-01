package uk.ac.qub.csc3045.api.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @NotNull
    @Valid
    private User user;
    @NotNull
    private String username;
    @NotNull
    private String password;

    public Account() { }

    public Account(User user, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public boolean equals(Object toCompare) {
    	if (!(toCompare instanceof Account))
    		return false;
    	
    	Account account = ((Account)toCompare);
    	account.setId(null);
   
    	if (this.username.equalsIgnoreCase(account.getUsername())
			&& this.user.equals(account.user)) {
    		return true;
    	}
    	return false;
    }
}
