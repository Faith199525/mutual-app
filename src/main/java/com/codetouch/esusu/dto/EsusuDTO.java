package com.codetouch.esusu.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.json.bind.annotation.JsonbPropertyOrder;


@JsonbPropertyOrder({ "name", "description", "cycle", "amount", "owner"})
public class EsusuDTO {
	
	private String name;
	private String description;
	private long cycleId;
	private BigDecimal amount;
	private LocalDate startDate;
	private LocalDate collectionStartDate;
	private LocalDate collectionEndDate;
	
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
	public long getCycleId() {
		return cycleId;
	}
	public void setCycleId(long cycleId) {
		this.cycleId = cycleId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	
}
