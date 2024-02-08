package com.techexponentsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techexponentsystem.jwt.JwtHelper;
import com.techexponentsystem.model.JwtRequest;
import com.techexponentsystem.model.JwtResponse;


@RestController
public class JwtAuthenticationController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    private AuthenticationManager manager;

	@Autowired
    private JwtHelper helper;
    
    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);

        
        //Newly Added....
        JwtResponse.JwtResponseBuilder builder = JwtResponse.builder();
        builder.username(userDetails.getUsername());
        builder.jwtToken(token);
        
        // Building the JwtResponse instance..
        JwtResponse response = builder.build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credentials Invalid !!");
        }

    }

}
