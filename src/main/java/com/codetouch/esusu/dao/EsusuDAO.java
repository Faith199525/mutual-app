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
public class EsusuDAO {
	
	private static final Long ESUSU_PENDING_THRESHOLD = 3l;
	
	@Inject 
	EntityManager em;

	
	public boolean isThresholdExceeded(User us) {
		String sql =" select count(c) from PendingEsusu c where c.createdBy = :user";
		Long count = (Long)em.createQuery(sql).setParameter("user", us).getSingleResult();
		
		if(null == count || count <=  ESUSU_PENDING_THRESHOLD)return false;
		return true;
	}
	
	

}
