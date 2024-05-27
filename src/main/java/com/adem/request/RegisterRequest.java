package com.adem.request;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.adem.domain.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	@Size(max=50)
	@NotBlank(message="Please provide your First Name")
	private String firstName;
	
	@Size(max=50)
	@NotBlank(message="Please provide your Last Name")
	private String lastName;
	
	@Size(min=5, max=20)
	@Email(message="Please provide valid e-mail")
	private String email;
	
	@Size(min=4, max=20, message="Please provide Correct Size of Password")
	@NotBlank(message="Please provide your password")
	private String password;
	
	@Pattern(regexp ="^(\\d{4} \\d{3} \\d{2} \\d{2})$",	// 9999 999 99 99
			message = "Please provide valid phone number" ) 
	@Size(min=14, max=20, message="Please provide Correct Size of Password")
	@NotBlank(message="Please provide your password")
	private String phone;
	
    @Size(max= 100)
    @NotBlank(message = "Please provide your address")
	private String address;
	
    @CreationTimestamp
    @Column(name = "creatAt", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updateAt;
    
    @Size(max=15)
	@NotBlank(message="Please provide your zipcode")
    private String zipCode;



}