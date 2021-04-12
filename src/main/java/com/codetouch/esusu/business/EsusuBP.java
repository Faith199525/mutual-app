/**
 * 
 */
package com.codetouch.esusu.business;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.codetouch.esusu.entity.Esusu;
import com.codetouch.esusu.entity.EsusuNotification;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

/**
 * @author CODETOUCH
 *
 */
@ApplicationScoped
public class EsusuBP {
	@Inject
	EntityManager em;
	
	@Inject 
	Mailer mailer;
	
	public void activateNewEsusu(@Observes Esusu esu) {
		
		System.out.println( " you are welcome " + esu.getName());
		
		Query query = em.createQuery("select u from EsusuNotification u where u.esusu=:esusu").setParameter("esusu", esu.getId());
		List<EsusuNotification> s =query.getResultList(); 
		
		s.forEach(member-> {
			mailer.send(Mail.withText(member.getEmail(),
				 "Acceptance Notification",
				  "This is to inform you that all members invited to" + esu.getName() +
				 " has successfully accepted invitataion")); });
		
	}

}
