package com.techexponentsystem.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {

	   //Here I have specified the JWt Token validity (It means token is going to expire in the 5 hrs.).
	    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // This is 5hr * 60min * 60 seconds...

	    
	    //Note:- secret key must be greater than the Size of algorithm otherwise JWt will not be generated..)
	    //Example- I have used the "HS512"  algorithm below to generate the Token...
	    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

	    //Retrieve username from jwt token....
	    public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }

	    //Retrieve expiration date from jwt token
	    public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }

	    
	    //Who has claimed the token...
	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }

	    //For retrieving any information from token we will need the secret key..
	    private Claims getAllClaimsFromToken(String token) {
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }

	    //Check if the token has expired...
	    private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }

	    //Generate token for the user
	    public String generateToken(UserDetails userDetails) {
	        Map<String, Object> claims = new HashMap<>();
	        return doGenerateToken(claims, userDetails.getUsername());
	    }

	    //while creating the token -
	    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	    //2. Sign the JWT using the HS512 algorithm and secret key.
	    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	    //   compaction of the JWT to a URL-safe string
	    private String doGenerateToken(Map<String, Object> claims, String subject) {

	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // converting to milliseconds
	                .signWith(SignatureAlgorithm.HS512, secret)
	                .compact();
	    }

	    //Method to validate token..
	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = getUsernameFromToken(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
}
