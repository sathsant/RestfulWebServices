package com.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAResource {

	
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/jpa/users")
	public List<User> retrieveAll()
	{
		return userRepository.findAll();
	}
	
	@GetMapping(path="/jpa/users/{id}")
	public Optional<User> retrieveUser(@PathVariable int id)
	{
		Optional<User> user=userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id" +id);
		}
		//return service.findOne(id);
		return user;
				
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAll(@PathVariable int id)
	{
		Optional<User> user= userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id" +id);
		}
		return user.get().getPosts();
	}
	
	@DeleteMapping(path="/jpa/users/{id}")
	public void DeleteUser(@PathVariable int id)
	{
		userRepository.deleteById(id);
				
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user)
	{
	     	User SavedUser=userRepository.save(user);
	
	//users/{id}
	URI location=ServletUriComponentsBuilder
	.fromCurrentRequest()
	.path("{id}")
	.buildAndExpand(SavedUser.getId()).toUri();
	
	return ResponseEntity.created(location).build();
	}
	
	
}	
	
	



