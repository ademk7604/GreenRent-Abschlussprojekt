package com.adem.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adem.DTO.UserDTO;
import com.adem.service.UserService;




@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService=userService;
		
	}

	@GetMapping("auth/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<UserDTO> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<UserDTO> getUser(){
		
		UserDTO userDTO = userService.getPrincipal();
		return ResponseEntity.ok(userDTO);
	}
	
	@GetMapping("/auth/pages")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<UserDTO>> getAllUserByPage(@RequestParam("page") int page,
															@RequestParam("size") int size,
															@RequestParam("sort") String prop,
															@RequestParam("direction") Direction direction){
		Pageable pageable = PageRequest.of(page, size,Sort.by(direction,prop));
		Page<UserDTO> pages = userService.getUserPage(pageable);
		
		return ResponseEntity.ok(pages);
	}
	
	 

}
