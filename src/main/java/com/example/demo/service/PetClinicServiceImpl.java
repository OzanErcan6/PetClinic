package com.example.demo.service;

import java.util.List;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.OwnerRepository;
import com.example.demo.dao.PetRepository;
import com.example.demo.exception.OwnerNotFoundException;
import com.example.demo.model.Owner;

@Service // spring container bu sınıftan bir bean yaratır
@Transactional(rollbackFor = Exception.class)
// tüm public methodlar transactional olur
// herhangi bir servis methoda çağrısı sırasında aktif bir transaction yoksa
// yeni bir transaction başlatılacak methodun başarılı veya basarısız sonuclanmasına göre
// commit veya rollback gerçekleştirilecek
public class PetClinicServiceImpl implements PetClinicService {

	private OwnerRepository ownerRepository;
	private PetRepository petRepository;
	
	@Autowired
	public void setOwnerRepository(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}
	
	@Autowired
	public void setPetRepository(PetRepository petRepository) {
		this.petRepository = petRepository;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
	//For SUPPORTS, Spring first checks if an active transaction exists. 
	//If a transaction exists, then the existing transaction will be used.
	//If there isn't a transaction, it is executed non-transactional:
	public List<Owner> findOwners() {
		return ownerRepository.findAll();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
	public List<Owner> findOwners(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
	public Owner findOwner(Long id) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(id);
		if(owner == null) throw new OwnerNotFoundException("Owner not found with id:" + id);
		return owner;
	}

	@Override
	public void createOwner(Owner owner) {
		ownerRepository.create(owner);
	}

	@Override
	public void updateOwner(Owner owner) {
		ownerRepository.update(owner);
	}

	@Override
	public void deleteOwner(Long id) {
		petRepository.deleteByOwnerId(id);
		ownerRepository.delete(id);
		//if(true) throw new RuntimeErrorException(null, "testing rollback");
		
	}

}
