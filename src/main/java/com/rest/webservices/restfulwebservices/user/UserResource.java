package com.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;
	
	@GetMapping("/users")
	public List<User> retrieveAll()
	{
		return service.findAll();
	}
	
	@GetMapping(path="/users/{id}")
	public User retrieveUser(@PathVariable int id)
	{
		User user=service.findOne(id);
		if(user==null) {
			throw new UserNotFoundException("id" +id);
		}
		return service.findOne(id);
		
	}
	
	@DeleteMapping(path="/users/{id}")
	public void DeleteUser(@PathVariable int id)
	{
		User user=service.DeleteOne(id);
		if(user==null) {
			throw new UserNotFoundException("id" +id);
		}
		//return service.findOne(id);
		
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user)
	{
	     	User SavedUser=service.save(user);
	
	//users/{id}
	URI location=ServletUriComponentsBuilder
	.fromCurrentRequest()
	.path("{id}")
	.buildAndExpand(SavedUser.getId()).toUri();
	
	return ResponseEntity.created(location).build();
	}
}	
	
	



