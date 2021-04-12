package com.codetouch.esusu.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codetouch.user.User;

@Entity
@Table(name="es_contribution")
public class EsusuContribution {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cont_genertor")
	@SequenceGenerator(name="cont_genertor", sequenceName="esusu_cont")
	@Column(name="id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="member",  nullable = false)
	private User member;
	
	
	@ManyToOne
	@JoinColumn(name="esusu",  nullable = false)
	private Esusu esusu;
	
	@Column(name="amount", length = 80, nullable = false)
	private String amount;
	
	@Column(name="date_contributed", length = 30, nullable = false)
	private LocalDate date_contributed;
	
	@Column(name="time_contributed", length = 30, nullable = false)
	private LocalTime time_contributed;

	public EsusuContribution() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	public Esusu getEsusu() {
		return esusu;
	}

	public void setEsusu(Esusu esusu) {
		this.esusu = esusu;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public LocalDate getDate_contributed() {
		return date_contributed;
	}

	public void setDate_contributed(LocalDate date_contributed) {
		this.date_contributed = date_contributed;
	}

	public LocalTime getTime_contributed() {
		return time_contributed;
	}

	public void setTime_contributed(LocalTime time_contributed) {
		this.time_contributed = time_contributed;
	}
	
	
}
