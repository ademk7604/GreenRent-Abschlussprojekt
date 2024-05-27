package com.adem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adem.domain.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long>{
	

}
