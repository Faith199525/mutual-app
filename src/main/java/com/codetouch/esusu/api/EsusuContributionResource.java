package com.codetouch.esusu.api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import com.codetouch.esusu.GsonResponse;
import com.codetouch.esusu.StatusMessage;
import com.codetouch.esusu.dto.EsusuContributionDTO;
import com.codetouch.esusu.entity.EsusuContribution;
import com.codetouch.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.quarkus.mailer.Mailer;
@RequestScoped
@Path("/esusu/contribution")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class EsusuContributionResource {

	@Inject
	EntityManager em;

	private Gson gson;
	private GsonResponse response = new GsonResponse();

	@Inject
	Mailer mailer;
	
	@Inject
    JsonWebToken jwt;
	
	@PostConstruct
	private void postConstruct() {
		GsonBuilder builder = new GsonBuilder();

		gson = builder.create();

	}
	
	@POST
	@Transactional
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response contribute(EsusuContributionDTO cont, @Context SecurityContext ctx) {
		
		
		List<StatusMessage> ml = new ArrayList<>();
		
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
		
		EsusuContribution ec = new EsusuContribution();
		ec.setAmount(cont.getAmount());
		ec.setMember(us);
		//ec.setEsusu(esusu);//find out how to get esusu id
		ec.setDate_contributed(LocalDate.now());
		ec.setTime_contributed(LocalTime.now());
		em.persist(ec);
		
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);

		response.setStatus(200);
		response.setStatusMessages(ml);

		String j = gson.toJson(response);

		return Response.status(201).entity(j).build();
	}
}
