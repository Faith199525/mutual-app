package com.codetouch.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="us_notification")
public class UserNotification {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="us_genertor")
	@SequenceGenerator(name="us_genertor", sequenceName="us_seq")
	@Column(name="id")
    public long id;
	
	@Column(name="last_name", length =  50, nullable = false)
	private String lastName;
	
	@Column(name="email", length = 80, nullable = false, unique=true)
	private String email;
	
	@Column(name="confirmationToken", length = 80, nullable = false, unique=true)
	private String confirmationToken;

	public UserNotification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	
	
}
