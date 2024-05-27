package com.adem.mapper;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.adem.DTO.UserDTO;
import com.adem.domain.Role;
import com.adem.domain.User;
import com.adem.request.RegisterRequest;
import com.adem.request.UserRequest;



@Mapper(componentModel = "spring")
public interface UserMapper  {
	
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "updateAt",ignore = true)
	@Mapping(target = "roles",ignore = true)
	User userDTOToUser(UserDTO userDTO);
	
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "roles",ignore = true)
	User registerUserToUser(RegisterRequest registerRequest);
  
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "roles",ignore = true)
	@Mapping(target = "createAt",ignore = true)
	@Mapping(target = "builtIn",ignore = true)
	User userRequestToUser(UserRequest userRequest);
	

	
	UserDTO userToUserDto(User user);
	
	
	List<UserDTO> userListToUserDTOList(List<User> userList);
	
	@Named("getRoleAsString")
	public static Set<String> mapRoles(Set<Role> roles) {
		
		Set<String> roleStr = new HashSet<>();
		
       roles.forEach( r-> {
			roleStr.add(r.getType().getName()); // Administrator veya Customer gözükecek
			
		}); 
		
		return roleStr;
	}
}