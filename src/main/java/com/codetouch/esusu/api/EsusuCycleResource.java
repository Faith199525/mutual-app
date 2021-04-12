package com.codetouch.esusu.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codetouch.esusu.GsonResponse;
import com.codetouch.esusu.StatusMessage;
import com.codetouch.esusu.dto.EsusuCycleDTO;
import com.codetouch.esusu.entity.EsusuCycle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/esusuCycle")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EsusuCycleResource {

	@Inject
	EntityManager em;

	private Gson gson;
	private GsonResponse response = new GsonResponse();


	@PostConstruct
	private void postConstruct() {
		GsonBuilder builder = new GsonBuilder();

		gson = builder.create();

	}
	
	@GET
	public Response cyc() {
		List<StatusMessage> ml = new ArrayList<>();
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");
		ml.add(m);

		List<EsusuCycle> es = em.createQuery(" select c from EsusuCycle c").getResultList();

		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(es);
		String json = gson.toJson(response);
		return Response.status(200).entity(json).build(); // EsusuCycle.listAll();
	}
	
	@POST
	@Transactional
	public Response cycle(EsusuCycleDTO cycle) {
		List<StatusMessage> ml = new ArrayList<>();
		
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String j = gson.toJson(response);

		EsusuCycle es= new EsusuCycle();
		es.setName(cycle.getName());
		es.setDescription(cycle.getDescription());
		em.persist(es);
		return Response.status(201).entity(j).build();
		
	}
}
