package com.adem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adem.domain.ContactMessage;
import com.adem.service.ContactMessageService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;



//@AllArgsConstructor
@RestController
@RequestMapping("/contactmessage")
public class ContactMessageController {

	
	private ContactMessageService contactMessageService;
	
	@Autowired
	public ContactMessageController(ContactMessageService contactMessageService) {
		this.contactMessageService=contactMessageService;
	}

	@PostMapping("/visitor")
	public ResponseEntity<Map<String, String>> createMessage(@Valid @RequestBody ContactMessage contactMessage) {
		contactMessageService.createContactMessage(contactMessage);

		Map<String, String> map = new HashMap<>();
		map.put("message", "Contact Message Successfully created");
		map.put("status", "true");

		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ContactMessage>> getAllContactMessage(){
		List<ContactMessage> list = contactMessageService.getAll();
		return ResponseEntity.ok(list);
	}
	
	/** cift yildiz enter ile geliyor
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ContactMessage> getContactMessageWithId(@PathVariable("id") Long id){
		ContactMessage contactMessage = contactMessageService.getContactMessage(id);
		return ResponseEntity.ok(contactMessage);
	}
	
	//http://localhost:8080/contactmessage/request?id=2
	@GetMapping("/request")
	public ResponseEntity<ContactMessage> getContactMessageRequestParam(@RequestParam("id") Long id){
		ContactMessage contactMessage = contactMessageService.getContactMessage(id);
		return ResponseEntity.ok(contactMessage);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, String>> updateContactMessage(@PathVariable Long id, @Valid @RequestBody ContactMessage contactMessage){
		contactMessageService.updateContactMessage(id, contactMessage);
		
		Map<String, String> map = new HashMap<>();
		map.put("message", "Contact Message Successfully updated");
		map.put("status", "true");

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteContactMessage(@PathVariable Long id){
		contactMessageService.deleteContactMessage(id);
		
		Map<String, String> map = new HashMap<>();
		map.put("message", "Contact Message Successfully deleted");
		map.put("status", "true");

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping("/pages")
	public ResponseEntity<Page<ContactMessage>> getAllWithPage(
			@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("sort") String prop, @RequestParam("direction") Direction direction){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction,prop));
		Page<ContactMessage> contactMessagePage = contactMessageService.getAllWithPage(pageable);
		
		return ResponseEntity.ok(contactMessagePage);
	}

}
