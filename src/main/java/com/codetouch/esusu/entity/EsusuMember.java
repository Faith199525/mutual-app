/**
 * 
 */
package com.codetouch.esusu.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.codetouch.user.User;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * @author CODETOUCH
 *
 */
@Entity
@Table(name="es_esusu_member", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"member", "esusu"})
	})
public class EsusuMember {
	
	@Id
    @SequenceGenerator(
            name = "esusuMemberSequence",
            sequenceName = "esusu_member_id_seq",
            allocationSize = 1,
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "esusuMemberSequence")
    public long id;
	
	@ManyToOne
	@JoinColumn(name="member",  nullable = false)
	private User member;
	
	
	@ManyToOne
	@JoinColumn(name="esusu",  nullable = false)
	private Esusu esusu;
	

	@Basic
	@Column(name="date_accepted",  nullable = false)
	private LocalDate dateAccepted;
	
	@Basic
	@Column(name="time_accepted",  nullable = false)
	private LocalTime timeAccepted;

	@Column(name="schedule",  nullable = false)
	private long schedule;
	
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

	public long getSchedule() {
		return schedule;
	}

	public void setSchedule(long schedule) {
		this.schedule = schedule;
	}

	public LocalDate getDateAccepted() {
		return dateAccepted;
	}

	public void setDateAccepted(LocalDate dateAccepted) {
		this.dateAccepted = dateAccepted;
	}

	public LocalTime getTimeAccepted() {
		return timeAccepted;
	}

	public void setTimeAccepted(LocalTime timeAccepted) {
		this.timeAccepted = timeAccepted;
	};
}
