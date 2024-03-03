package it.aips.securityJWT.auth;

public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    
    public RegisterRequest() {
    }
    
    public RegisterRequest(String firstname, String lastname, String email, String password) {
    	this.firstname=firstname;
    	this.lastname=lastname;
    	this.email=email;
    	this.password=password;
    }
    
    public static RegisterRequestBuilder builder() {
        return new RegisterRequestBuilder();
    }
    
    public static class RegisterRequestBuilder {

        private String email;
        private String password;
        private String firstname;
        private String lastname;

        public RegisterRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegisterRequestBuilder password(String password) {
            this.password = password;
            return this;
        }
        
        public RegisterRequestBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public RegisterRequestBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }
        
        public RegisterRequest build() {
            return new RegisterRequest(firstname,lastname,email, password);
        }

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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

