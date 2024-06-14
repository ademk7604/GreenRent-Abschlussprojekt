package com.adem.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adem.DTO.UserDTO;
import com.adem.domain.Role;
import com.adem.domain.User;
import com.adem.enums.RoleType;
import com.adem.exception.ConflictException;
import com.adem.exception.ResourceNotFoundException;
import com.adem.exception.message.ErrorMessage;
import com.adem.mapper.UserMapper;
import com.adem.repository.UserRepository;
import com.adem.request.RegisterRequest;
import com.adem.request.UpdateUserRequest;
import com.adem.security.config.SecurityUtils;
import com.adem.security.jwt.JwtUtils;

@Service
public class UserService {

	private UserRepository userRepository;

	private UserMapper userMapper;

	private RoleService roleService;

	private PasswordEncoder passwordEncoder;

	private JwtUtils jwtUtils;
	
	private UserService userService;

	public UserService(UserRepository userRepository,UserMapper userMapper, @Lazy UserService userService,
			RoleService roleService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {

		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
		this.userService = userService;
	}

	// ------------------ get current user ------------------------
	public User getCurrentUser() {

		String email = SecurityUtils.getCurrentUserLogin()
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
		User user = getUserByEmail(email);
		return user;

	}

	// ------------------ get current userDTO ------------------------
	public UserDTO getPrincipal() {
		User currentUser = getCurrentUser();
		return userMapper.userToUserDto(currentUser);

	}


	// --------------------get user profile------------------------
	public UserDTO findUserProfile() {

		User user = getCurrentUser();
		if (user == null) {
			throw new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, user));
		}
		
		Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		
        user.setCreateAt(LocalDateTime.now());
		UserDTO userDTO = userMapper.userToUserDto(user);
		return userDTO;
	}

	//---------------get all users-----------------
	public List<UserDTO> getAllUsers(){
		List<User> users = userRepository.findAll();
		return userMapper.userListToUserDTOList(users);
	}
	
	public Page<UserDTO> getUserPage(Pageable pageable){
		Page<User> users= userRepository.findAll(pageable);
		Page<UserDTO> dtoPage = users.map(new Function<User, UserDTO>(){
			
			@Override
			public UserDTO apply(User user) {
				return userMapper.userToUserDto(user);
			}
		});
		return dtoPage;
	}

	//--------------update user-------------------
	public void updateUser(UpdateUserRequest updateUserRequest) {
		User user=userService.getCurrentUser();
		if ((user == null)) {
			new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_IS_NOT_MATCH));

		}
		Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);

		Set<Role> roles = new HashSet<>();
		
		String encodedPassword = passwordEncoder.encode(updateUserRequest.getPassword());
	
		roles.add(role);
		user.setRoles(roles);
		user.setUpdateAt(LocalDateTime.now());
		user.setAddress(updateUserRequest.getAddress());
		user.setFirstName(updateUserRequest.getFirstName());
		user.setLastName(updateUserRequest.getLastName());
		user.setPhone(updateUserRequest.getPhone());
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}

	// ---------------- register user----------------------

	public void saveUser(RegisterRequest registerRequest) {
		if(userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new ConflictException(
					String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, registerRequest.getEmail()));
		}

		Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);

		Set<Role> roles = new HashSet<>();
		roles.add(role);

		String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

		User user = new User();
		user.setRoles(roles);
		user.setPassword(encodedPassword);
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setEmail(registerRequest.getEmail());
		user.setAddress(registerRequest.getAddress());
		user.setPhone(registerRequest.getPhone());
		user.setZipCode(registerRequest.getZipCode());
		user.setCreateAt(LocalDateTime.now());

		userRepository.save(user);

	}

	public void deleteUserWithId(Long id) {

		userRepository.deleteById(id);
	}

//------------- find user by email-------------------
	public User getUserByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email)));
		return user;
	}

//------------ convert roles------------------
	public Set<Role> convertRoles(Set<String> pRoles) {
		Set<Role> roles = new HashSet<>();

		if (pRoles == null) {
			Role userRole = roleService.findByType(RoleType.ROLE_ADMIN);
			roles.add(userRole);
		} else {
			pRoles.forEach(roleStr -> {
				if (roleStr.equals(RoleType.ROLE_ADMIN.getName())) { // Administrator
					Role adminRole = roleService.findByType(RoleType.ROLE_ADMIN);
					roles.add(adminRole);

				} else {
					Role userRole = roleService.findByType(RoleType.ROLE_ADMIN);
					roles.add(userRole);
				}
			});
		}

		return roles;
	}


}
