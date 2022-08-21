package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Owner;

public class PetClinicRestControllerTests {
	private RestTemplate restTemplate;
	
	//test initialization
	@Before
	public void init() {
		
	}
	
	@Test
	public void testGetOwnerById() {
		this.restTemplate = new RestTemplate();
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8084/rest/owner/1", Owner.class);
	
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("ozan"));

	}
	@Test
	public void testGetOwnerByLastName() {
		this.restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8084/rest/owner?ln=ercan", List.class);
	
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String,String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("ozan","duygu","zehra"));

	}
	
	@Test
	public void testGetOwners() {
		this.restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8084/rest/owners", List.class);
	
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String,String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("ozan","duygu","zehra","ışın"));

	}
}
