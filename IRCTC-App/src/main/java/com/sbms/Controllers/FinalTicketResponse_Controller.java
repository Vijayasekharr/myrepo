package com.sbms.Controllers;

import java.util.Optional;import org.bouncycastle.crypto.engines.ISAACEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sbms.Entitys.TicketRequest;
import com.sbms.Entitys.FinalTicketResponse;
import com.sbms.ServicessI.FinalTicketResponseServicessI;
import com.sbms.ServicessImpl.FinalTicketResponseServicessImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class FinalTicketResponse_Controller {
	
	@Autowired
	FinalTicketResponseServicessI finalTicketResponseServicessI;
	
	@Autowired
	Environment environment;
	
	@PostMapping(value = "/bookTicket", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<FinalTicketResponse> bookTicket(@RequestBody TicketRequest ticketRequest) {
		FinalTicketResponse finalTicketResponse = finalTicketResponseServicessI.bookTicket(ticketRequest);
		log.info("[ The bookTicket Request Processing by the IRCTC-APPLICATION which run's on the Port :: "+environment.getProperty("server.port") +" ]");
		return new ResponseEntity<FinalTicketResponse>(finalTicketResponse,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/deleteTicket/{pnr}")
	public ResponseEntity<String> deleteTicket(@PathVariable Integer pnr){
		String string = finalTicketResponseServicessI.deleteTicket(pnr);
		if(string.equals("Invalid pnr Number !!!")) {
			return new ResponseEntity<String>(string, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(string, HttpStatus.OK);
	}
	
	@PutMapping("/updateTicket")
	public ResponseEntity updateTicket(@RequestBody FinalTicketResponse finalTicketResponse1) throws CloneNotSupportedException{
		
		finalTicketResponse1.setBoarding_station(finalTicketResponse1.getBoarding_station().toUpperCase());
		System.out.println("finalTicketResponse1 is :: "+finalTicketResponse1);
		
		ResponseEntity ticket = this.getTicket(finalTicketResponse1.getPnr());
		Object body = ticket.getBody();
		if(body instanceof FinalTicketResponse) {
			FinalTicketResponse finalTicketResponse2 = (FinalTicketResponse) body;
			
			ResponseEntity updateTicket = finalTicketResponseServicessI.updateTicket(finalTicketResponse1, finalTicketResponse2);
			return updateTicket;			
		}
		else {
			if (body instanceof String) {
				return new ResponseEntity<String>("Invalid pnr number !!!!!!!", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>("Invalid pnr number !!!!!!!", HttpStatus.BAD_REQUEST);	
	}
	
	@GetMapping("/getTicket/{pnr}")
	public ResponseEntity getTicket(@PathVariable Integer pnr){
		Optional<FinalTicketResponse> ticket = finalTicketResponseServicessI.getTicket(pnr);
		if (ticket.isPresent()) {
			return new ResponseEntity<FinalTicketResponse>(ticket.get(), HttpStatus.FOUND);
		}
		return new ResponseEntity<String>("Invalid pnr Number !!!",HttpStatus.BAD_REQUEST);
		
	}
}
