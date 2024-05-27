package com.adem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adem.domain.ContactMessage;
import com.adem.exception.ResourceNotFoundException;
import com.adem.exception.message.ErrorMessage;
import com.adem.repository.ContactMessageRepository;


@Service
//@AllArgsConstructor
public class ContactMessageService {
	
	@Autowired
	private ContactMessageRepository repository;
	
	
	public void createContactMessage(ContactMessage contactMessage) {
		repository.save(contactMessage);
	}

	public List<ContactMessage> getAll() {
		return repository.findAll();
	}

	public ContactMessage getContactMessage(Long id) throws ResourceNotFoundException {
		return repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
	}

	public void deleteContactMessage(Long id) throws ResourceNotFoundException {
		ContactMessage contactMessage = getContactMessage(id);
		// repository.delete(contactMessage); entweder oder
		repository.deleteById(contactMessage.getId());
	}

	public void updateContactMessage(Long id, ContactMessage newContactMessage) {
		ContactMessage foundMessage = getContactMessage(id);
		foundMessage.setName(newContactMessage.getName());
		foundMessage.setSubject(newContactMessage.getSubject());
		foundMessage.setEmail(newContactMessage.getEmail());
		foundMessage.setBody(newContactMessage.getBody());

		repository.save(foundMessage);
	}
	
	public Page<ContactMessage> getAllWithPage(Pageable pageable){
		return repository.findAll(pageable);
	}

}
