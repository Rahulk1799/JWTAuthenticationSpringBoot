package com.techexponentsystem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.techexponentsystem.model.Person;
import com.techexponentsystem.service.PersonService;

@RestController
@RequestMapping("/authenticate")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("/person")
	public List<Person> getPersonsList()
	{
		logger.info("Fetching list of persons");
		return personService.getPersonList();
	}
}
