package com.codetouch.esusu.api;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.decorator.Decorator;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import com.codetouch.esusu.GsonResponse;
import com.codetouch.esusu.StatusMessage;
import com.codetouch.esusu.dto.EsusuNotificationDTO;
import com.codetouch.esusu.dto.SusuMember;
import com.codetouch.esusu.entity.Esusu;
import com.codetouch.esusu.entity.EsusuNotification;
import com.codetouch.esusu.enums.EsusuNotificationStatus;
import com.codetouch.user.User;
import com.codetouch.user.UserContinueDTO;
import com.codetouch.user.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

@Path("/esusu/members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EsusuMemberResource {

	@Inject
	EntityManager em;

	@Inject
	JsonWebToken jwt;

	@Inject
	Mailer mailer;

	private Gson gson;
	private GsonResponse response = new GsonResponse();

	@PostConstruct
	private void postConstruct() {
		GsonBuilder builder = new GsonBuilder();

		gson = builder.create();

	}

	@POST
	@Transactional
	@PermitAll
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response add(EsusuNotificationDTO member, @Context SecurityContext ctx) {

		Principal caller = ctx.getUserPrincipal();
		System.out.println(caller);
		List<StatusMessage> ml = new ArrayList<>();

		Long convert = Long.valueOf(jwt.getSubject());
		User u = em.find(User.class, convert);
		if (u == null) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("User is not registered ");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			return Response.status(200).entity(j).build();

		}
		List<SusuMember> users = member.getMembers();
		if (null == users || users.isEmpty()) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("Add members to esusu");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			return Response.status(200).entity(j).build();
		}

		// Ensure that the essuid is not null
		Esusu es = em.find(Esusu.class, member.getEsusuId());
		if (es == null) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("Esusu does not exist");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			Response.status(200).entity(j).build();
		}

		if (u != es.getOwner()) {
			StatusMessage m = new StatusMessage();
			m.setCode("-1");
			m.setDescription("Esusu does not belong to User");

			ml.add(m);

			response.setStatus(400);
			response.setStatusMessages(ml);

			String j = gson.toJson(response);

			Response.status(200).entity(j).build();
		}

		users.forEach(user -> {

			EsusuNotification us = new EsusuNotification();

			us.setFirstName(user.getFirstName());
			us.setLastName(user.getLastName());
			us.setEmail(user.getEmail());
			us.setEsusu(member.getEsusuId());
			us.setOwner(convert);
			us.setLinkExpiryDate(es.getStartDate());
			us.setLinkSentDate(LocalDate.now());
			us.setConfirmationToken(UUID.randomUUID().toString());
			us.setStatus(EsusuNotificationStatus.P);

			em.persist(us);

			mailer.send(Mail.withText(user.getEmail(), "Click to complete registration:",
					"http://localhost:8080/esusu/members/registration?id=" + us.getConfirmationToken()));
		});

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
