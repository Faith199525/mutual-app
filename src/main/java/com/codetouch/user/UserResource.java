/**
 * 
 */
package com.codetouch.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codetouch.esusu.GsonResponse;
import com.codetouch.esusu.StatusMessage;
import com.codetouch.esusu.dao.EsusuDAO;
import com.codetouch.esusu.entity.EsusuNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

/**
 * @author CODETOUCH
 *
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	@Inject
	EntityManager em;

	private Gson gson;
	private GsonResponse response = new GsonResponse();

	@Inject
	Mailer mailer;

	@PostConstruct
	private void postConstruct() {
		GsonBuilder builder = new GsonBuilder();

		gson = builder.create();

	}

	@SuppressWarnings("unchecked")
	@GET
	public Response us() {
		List<StatusMessage> ml = new ArrayList<>();

		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");
		ml.add(m);

		List<User> es = em.createQuery("select c from User c").getResultList();

		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(es);
		String j = gson.toJson(response);
		return Response.status(200).entity(j).build(); // return User.listAll();
	}
	
	@POST
	@Transactional
	public Response account(UserDTO user) {
		UserNotification us = new UserNotification();
	
			List<StatusMessage> ml = new ArrayList<>();
			StatusMessage m = new StatusMessage();
			m.setCode("00");
			m.setDescription("Success");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);

			us.setLastName(user.getLastName());
			us.setEmail(user.getEmail());
			us.setConfirmationToken(UUID.randomUUID().toString());
			em.persist(us);
			mailer.send(Mail.withText(user.getEmail(), "Click to complete registration:",
					"http://localhost:8080/user?id=" + us.getConfirmationToken()));
			return Response.status(200).entity(json).build();
		
	}

	@PUT
	@Transactional
	public Response updateAccount(UserContinueDTO reg, @QueryParam("id") String uId) {

		String sql = "SELECT a FROM UserNotification a WHERE a.confirmationToken=:token";
		Query query = em.createQuery(sql);
		query.setParameter("token", uId);
		
		List<UserNotification>  n = query.getResultList();
		
		UserNotification es =n.get(0);
	    
		User us= new User();
		//us.setConfirmationToken(es.getConfirmationToken());
		us.setCountry(reg.getCountry());
		us.setEmail(es.getEmail());
		us.setFirstName(reg.getFirstName());
		us.setLastName(es.getLastName());
		us.setMiddleName(reg.getMiddleName());
		us.setPassword(reg.getPassword());
		us.setPhoneNumber(reg.getPhoneNumber());
		us.setSex(reg.getSex());
		us.setState(reg.getState());
		
		em.persist(us);
		
		List<StatusMessage> ml = new ArrayList<>();
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String j = gson.toJson(response);
		return Response.status(200).entity(j).build();
	}

}
