/**
 * 
 */
package com.codetouch.esusu;

import java.util.List;

import com.codetouch.esusu.entity.Esusu;

/**
 * @author CODETOUCH
 *
 */
public class GsonResponse {
	
	private Integer status;
	private List<StatusMessage> statusMessages;
	private Object payload;
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<StatusMessage> getStatusMessages() {
		return statusMessages;
	}
	public void setStatusMessages(List<StatusMessage> statusMessages) {
		this.statusMessages = statusMessages;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	public void setPayload(Esusu esid, String name) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
