package com.codetouch.esusu.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
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
@Table(name="es_pending_esusu")
public class PendingEsusu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(
            name = "pendingEsusuSequence",
            sequenceName = "pending_esusu_id_seq",
            allocationSize = 1,
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pendingEsusuSequence")
    public long id;
	
	@ManyToOne
	@JoinColumn(name="created_by",  nullable = false)
	private User createdBy;
	
	
	@ManyToOne
	@JoinColumn(name="esusu",  nullable = false)
	private Esusu esusu;
	
	@Basic
	private LocalDateTime createdTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Esusu getEsusu() {
		return esusu;
	}

	public void setEsusu(Esusu esusu) {
		this.esusu = esusu;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	

	
}
