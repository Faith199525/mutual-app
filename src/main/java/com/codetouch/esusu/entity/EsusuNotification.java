package com.codetouch.esusu.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.codetouch.esusu.enums.EsusuNotificationStatus;
import com.codetouch.user.User;

@Entity
@Table(name="es_notification")
public class EsusuNotification {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="notify_genertor")
	@SequenceGenerator(name="notify_genertor", sequenceName="noti_genertor")
	@Column(name="id")
    public long id;
	
	@Column(name="first_name", length =  50)
	private String firstName;
	
	@Column(name="last_name", length =  50)
	private String lastName;
	
	@Column(name="email", length = 80)
	private String email;

	@Column(name="confirmationToken", length = 80, unique = true)
	private String confirmationToken;
	
	@Column(name="owner",  nullable = false)
	private Long owner;
	
	@Column(name="esusu",  nullable = false)
	private Long esusu;
	
	@Column(name="link_expiryDate", length = 80)
	private LocalDate linkExpiryDate;
	
	@Column(name="link_sentDate", length = 80)
	private LocalDate linkSentDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status", length = 20)
	private EsusuNotificationStatus status;

	public EsusuNotification() {
		super();
		// TODO Auto-generated constructor stub
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

	
	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Long getEsusu() {
		return esusu;
	}

	public void setEsusu(Long esusu) {
		this.esusu = esusu;
	}

	public LocalDate getLinkExpiryDate() {
		return linkExpiryDate;
	}

	public void setLinkExpiryDate(LocalDate linkExpiryDate) {
		this.linkExpiryDate = linkExpiryDate;
	}

	public LocalDate getLinkSentDate() {
		return linkSentDate;
	}

	public void setLinkSentDate(LocalDate linkSentDate) {
		this.linkSentDate = linkSentDate;
	}

	public EsusuNotificationStatus getStatus() {
		return status;
	}

	public void setStatus(EsusuNotificationStatus status) {
		this.status = status;
	}


	
	
}
