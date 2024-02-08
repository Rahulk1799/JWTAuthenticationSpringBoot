package com.techexponentsystem.model;

public class JwtResponse {

	private String username;
	
	private String jwtToken;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	
	public JwtResponse() {
		super();
	
	}

	public JwtResponse(String username, String jwtToken) {
		super();
		this.username = username;
		this.jwtToken = jwtToken;
	}

	@Override
	public String toString() {
		return "JwtResponse [username=" + username + ", jwtToken=" + jwtToken + "]";
	}	
	
	
	// Builder method to create a new instance of JwtResponseBuilder
    public static JwtResponseBuilder builder() {
        return new JwtResponseBuilder();
    }

    // Builder class
    public static class JwtResponseBuilder {
        private String username;
        private String jwtToken;

        // Setter methods for setting values
        public JwtResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public JwtResponseBuilder jwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        // Build method to create a new JwtResponse instance
        public JwtResponse build() {
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.username = this.username;
            jwtResponse.jwtToken = this.jwtToken;
            return jwtResponse;
        }
    }
	
}
