/**
 * 
 */
package com.codetouch.esusu.entity;

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

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * @author CODETOUCH
 *
 *This entity specifies the cycle of esusue 
 *ref
 */
@Entity
@Table(name="es_cycle")
public class EsusuCycle {
	
	@Id
    @SequenceGenerator(
            name = "esusucycleSequence",
            sequenceName = "esusu_cycle_id_seq",
            allocationSize = 1,
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "esusucycleSequence")
	private Long id;
	
	@Column(name="name", length=30, nullable = false)
	private String name;
	
	@Column(name="description",length=50, nullable = false)
	private String description;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy=("cycle"))
	private List<Esusu> esusu;
	
	public EsusuCycle() {}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}
