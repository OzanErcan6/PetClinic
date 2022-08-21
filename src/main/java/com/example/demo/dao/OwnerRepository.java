package com.example.demo.dao;

import java.util.List;
import com.example.demo.model.Owner;


public interface OwnerRepository {
	List<Owner> findAll();
	Owner findById(Long id);
	List<Owner> findByLastName(String lastName);
	void create(Owner owner);
	// JPA Hibernate'de güncellenince aynı referansı dönmez bu sebeple void dönmez
	Owner update(Owner owner); // güncellenen Owner döndürülmeli
	void delete(Long id);
	
}
