package com.adem.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String id;
	
	public ResourceNotFoundException(String message) {
		super(message );
	}	
		public ResourceNotFoundException(String message, String id) {
			super(message );
		    this.id=id;
	}

	
	

}
