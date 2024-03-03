package it.aips.securityJWT.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.aips.securityJWT.security.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

	@Entity
	@Table(name="_user")
	public class User implements UserDetails {
	    @Id
	    @GeneratedValue
	    private Integer id;
	    private String firstname;
	    private String lastname;
	    private String email;
	    private String password;
	    @Enumerated(EnumType.STRING)
	    private Role role;
	    
	    public User() {
	    }
	    
	    public User(Integer id, String firstname, String lastname, 
	    		String email, String password, Role role) {
	    	this.id=id;
	    	this.firstname=firstname;
	    	this.lastname=lastname;
	    	this.email=email;
	    	this.password=password;
	    	this.role=role;
	    }
	    
	    
	    public static UserBuilder builder() {
	        return new UserBuilder();
	    }
	    
	    public static class UserBuilder {

	    	private Integer id;
	        private String email;
	        private String password;
	        private String firstname;
	        private String lastname;
		    @Enumerated(EnumType.STRING)
		    private Role role;
		    
		    
	        public UserBuilder id(Integer id) {
	            this.id = id;
	            return this;
	        }
	        
	        public UserBuilder email(String email) {
	            this.email = email;
	            return this;
	        }

	        public UserBuilder password(String password) {
	            this.password = password;
	            return this;
	        }
	        
	        public UserBuilder firstname(String firstname) {
	            this.firstname = firstname;
	            return this;
	        }

	        public UserBuilder lastname(String lastname) {
	            this.lastname = lastname;
	            return this;
	        }
	        
	        public UserBuilder role(Role role) {
	            this.role = role;
	            return this;
	        }
	        
	        public User build() {
	            return new User(id, firstname, lastname, 
	    	    		email, password, role);
	        }

	    }
	    
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return List.of(new SimpleGrantedAuthority(role.name()));
	    }

	    @Override
	    public String getPassword() {
	        return password;
	    }

	    @Override
	    public String getUsername() {
	        return email;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	

}
