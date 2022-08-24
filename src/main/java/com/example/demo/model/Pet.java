package com.example.demo.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="t_pet")
public class Pet {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="petClinicSeqGen")
	@SequenceGenerator(name="petClinicSeqGen",sequenceName="petclinic_sequence", allocationSize = 1)
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="birth_date")
	private Date birthDate;
	@ManyToOne
	@JoinColumn(name="owner_id")
	private Owner owner;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", owner=" + owner + "]";
	}
	
	
}