/**
 * 
 */
package com.codetouch.esusu.enums;

/**
 * @author CODETOUCH
 *
 */
public enum EsusuNotificationStatus {
	A("Accepted"),
	P("Pending"),
	R("Rejected");
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	private EsusuNotificationStatus(String desc) {
		this.description = desc;
	}

	
	
}
