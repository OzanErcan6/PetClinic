package com.example.demo.dao.mem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.dao.OwnerRepository;
import com.example.demo.model.Owner;

// to create a bean in runtime, we will inject this bean to petclinicservice bean
@Repository
public class OwnerRepositoryInMemoryImpl implements OwnerRepository{
	
	private Map<Long, Owner> ownersMap = new HashMap<>();
	
	
	public OwnerRepositoryInMemoryImpl() {
		Owner owner1 = new Owner();
		owner1.setId(1L);
		owner1.setFirstName("ozan");
		owner1.setLastName("ercan");
		
		Owner owner2 = new Owner();
		owner2.setId(2L);
		owner2.setFirstName("ozan2");
		owner2.setLastName("ercan2");

		ownersMap.put(owner1.getId(), owner1);
		ownersMap.put(owner2.getId(), owner2);

	}

	@Override
	public List<Owner> findAll() {
		return new ArrayList<>(ownersMap.values());
	}

	@Override
	public Owner findById(Long id) {
		return ownersMap.get(id);
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		return ownersMap.values().stream().filter(o->o.getLastName().equals(lastName)).collect(Collectors.toList());
	}

	@Override
	public void create(Owner owner) {
		owner.setId(new Date().getTime());
		ownersMap.put(owner.getId(), owner);
	}

	@Override
	public Owner update(Owner owner) {
		ownersMap.replace(owner.getId(), owner);
		return owner;
	}

	@Override
	public void delete(Long id) {
		ownersMap.remove(id);
	}

}
