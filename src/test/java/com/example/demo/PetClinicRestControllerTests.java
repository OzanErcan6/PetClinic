package com.example.demo;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Owner;

public class PetClinicRestControllerTests {
	private RestTemplate restTemplate;
	
	//test initialization, runs before for each test function 
	@BeforeEach
	public void init() {
		this.restTemplate = new RestTemplate();
	}
	
	//@Test
	public void testGetOwners() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8084/rest/owners", List.class);
	
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String,String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("ozan","duygu","zehra","ışın"));
	}
	
	//@Test
	public void testGetOwnerByLastName() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8084/rest/owner?ln=ercan", List.class);
	
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String,String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("ozan","duygu","zehra"));
	}
	
	//@Test
	public void testGetOwnerById() {
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8084/rest/owner/1", Owner.class);
	
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("ozan"));
	}
	
	//@Test
	public void testCreateOwner() {
		Owner owner = new Owner();
		owner.setFirstName("deneme");
		owner.setLastName("denemelast");
		
		URI location = restTemplate.postForLocation("http://localhost:8084/rest/owner", owner);
		
		Owner owner2 = restTemplate.getForObject(location, Owner.class);
		
		MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo(owner.getFirstName()));
		MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo(owner.getLastName()));
	}
	
	//@Test
	public void testUpdateOwner() {
		Owner owner = restTemplate.getForObject("http://localhost:8084/rest/owner/1", Owner.class);

		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("ozan"));
		
		owner.setFirstName("ozannew");
		restTemplate.put("http://localhost:8084/rest/owner/1", owner);
		
		owner = restTemplate.getForObject("http://localhost:8084/rest/owner/1", Owner.class);
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("ozannew"));
	}
	

	@Test
	public void testDeleteOwner() {
		restTemplate.delete("http://localhost:8084/rest/owner/1");
		
		try {
			restTemplate.getForEntity("http://localhost:8084/rest/owner/1", List.class);
			Assert.fail("should have not returned owner");
		}catch(HttpClientErrorException ex) {
			MatcherAssert.assertThat(ex.getStatusCode(), Matchers.equalTo(404));
		}

	}
	


	

	

}
