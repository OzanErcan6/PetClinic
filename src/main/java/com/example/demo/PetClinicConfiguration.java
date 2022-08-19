package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetClinicConfiguration {
	
	// PetClinicConfiguration bean yaratıldıktan ve bagımlılıkları
	// enjekte edildikten sonra init otomatik olarak cagrilir
	@Autowired
	private PetClinicProperties petClinicProperties;
	
	// life cycle
	@PostConstruct
	public void init() {
		System.out.println("Display owners with pets: " + 
				petClinicProperties.isDisplayOwnersWithPets());
	}
}
