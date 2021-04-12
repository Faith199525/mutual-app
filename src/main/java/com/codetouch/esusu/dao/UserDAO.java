/**
 * 
 */
package com.codetouch.esusu.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.codetouch.user.User;

/**
 * @author CODETOUCH
 *
 */
@ApplicationScoped
public class UserDAO {
	
	@Inject 
	EntityManager em;

	public User getUserByEmail(String email) throws Exception {
		User user = null;
		
		user = (User)em.createQuery("select c from User c where lower(c.email) =  :user")
				.setParameter("user", email.trim().toLowerCase()).getSingleResult();
		
		return user;
		
		
	}

}
