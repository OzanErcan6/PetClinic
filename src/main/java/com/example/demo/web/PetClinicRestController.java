package com.example.demo.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.OwnerNotFoundException;
import com.example.demo.model.Owner;
import com.example.demo.service.PetClinicService;

@RestController
@RequestMapping("/rest/")
public class PetClinicRestController {
	
	@Autowired
	private PetClinicService petClinicService;
	
	@RequestMapping(method = RequestMethod.GET, value="/owners")
	public ResponseEntity<List<Owner>> getOwners(){
		List<Owner> owners = petClinicService.findOwners();
		return ResponseEntity.ok(owners);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/owner")
	public ResponseEntity<List<Owner>> getOwners(@RequestParam("ln") String lastName){
		List<Owner> owners = petClinicService.findOwners(lastName);
		return ResponseEntity.ok(owners);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/owner/{id}")
	public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id){
		try {
			Owner owner = petClinicService.findOwner(id);
			return ResponseEntity.ok(owner);
		}catch(OwnerNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/owner")
	public ResponseEntity<URI> createOwner(@RequestBody Owner owner){
		try {
			petClinicService.createOwner(owner);
			Long id = owner.getId();
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(location).build();
		}catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/owner/{id}")
	public ResponseEntity<?> updateOwner(@PathVariable Long id, @RequestBody Owner ownerRequest){
		try {
			Owner owner = petClinicService.findOwner(id);
			owner.setFirstName(ownerRequest.getFirstName());
			owner.setLastName(ownerRequest.getLastName());
			petClinicService.updateOwner(owner);
			return ResponseEntity.ok().build();
		}catch(OwnerNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}
		catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/owner/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id){
		try {
			petClinicService.deleteOwner(id);
			return ResponseEntity.ok().build();
		}catch(OwnerNotFoundException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new InternalServerException(ex);
		}
	}
}
