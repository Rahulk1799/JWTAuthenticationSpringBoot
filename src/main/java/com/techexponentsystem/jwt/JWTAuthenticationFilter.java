package com.techexponentsystem.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.techexponentsystem.controller.HomeController;
import com.techexponentsystem.exception.JwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;


@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestHeader=request.getHeader("Authorization");
		
		logger.info("Header : {}",requestHeader);
		
		String username=null;
		
		String token=null;
		
		
		// Here we have provided the extra security that token should append with the "Bearer".....
		if(requestHeader!=null&&requestHeader.startsWith("Bearer"))
		{
			token=requestHeader.substring(7);
			
			System.out.println(token);
			try {
				username=this.jwtHelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException e) {
				logger.info("Illeagal Argument while fetching the username !!");
				e.printStackTrace();
				throw new JwtException(e.getMessage());	
			}
			catch(ExpiredJwtException e) {
				logger.info("Given JWt token is expired !!");
				e.printStackTrace();
				throw new JwtException(e.getMessage());
			}
			catch(MalformedJwtException e) {
				logger.info("Some changes has been done in the token !! Invalid Token...");
				e.printStackTrace();
				throw new JwtException(e.getMessage());
			}
			catch(Exception e) {
				e.printStackTrace();
				throw new JwtException(e.getMessage());
			}
		}
		else
		{
			logger.info("Invalid Header Value !!");
		}
		
		
		if(username!=null&&SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			
			Boolean validateToken=this.jwtHelper.validateToken(token, userDetails);
			
			if(validateToken) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			}
			else
			{
				logger.info("Validation fails !!");
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
