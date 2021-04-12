package com.codetouch.esusu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.codetouch.user.User;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="role_genertor")
	@SequenceGenerator(name="role_genertor", sequenceName="role_seq")
	@Column(name="id")
	private long id;
	
	@Column(name="name", length = 80, nullable = false)
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="esusu_id",  nullable = false)
	private Esusu esusu;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="owner_id",  nullable = false)
	private User owner;
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Esusu getEsusu() {
		return esusu;
	}
	public void setEsusu(Esusu esusu) {
		this.esusu = esusu;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}	
	
}
