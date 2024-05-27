package com.adem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.adem.DTOresponse.LoginResponse;
import com.adem.DTOresponse.Response;
import com.adem.DTOresponse.ResponseMessage;
import com.adem.request.LoginRequest;
import com.adem.request.RegisterRequest;
import com.adem.security.jwt.JwtUtils;
import com.adem.service.UserService;
import jakarta.validation.Valid;

@RestController
public class UserJwtController {

	private UserService userService;
	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;

	@Autowired
	public UserJwtController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody RegisterRequest registerRequest) {
		userService.saveUser(registerRequest);

		Response response = new Response();
		response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
		response.setSuccess(true);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@Validated @RequestBody LoginRequest loginRequest) {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail(), loginRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		// mevcut giriş yapan kullanıcıyı getirir
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		String jwtToken = jwtUtils.generateJwtToken(userDetails);

		LoginResponse loginResponse = new LoginResponse(jwtToken);

		return new ResponseEntity<>(loginResponse, HttpStatus.OK);

	}

}
