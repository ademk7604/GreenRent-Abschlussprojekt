package com.adem.domain;



import com.adem.enums.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name="tbl_role")
@Entity
public class Role {
	
	// 1- Customer / 2 Admin
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Integer id;       
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RoleType type;

	@Override
	public String toString() {
		return "Role [type=" + type + "]";
	}
	
	
	
	

}