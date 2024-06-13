package com.adem.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adem.domain.Role;
import com.adem.domain.User;
import com.adem.enums.RoleType;


@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

    @EntityGraph(attributePaths = "roles")
	Optional<User> findByEmail(String email);


	//Optional<User>  findById(Long id);

	@EntityGraph(attributePaths = "roles")
	Page<User> findAll(Pageable pageable);
	
	
	Boolean existsByEmail(String email);


	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u JOIN u.roles r WHERE r.type = :roleType")
    boolean existsByRoles(@Param("roleType") RoleType roleType);

}

