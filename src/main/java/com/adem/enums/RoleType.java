package com.adem.enums;

public enum RoleType {
	
	ROLE_ADMIN("Administrator"),
	ROLE_CUSTOMER("Customer"),
	 ;
	
	private String name;
	
	// constructorı dışarı açmamak için private yapıyoruz
	private RoleType(String name) {
		this.name = name ;
	}
	
	public String getName() {
		return name ;
	}
	
	

}
