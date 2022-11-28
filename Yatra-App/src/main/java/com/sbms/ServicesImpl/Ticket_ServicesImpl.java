package com.sbms.ServicesImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sbms.Entitys.FinalTicketResponse;
import com.sbms.Entitys.TicketRequest;
import com.sbms.ServicesI.Ticket_ServicesI;

@Service
public class Ticket_ServicesImpl implements Ticket_ServicesI {
	
	
	@Override
	public FinalTicketResponse bookTicket(TicketRequest ticketRequest) {

		
		/* Connecting to IRCTC-Application through WebClient */
		String URL = "http://swapna:2000/sbms/irctc/bookTicket";
		FinalTicketResponse finalTicketResponse = WebClient.create()
				 .post()
				 .uri(URL)
				 .headers(headers->headers.setBasicAuth("yatra", "yatra123"))
				 .bodyValue(ticketRequest)
				 .retrieve()
				 .bodyToMono(FinalTicketResponse.class)
				 .block();
				 
		finalTicketResponse.setProvider("Yatra");
		return finalTicketResponse;
	}

}
