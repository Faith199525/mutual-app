package com.codetouch.esusu.login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
import com.codetouch.esusu.jwt.TokenUtils;
import com.codetouch.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.quarkus.mailer.Mailer;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Path("/login")
public class LoginResource {
	
 private Login user = null;
	
    @Inject
	EntityManager em;
    
	/*@PostConstruct
	public void initialize() {
		 user = new Login();
		user.setEmail("code@gmail.com");
		user.setFirstName("Victor");
		user.setLastName("user");
		user.setPassword("demo");	
	}*/
	
	@SuppressWarnings("unchecked")
	@POST
	@Transactional
	public String login( Login user) {
		
		String select = "SELECT ua FROM User ua WHERE ua.email=:email and ua.password=:password";
		Query query = em.createQuery(select);
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
	   List<User> u= query.getResultList();
	   
	   if (u.isEmpty()) {
		   
			return "User does not exist";
		}
	   
	    User us= u.get(0);
	   
		String userName= user.getEmail();
		String owner=String.valueOf(us.getId());
		
		System.out.println(owner);
	
		try {
			String token = TokenUtils.generateJWT(userName, owner);
			System.out.println(token);
		
			return token;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
        
	
	}

	

	
}
