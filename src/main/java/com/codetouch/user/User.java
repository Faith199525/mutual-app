package com.codetouch.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.codetouch.esusu.entity.Esusu;
import com.codetouch.esusu.entity.Role;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name="core_user")
public class User{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_genertor")
	@SequenceGenerator(name="user_genertor", sequenceName="user_seq")
	@Column(name="id")
    public long id;
	
	@Column(name="first_name", length =  50, nullable = false)
	private String firstName;
	
	@Column(name="middle_name", length =  50, nullable = true)
	private String middleName;
	
	@Column(name="last_name", length =  50, nullable = false)
	private String lastName;
	
	@Column(name="sex", length = 30, nullable = false)
	private String sex;
	
	@Column(name="phone_number", length = 80, nullable = false)
	private String phoneNumber;
	
	@Column(name="email", length = 80, nullable = false, unique=true)
	private String email;
	
	@Column(name="country", length = 80, nullable = false)
	private String country;
	
	@Column(name="state", length = 80, nullable = false)
	private String state;
	
	@Column(name="password", length = 80, nullable = false)
	private String password;
	
	/*@Column(name="confirmationToken", length = 80, nullable = false)
	private String confirmationToken;*/

	@OneToMany(fetch=FetchType.LAZY, mappedBy=("owner"))
	private List<Esusu> esusu;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy=("owner"))
	private List<Role> role;
	public User() {}

	public List<Esusu> getEsusu() {
		return esusu;
	}

	public void setEsusu(List<Esusu> esusu) {
		this.esusu = esusu;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
