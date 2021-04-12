/**
 * 
 */
package com.codetouch.esusu.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codetouch.user.User;

/**
 * @author CODETOUCH
 *
 */
@Entity
@Table(name="esusu")
public class Esusu {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="esusu_genertor")
	@SequenceGenerator(name="esusu_genertor", sequenceName="esusu_seq")
	@Column(name="id")
	private long id;
	
	@Column(name="name", length = 80, nullable = false)
	private String name;
	
	
	@Column(name="description", length = 200, nullable = false)
	private String description;
	
	
	@ManyToOne
	@JoinColumn(name="cycle",  nullable = false)
	private EsusuCycle cycle;
	
	@Column(name="amount",  nullable = false)
	private BigDecimal amount;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="owner_id",  nullable = false)
	private User owner;
	
	@Basic
	@Column(name="date_created", nullable=false)
	private LocalDate dateCreated;
	
	@Basic
	@Column(name="time_created", nullable=false)
	private LocalTime timeCreated;
	
	@Column(name="start_date", nullable=false)
	private LocalDate startDate;
	
	@Column(name="collection_start_date", nullable=false)
	private LocalDate collectionStartDate;
	
	@Column(name="collection_end_date", nullable=false)
	private LocalDate collectionEndDate;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy=("esusu"))
	private List<Role> role;

	/*private boolean cancelled;
	
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public LocalTime getTimeCancelled() {
		return timeCancelled;
	}

	public void setTimeCancelled(LocalTime timeCancelled) {
		this.timeCancelled = timeCancelled;
	}

	public LocalDate getDateCancelled() {
		return dateCancelled;
	}

	public void setDateCancelled(LocalDate dateCancelled) {
		this.dateCancelled = dateCancelled;
	}

	@Basic
	@Column(name="time_cancelled", nullable=true)
	private LocalTime timeCancelled;
	
	@Column(name="date_cancelled", nullable=true)
	private LocalDate dateCancelled;*/

	public Esusu() {}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public EsusuCycle getCycle() {
		return cycle;
	}

	public void setCycle(EsusuCycle cycle) {
		this.cycle = cycle;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalTime getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(LocalTime timeCreated) {
		this.timeCreated = timeCreated;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getCollectionStartDate() {
		return collectionStartDate;
	}

	public void setCollectionStartDate(LocalDate collectionStartDate) {
		this.collectionStartDate = collectionStartDate;
	}

	public LocalDate getCollectionEndDate() {
		return collectionEndDate;
	}

	public void setCollectionEndDate(LocalDate collectionEndDate) {
		this.collectionEndDate = collectionEndDate;
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	

}
