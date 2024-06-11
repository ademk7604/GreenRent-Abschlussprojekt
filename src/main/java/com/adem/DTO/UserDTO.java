package com.adem.DTO;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.adem.domain.Role;
import com.adem.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private LocalDateTime updateAt;

	private String phone;

	private LocalDateTime createAt;

	private String address;

	private String zipCode;

	private boolean builtIn;

	private Set<String> roles;

	//Warum sollte der Benutzer meine Rollen kennen?
	public void setRoles(Set<Role> roles) {
		Set<String> rolesStr = new HashSet<>();

		roles.forEach(r -> {
			if (r.getType().equals(RoleType.ROLE_ADMIN)) {
				rolesStr.add("Administrator");
			} else {
				rolesStr.add("Customer");
			}
		});
		this.roles=rolesStr;
	}

}
