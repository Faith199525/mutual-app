/**
 * 
 */
package com.codetouch.esusu.api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.enterprise.event.Event;
import javax.inject.Inject;
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
import javax.ws.rs.QueryParam;
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
import com.codetouch.esusu.dao.UserDAO;
import com.codetouch.esusu.entity.Esusu;
import com.codetouch.esusu.entity.EsusuMember;
import com.codetouch.esusu.entity.EsusuNotification;
import com.codetouch.esusu.enums.EsusuNotificationStatus;
import com.codetouch.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

/**
 * @author CODETOUCH
 *
 */

@Path("/esusu/members/registration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class EsusuUserRegNotificationResource {

	@Inject
	EntityManager em;

	private Gson gson;
	private GsonResponse response = new GsonResponse();

	@Inject
	JsonWebToken jwt;

	@Inject
	Mailer mailer;

	@Inject
	UserDAO userDao;

	@Inject 
	Event<Esusu> esEvent;
	
	@PostConstruct
	private void postConstruct() {
		GsonBuilder builder = new GsonBuilder();

		gson = builder.create();

	}

	/**
	 * 
	 * @param reg
	 * @param uId
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@PUT
	@Transactional
	@PermitAll
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response checkIfUserIsRegistered(@QueryParam("id") String uId, @Context SecurityContext ctx) {
		List<StatusMessage> ml = new ArrayList<>();

		String sql = "SELECT a FROM EsusuNotification a WHERE a.confirmationToken=:token";
		Query query = em.createQuery(sql);
		query.setParameter("token", uId);

		List<EsusuNotification> n = query.getResultList();

		if (n.isEmpty()) {
			StatusMessage m = new StatusMessage();
			m.setCode("00");
			m.setDescription("No invitation exists");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}

		//
		EsusuNotification es = n.get(0);

		if (es.getLinkExpiryDate() == LocalDate.now() || LocalDate.now().isAfter(es.getLinkExpiryDate())) {
			StatusMessage m = new StatusMessage();
			m.setCode("00");
			m.setDescription("Link has expired");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}

		Esusu esu = em.find(Esusu.class, es.getEsusu());
		if (null == esu) {
			StatusMessage m = new StatusMessage();
			m.setCode("00");
			m.setDescription("Esusu does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();

		}

		// Long convert=Long.valueOf(jwt.getSubject());
		String convert = jwt.getSubject();
		if (convert != null) {
			Long sub = Long.valueOf(convert);
			User user = em.find(User.class, sub);

			return update(user, es);

		}

		User user = null;

		try {
			user = userDao.getUserByEmail(es.getEmail());
		} catch (Exception e) {
			StatusMessage m = new StatusMessage();
			m.setCode("45");
			m.setDescription("Further registration required, pls create an account");

			ml.add(m);

			response.setStatus(404);
			response.setStatusMessages(ml);
			response.setPayload(es.getEmail() + "," + es.getFirstName() + "," + es.getLastName());

			String j = gson.toJson(response);
			return Response.status(201).entity(j).build();
		}

		return update(user, es);

	}

	@SuppressWarnings("unused")
	@Transactional
	public Response update(User user, EsusuNotification es) {
		List<StatusMessage> ml = new ArrayList<>();

		Esusu esu = em.find(Esusu.class, es.getEsusu());
		
		/*if(esu.isCancelled()) {
			
			
		}*/

		Query query = em.createQuery("Select MAX(e) from EsusuMember e where e.esusu=:esusu").setParameter("esusu",
				esu);
		
		List<EsusuMember> n = query.getResultList();
		EsusuMember ee = new EsusuMember();
		ee.setMember(user);
		ee.setEsusu(esu);
		ee.setDateAccepted(LocalDate.now());
		ee.setTimeAccepted(LocalTime.now());

		es.setStatus(EsusuNotificationStatus.A);

		em.merge(es);
		// max leads to null pointer exception when not found, so check against null value
		EsusuMember sm = n.get(0);
		if (n.isEmpty() || sm==null) {
			ee.setSchedule(1);

			em.persist(ee);
			
			StatusMessage m = new StatusMessage();
			m.setCode("00");
			m.setDescription("Success");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}

		Long sch = sm.getSchedule() + 1;
		ee.setSchedule(sch);

		em.persist(ee);
		
		//observer and observable
		Query obs = em.createQuery("Select count(u) from EsusuNotification u where u.esusu=:esusu and u.status = 'P' ").setParameter("esusu",
				es.getEsusu());
		Long s=(Long) obs.getSingleResult();
		int c = 0;
		
		if(s==c) { // this is  critical point of failure
			//send a message to all observers telling them to act basedo n the information 
			esEvent.fire(esu);
		}
		
		 
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
