package com.adem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tbl_cmessage")
public class ContactMessage {
	
	private static final long serialVersionUID = 1L;
	
	public ContactMessage() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=1,max=50,message="Your Name '${validatedValue'} must be between {min} and {max} chars long")
	@NotNull(message="Please provide your name")
	@Column(length=50, nullable = false)
	private String name;
	
	@Size(min=5,max=50,message="Your subject '${validatedValue'} must be between {min} and {max} chars long")
	@NotNull(message="Please provide your message subject")
	@Column(length=50, nullable = false)
	private String subject;
	
	@Size(min=20,max=200,message="Your message Body '${validatedValue'} must be between {min} and {max} chars long")
	@NotNull(message="Please provide your message body")
	@Column(length=200, nullable = false)
	private String body;
	
	
	@Email(message = "Provide correct an email")
	@Column(length=50, nullable = false)
	private String email;
	
	
	
	
}
