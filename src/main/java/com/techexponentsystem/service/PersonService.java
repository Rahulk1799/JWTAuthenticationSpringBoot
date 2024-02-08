package com.techexponentsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.techexponentsystem.model.Person;



@Service
public class PersonService {

	private List<Person> personList =new ArrayList<Person>();
	
	public PersonService() {
		personList.add(new Person(UUID.randomUUID().toString(),"Rahul Kumar","rahulkumar@gmail.com"));
		personList.add(new Person(UUID.randomUUID().toString(),"Divyansh Agarwal","divyansh23@gmail.com"));
		personList.add(new Person(UUID.randomUUID().toString(),"Sagar","sagar@gmail.com"));
		personList.add(new Person(UUID.randomUUID().toString(),"Samay","krsamay@gmail.com"));
		personList.add(new Person(UUID.randomUUID().toString(),"Siddharth","sid123@gmail.com"));
		personList.add(new Person(UUID.randomUUID().toString(),"Sohan","sohan32@gmail.com"));
		personList.add(new Person(UUID.randomUUID().toString(),"Saurabh","saurabh@gmail.com"));
		personList.add(new Person(UUID.randomUUID().toString(),"Raman Kumar","raman@gmail.com"));
		personList.add(new Person(UUID.randomUUID().toString(),"Ram","ram24@gmail.com"));
		personList.add(new Person(UUID.randomUUID().toString(),"Soniya","soniya78@gmail.com"));
	}
	
	
	public List<Person> getPersonList(){
		return personList;}
	
}
