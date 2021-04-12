package com.codetouch.esusu.dto;

import java.util.List;

public class EsusuNotificationDTO {
	
	private Long esusuId;
	private List<SusuMember> members;
	
	

	public EsusuNotificationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getEsusuId() {
		return esusuId;
	}



	public void setEsusuId(Long esusuId) {
		this.esusuId = esusuId;
	}



	public List<SusuMember> getMembers() {
		return members;
	}



	public void setMembers(List<SusuMember> members) {
		this.members = members;
	}



	
	

	
	
	
	
	
}
