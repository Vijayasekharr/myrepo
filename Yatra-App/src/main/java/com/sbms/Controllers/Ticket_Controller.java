package com.sbms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbms.Entitys.FinalTicketResponse;
import com.sbms.Entitys.TicketRequest;
import com.sbms.ServicesI.Ticket_ServicesI;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class Ticket_Controller {
	
	@Autowired
	Ticket_ServicesI ticket_ServicesI;
	
	@Autowired
	Environment environment;

	@RequestMapping(method = RequestMethod.POST, value = "bookTicket", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<FinalTicketResponse> bookTicket(@RequestBody TicketRequest ticketRequest) {
		
		System.out.println("ticketRequest is :: "+ticketRequest);
		
		FinalTicketResponse finalTicketResponse = ticket_ServicesI.bookTicket(ticketRequest);
		log.info("[ The bookTicket Request Processing by the Yatra-APPLICATION which run's on the Port :: "+environment.getProperty("server.port") +" ]");
		return new ResponseEntity<FinalTicketResponse>(finalTicketResponse, HttpStatus.FOUND);

	}
}
