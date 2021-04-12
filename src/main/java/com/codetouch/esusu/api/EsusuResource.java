package com.codetouch.esusu.api;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import com.codetouch.esusu.GsonResponse;
import com.codetouch.esusu.StatusMessage;
import com.codetouch.esusu.dao.EsusuDAO;
import com.codetouch.esusu.dto.EsusuDTO;
import com.codetouch.esusu.entity.Esusu;
import com.codetouch.esusu.entity.EsusuCycle;
import com.codetouch.esusu.entity.PendingEsusu;
import com.codetouch.esusu.entity.Role;
import com.codetouch.esusu.jwt.TokenUtils;
import com.codetouch.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;


@RequestScoped
@Path("/esusu")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class EsusuResource {

	@Inject
	EntityManager em;

	private Gson gson;
	private GsonResponse response = new GsonResponse();

	@Inject
	Mailer mailer;

	@Inject
	EsusuDAO dao;

	@Inject
    JsonWebToken jwt;
	
	@PostConstruct
	private void postConstruct() {
		GsonBuilder builder = new GsonBuilder();

		gson = builder.create();

	}

	@GET
	public Response hello() {
		List<StatusMessage> ml = new ArrayList<>();
		
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);
		List<Esusu> el = em.createQuery(" select c from  Esusu c ").getResultList();
		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(el);

		String j = gson.toJson(response);

		return Response.status(200).entity(j).build();
	}
	
	@POST
	@Transactional
	@Path("/claims")
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response printjwtclaims(@Context SecurityContext ctx) {
	
		Principal caller =  ctx.getUserPrincipal();
		System.out.println(caller);
		
		List<StatusMessage> ml = new ArrayList<>();
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);

		response.setStatus(200);
		response.setStatusMessages(ml);

		String j = gson.toJson(response);

		return Response.status(201).entity(j).build();
	}
	
	@POST
	@Transactional
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response register(EsusuDTO esusu, @Context SecurityContext ctx) {
		
		Principal caller =  ctx.getUserPrincipal();
		System.out.println(caller);
	
		List<StatusMessage> ml = new ArrayList<>();

		EsusuCycle ec = em.find(EsusuCycle.class, esusu.getCycleId()); 

		if (ec == null) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("Cycle cannot be empty");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			return Response.status(200).entity(j).build();

		}

		Long convert=Long.valueOf(jwt.getSubject());
		User us = em.find(User.class, convert);
		if (us == null) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("User cannot be empty ");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			return Response.status(200).entity(j).build();

		}

		LocalDate today = LocalDate.now();
		LocalDate date = esusu.getStartDate();

		if (date.isBefore(today)) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("Start Date is in the past ");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			return Response.status(200).entity(j).build();

		}
		
		if (date.equals(today)) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("Esusu cannot be started in the created Date");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			return Response.status(200).entity(j).build();

		}

		if (today.plusDays(5).isAfter(date)) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("Start Date is too close");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			return Response.status(200).entity(j).build();

		}
		Esusu es = new Esusu();
		
		if (dao.isThresholdExceeded(us)) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("You have unactivated esusu");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			return Response.status(200).entity(j).build();
		}

		es.setName(esusu.getName());
		es.setDescription(esusu.getDescription());
		es.setOwner(us);
		es.setCycle(ec);
		es.setAmount(esusu.getAmount());
		es.setDateCreated(LocalDate.now());
		es.setTimeCreated(LocalTime.now());
		es.setStartDate(esusu.getStartDate());
		es.setCollectionStartDate(esusu.getCollectionStartDate());
		es.setCollectionEndDate(esusu.getCollectionEndDate());

		em.persist(es);
		
		em.flush();
		Esusu esid =em.find(Esusu.class, es.getId());
		Role role= new Role();
		role.setOwner(us);
		role.setName("admin");
		role.setEsusu(esid);
		em.persist(role);
		
		PendingEsusu pe = new PendingEsusu();
		pe.setEsusu(es);
		pe.setCreatedBy(us);
		pe.setCreatedTime(LocalDateTime.now());

		em.persist(pe);

		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");
      
		ml.add(m);
        
		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload("Esusu id is:" + es.getId()+ " " + "and Esusu name is: "+ es.getName());

		String j = gson.toJson(response);

		return Response.status(201).entity(j).build();

	}
}